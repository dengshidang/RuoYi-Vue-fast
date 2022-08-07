package com.ruoyi.project.common.controller;


import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.model.domain.Attribute;
import com.ruoyi.project.model.service.IModelAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 模型属性Controller
 *
 * @author dengsd
 * @date 2022-07-31
 */
@RestController
@RequestMapping("/attribute")
public class AttributeController extends BaseController
{
    @Autowired
    private IModelAttributeService modelAttributeService;

     /**
     * 查询模型属性列表
     */
    @PreAuthorize("@ss.hasPermi('model:attribute:list')")
    @GetMapping("/list")
    public TableDataInfo list(Attribute attribute)
    {
        startPage();
        List<Attribute> list = modelAttributeService.selectModelAttributeList(attribute,(ex)->ex.orderByDesc(Attribute::getAttrId));
        return getDataTable(list);
    }
    @GetMapping("/all")
    public AjaxResult all(Attribute attribute)
    {
        return AjaxResult.success(modelAttributeService.selectModelAttributeList(attribute,(ex)->ex.orderByAsc(Attribute::getAttrId)));
    }

    /**
     * 导出模型属性列表
     */
    @PreAuthorize("@ss.hasPermi('model:attribute:export')")
    @Log(title = "模型属性", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Attribute attribute)
    {
        List<Attribute> list = modelAttributeService.selectModelAttributeList(attribute,(ex)->ex.orderByDesc(Attribute::getAttrId));
        ExcelUtil<Attribute> util = new ExcelUtil<Attribute>(Attribute.class);
        util.exportExcel(response, list, "模型属性数据");
    }

    /**
     * 获取模型属性详细信息
     */
    @PreAuthorize("@ss.hasPermi('model:attribute:query')")
    @GetMapping(value = "/{modelAttrId}")
    public AjaxResult getInfo(@PathVariable("modelAttrId") Integer modelAttrId)
    {
        return AjaxResult.success(modelAttributeService.selectModelAttributeByModelAttrId(modelAttrId));
    }

    /**
     * 新增模型属性
     */
    @PreAuthorize("@ss.hasPermi('model:attribute:add')")
    @Log(title = "模型属性", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated Attribute attribute)
    {
        return toAjax(modelAttributeService.insertModelAttribute(attribute));
    }

    /**
     * 修改模型属性
     */
    @PreAuthorize("@ss.hasPermi('model:attribute:edit')")
    @Log(title = "模型属性", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody  @Validated Attribute attribute)
    {
        return toAjax(modelAttributeService.updateModelAttribute(attribute));
    }

    /**
     * 删除模型属性
     */
    @PreAuthorize("@ss.hasPermi('model:attribute:remove')")
    @Log(title = "模型属性", businessType = BusinessType.DELETE)
	@DeleteMapping("/{modelAttrId}")
    public AjaxResult remove(@PathVariable Integer modelAttrId)
    {
        return toAjax(modelAttributeService.deleteModelAttributeByModelAttrId(modelAttrId));
    }
}
