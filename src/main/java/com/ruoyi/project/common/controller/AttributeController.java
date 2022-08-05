package com.ruoyi.project.common.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.model.domain.ModelAttribute;
import com.ruoyi.project.model.service.IModelAttributeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

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
    public TableDataInfo list(ModelAttribute modelAttribute)
    {
        startPage();
        List<ModelAttribute> list = modelAttributeService.selectModelAttributeList(modelAttribute);
        return getDataTable(list);
    }

    /**
     * 导出模型属性列表
     */
    @PreAuthorize("@ss.hasPermi('model:attribute:export')")
    @Log(title = "模型属性", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ModelAttribute modelAttribute)
    {
        List<ModelAttribute> list = modelAttributeService.selectModelAttributeList(modelAttribute);
        ExcelUtil<ModelAttribute> util = new ExcelUtil<ModelAttribute>(ModelAttribute.class);
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
    public AjaxResult add(@RequestBody @Validated ModelAttribute modelAttribute)
    {
        return toAjax(modelAttributeService.insertModelAttribute(modelAttribute));
    }

    /**
     * 修改模型属性
     */
    @PreAuthorize("@ss.hasPermi('model:attribute:edit')")
    @Log(title = "模型属性", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody  @Validated ModelAttribute modelAttribute)
    {
        return toAjax(modelAttributeService.updateModelAttribute(modelAttribute));
    }

    /**
     * 删除模型属性
     */
    @PreAuthorize("@ss.hasPermi('model:attribute:remove')")
    @Log(title = "模型属性", businessType = BusinessType.DELETE)
	@DeleteMapping("/{modelAttrIds}")
    public AjaxResult remove(@PathVariable Integer[] modelAttrIds)
    {
        return toAjax(modelAttributeService.deleteModelAttributeByModelAttrIds(modelAttrIds));
    }
}
