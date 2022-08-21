package com.ruoyi.project.model.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.enums.ESeparator;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.framework.manager.AsyncManager;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.model.domain.ModelUpload;
import com.ruoyi.project.model.service.IModelUploadService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 素材Controller
 *
 * @author dengsd
 * @date 2022-08-19
 */
@RestController
@RequestMapping("/model/upload")
public class ModelUploadController extends BaseController
{
    @Autowired
    private IModelUploadService modelUploadService;
    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询素材列表
     */
    @PreAuthorize("@ss.hasPermi('model:upload:list')")
    @GetMapping("/list")
    public TableDataInfo list(ModelUpload modelUpload)
    {
        startPage();
        List<ModelUpload> list = modelUploadService.selectModelUploadList(modelUpload);
        return getDataTable(list);
    }

    /**
     * 导出素材列表
     */
    @PreAuthorize("@ss.hasPermi('model:upload:export')")
    @Log(title = "素材", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ModelUpload modelUpload)
    {
        List<ModelUpload> list = modelUploadService.selectModelUploadList(modelUpload);
        ExcelUtil<ModelUpload> util = new ExcelUtil<ModelUpload>(ModelUpload.class);
        util.exportExcel(response, list, "素材数据");
    }

    /**
     * 获取素材详细信息
     */
    @PreAuthorize("@ss.hasPermi('model:upload:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(modelUploadService.selectModelUploadById(id));
    }

    /**
     * 新增素材
     */
    @PreAuthorize("@ss.hasPermi('model:upload:add')")
    @Log(title = "素材", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ModelUpload modelUpload)
    {
        return toAjax(modelUploadService.insertModelUpload(modelUpload));
    }
    /**
     * 新增素材
     */
    @PreAuthorize("@ss.hasPermi('model:upload:add')")
    @Log(title = "素材", businessType = BusinessType.INSERT)
    @PostMapping("simple")
    public AjaxResult upload(MultipartFile file)
    {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            List<ModelUpload> uploadList = new ArrayList<>();
            String separator = ESeparator.DEFAULT.getSeparator();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            String originalFilename = file.getOriginalFilename();
            int indexOf = originalFilename.lastIndexOf(".");
            String pref = originalFilename.substring(0, indexOf);
            String modelCode = Stream.of(pref.split(separator)[0], pref.split(separator)[1], pref.split(separator)[2], pref.split(separator)[3]).collect(Collectors.joining(separator));
            //构建存储信息
            ModelUpload upload = new ModelUpload();
            upload.setFilePath(fileName);
            upload.setFileName(FileUtils.getName(fileName));
            upload.setFileType(FilenameUtils.getExtension(fileName));
            upload.setOriginalName(originalFilename);
            upload.setSize(file.getSize());
            upload.setModelCode(modelCode);
            uploadList.add(upload);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            AsyncManager.me().execute(new TimerTask() {
                @Override
                public void run() {
                    SpringUtils.getBean(IModelUploadService.class).insertBatch(uploadList);
                }
            });
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 修改素材
     */
    @PreAuthorize("@ss.hasPermi('model:upload:edit')")
    @Log(title = "素材", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ModelUpload modelUpload)
    {
        return toAjax(modelUploadService.updateModelUpload(modelUpload));
    }

    /**
     * 删除素材
     */
    @PreAuthorize("@ss.hasPermi('model:upload:remove')")
    @Log(title = "素材", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(modelUploadService.deleteModelUploadByIds(ids));
    }
}