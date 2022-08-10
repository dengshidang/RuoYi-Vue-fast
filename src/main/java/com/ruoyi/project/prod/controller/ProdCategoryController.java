package com.ruoyi.project.prod.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.TreeUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.model.domain.ModelCategory;
import com.ruoyi.project.model.service.IModelCategoryService;
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
    private IModelCategoryService modelCategoryService;

    /**
     * 查询产品分类列表
     */
    @PreAuthorize("@ss.hasPermi('prod:category:list')")
    @GetMapping("/list")
    public AjaxResult list(ModelCategory modelCategory)
    {
        List<ModelCategory> list = modelCategoryService.list(modelCategory);
        Integer modelCateId = modelCategory.getModelCateId();
        //list 转成tree
       return AjaxResult.success(TreeUtil.toTree(list, StringUtils.isNull(modelCateId)?0:modelCateId,ModelCategory::getParentId,ModelCategory::getModelCateId,ModelCategory::getOrderNum,ModelCategory::setChildren));
//       return AjaxResult.success(list);
    }


    /**
     * 获取产品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('prod:category:query')")
    @GetMapping(value = "/{modelCateId}")
    public AjaxResult getInfo(@PathVariable("modelCateId") Integer modelCateId)
    {
        return AjaxResult.success(modelCategoryService.getInfo(modelCateId));
    }
    @PreAuthorize("@ss.hasPermi('prod:category:edit')")
    @GetMapping(value = "/usedInterface")
    public AjaxResult usedInterface(ModelCategory modelCategory)
    {
        return AjaxResult.success(modelCategoryService.usedInterfaceCode(modelCategory.getModelCateId(),modelCategory.getInterfaceCode()));
    }

    /**
     * 新增产品分类
     */
    @PreAuthorize("@ss.hasPermi('prod:category:add')")
    @Log(title = "产品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated  ModelCategory modelCategory)
    {
        return toAjax(modelCategoryService.add(modelCategory));
    }

    /**
     * 修改产品分类
     */
    @PreAuthorize("@ss.hasPermi('prod:category:edit')")
    @Log(title = "产品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated ModelCategory modelCategory)
    {
        return toAjax(modelCategoryService.edit(modelCategory));
    }

    /**
     * 删除产品分类
     */
    @PreAuthorize("@ss.hasPermi('prod:category:remove')")
    @Log(title = "产品分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{modelCateIds}")
    public AjaxResult remove(@PathVariable Integer[] modelCateIds)
    {
        return toAjax(modelCategoryService.delete(modelCateIds));
    }
}
