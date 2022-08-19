package com.ruoyi.project.model.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.ruoyi.project.model.domain.Model;
import com.ruoyi.project.model.service.IModelService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;


/**
 * 模型Controller
 *
 * @author dengsd
 * @date 2022-08-16
 */
@RestController
@RequestMapping("/model/3d")
public class ModelController extends BaseController {
    private static final String FILE_DELIMETER = ",";
    @Autowired
    private IModelService modelService;


    /**
     * 查询模型列表
     */
    @PreAuthorize("@ss.hasPermi('model:model:list')")
    @GetMapping("/list")
    public TableDataInfo list(Model model) {
        startPage();
        List<Model> list = modelService.selectModelList(model);
        return getDataTable(list);
    }

    /**
     * 导出模型列表
     */
    @PreAuthorize("@ss.hasPermi('model:model:export')")
    @Log(title = "模型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Model model) {
        List<Model> list = modelService.selectModelList(model);
        ExcelUtil<Model> util = new ExcelUtil<Model>(Model.class);
        util.exportExcel(response, list, "模型数据");
    }

    /**
     * 获取模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('model:model:query')")
    @GetMapping(value = "/{modelId}")
    public AjaxResult getInfo(@PathVariable("modelId") Integer modelId) {
        return AjaxResult.success(modelService.selectModelByModelId(modelId));
    }

    /**
     * 新增模型
     */
    @PreAuthorize("@ss.hasPermi('model:model:add')")
    @Log(title = "模型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Model model) {
        return toAjax(modelService.insertModel(model));
    }

    /**
     * 修改模型
     */
    @PreAuthorize("@ss.hasPermi('model:model:edit')")
    @Log(title = "模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Model model) {
        return toAjax(modelService.updateModel(model));
    }

    /**
     * 删除模型
     */
    @PreAuthorize("@ss.hasPermi('model:model:remove')")
    @Log(title = "模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{modelIds}")
    public AjaxResult remove(@PathVariable Integer[] modelIds) {
        return toAjax(modelService.deleteModelByModelIds(modelIds));
    }
}
