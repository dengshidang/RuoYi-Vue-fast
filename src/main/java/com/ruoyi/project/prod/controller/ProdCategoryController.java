package com.ruoyi.project.prod.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.TreeUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.model.domain.ModelCategory;
import com.ruoyi.project.model.service.IModelCategoryService;
import com.ruoyi.project.prod.domain.ProdCategory;
import com.ruoyi.project.prod.service.IProdCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品分类Controller
 *
 * @author dengsd
 * @date 2022-07-29
 */
@RestController
@RequestMapping("/prod/category")
public class ProdCategoryController extends BaseController
{
    @Autowired
    private IProdCategoryService prodCategoryService;

    /**
     * 查询产品分类列表
     */
    @PreAuthorize("@ss.hasPermi('prod:category:list')")
    @GetMapping("/list")
    public AjaxResult list(ProdCategory prodCategory)
    {
        List<ProdCategory> list = prodCategoryService.list(prodCategory);
        Integer prodCateId = prodCategory.getProdCateId();
       return AjaxResult.success(TreeUtil.toTree(list, StringUtils.isNull(prodCateId)?0:prodCateId,ProdCategory::getParentId,ProdCategory::getProdCateId,ProdCategory::getOrderNum,ProdCategory::setChildren));
    }


    /**
     * 获取产品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('prod:category:query')")
    @GetMapping(value = "/{prodCateId}")
    public AjaxResult getInfo(@PathVariable("prodCateId") Integer prodCateId)
    {
        return AjaxResult.success(prodCategoryService.getInfo(prodCateId));
    }


    /**
     * 新增产品分类
     */
    @PreAuthorize("@ss.hasPermi('prod:category:add')")
    @Log(title = "产品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated  ProdCategory prodCategory)
    {
        return toAjax(prodCategoryService.add(prodCategory));
    }

    /**
     * 修改产品分类
     */
    @PreAuthorize("@ss.hasPermi('prod:category:edit')")
    @Log(title = "产品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated ProdCategory prodCategory)
    {
        return toAjax(prodCategoryService.edit(prodCategory));
    }

    /**
     * 删除产品分类
     */
    @PreAuthorize("@ss.hasPermi('prod:category:remove')")
    @Log(title = "产品分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{prodCateIds}")
    public AjaxResult remove(@PathVariable Integer[] prodCateIds)
    {
        return toAjax(prodCategoryService.delete(prodCateIds));
    }
}
