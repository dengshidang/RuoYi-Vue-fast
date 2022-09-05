package com.ruoyi.project.model.controller;

import com.ruoyi.common.enums.EModelSuffix;
import com.ruoyi.common.enums.ESeparator;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.model.domain.ModelUpload;
import com.ruoyi.project.model.service.IModelUploadService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 素材Controller
 *
 * @author dengsd
 * @date 2022-08-19
 */
@RestController
@RequestMapping("/model/upload")
public class ModelUploadController extends BaseController {
    @Autowired
    private IModelUploadService modelUploadService;
    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询素材列表
     */
    @PreAuthorize("@ss.hasPermi('model:upload:list')")
    @GetMapping("/list")
    public TableDataInfo list(ModelUpload modelUpload) {
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
    public void export(HttpServletResponse response, ModelUpload modelUpload) {
        List<ModelUpload> list = modelUploadService.selectModelUploadList(modelUpload);
        ExcelUtil<ModelUpload> util = new ExcelUtil<ModelUpload>(ModelUpload.class);
        util.exportExcel(response, list, "素材数据");
    }

    /**
     * 获取素材详细信息
     */
    @PreAuthorize("@ss.hasPermi('model:upload:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(modelUploadService.selectModelUploadById(id));
    }

    /**
     * 新增素材
     */
    @PreAuthorize("@ss.hasPermi('model:upload:add')")
    @Log(title = "素材", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody List<ModelUpload> modelUploads) {
        return toAjax(modelUploadService.insertModelUpload(modelUploads));
    }

    /**
     * 新增素材
     */
    @PreAuthorize("@ss.hasPermi('model:upload:add')")
    @Log(title = "素材", businessType = BusinessType.INSERT)
    @PostMapping("simple")
    public AjaxResult upload(MultipartFile file) {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadModePath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file, false);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
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
    public AjaxResult edit(@RequestBody ModelUpload modelUpload) {
        return toAjax(modelUploadService.updateModelUpload(modelUpload));
    }

    /**
     * 删除素材
     */
    @PreAuthorize("@ss.hasPermi('model:upload:remove')")
    @Log(title = "素材", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(modelUploadService.deleteModelUploadByIds(ids));
    }
}
