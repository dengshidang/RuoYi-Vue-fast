package com.ruoyi.project.model.controller;

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

import java.util.*;
import java.util.stream.Collectors;

/**
 * 模型分类Controller
 *
 * @author dengsd
 * @date 2022-07-29
 */
@RestController
@RequestMapping("/model/category")
public class ModelCategoryController extends BaseController
{
    @Autowired
    private IModelCategoryService modelCategoryService;

    /**
     * 查询模型分类列表
     */
    @PreAuthorize("@ss.hasPermi('model:category:list')")
    @GetMapping("/list")
    public AjaxResult list(ModelCategory modelCategory)
    {
        List<ModelCategory> list = modelCategoryService.list(modelCategory);
        Integer modelCateId = modelCategory.getModelCateId();
        //list 转成tree
       return AjaxResult.success(TreeUtil.toTree(list, StringUtils.isNull(modelCateId)?0:modelCateId,ModelCategory::getParentId,ModelCategory::getModelCateId,ModelCategory::getOrderNum,ModelCategory::setChildren));
//       return AjaxResult.success(list);
    }
    @PreAuthorize("@ss.hasPermi('model:category:list')")
    @GetMapping("/option")
    public AjaxResult option(ModelCategory modelCategory)
    {
        if(StringUtils.isNull(modelCategory.getParentId())){
            modelCategory.setParentId(0);
        }
        List<ModelCategory> list = modelCategoryService.list(modelCategory);
        return AjaxResult.success(Optional.ofNullable(list).orElse(Collections.emptyList())
                .parallelStream().map(m->{
                    Map<String,Object> item = new HashMap<>();
                    item.put("key",m.getCode());
                    item.put("val",m.getModelCateName());
                    return item;
                }).collect(Collectors.toList()));
    }
    /**
     * 获取模型分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('model:category:query')")
    @GetMapping(value = "/{modelCateId}")
    public AjaxResult getInfo(@PathVariable("modelCateId") Integer modelCateId)
    {
        return AjaxResult.success(modelCategoryService.getInfo(modelCateId));
    }
    @PreAuthorize("@ss.hasPermi('model:category:edit')")
    @GetMapping(value = "/usedInterface")
    public AjaxResult usedInterface(ModelCategory modelCategory)
    {
        return AjaxResult.success(modelCategoryService.usedInterfaceCode(modelCategory.getModelCateId(),modelCategory.getInterfaceCode()));
    }

    /**
     * 新增模型分类
     */
    @PreAuthorize("@ss.hasPermi('model:category:add')")
    @Log(title = "模型分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated  ModelCategory modelCategory)
    {
        return toAjax(modelCategoryService.add(modelCategory));
    }

    /**
     * 修改模型分类
     */
    @PreAuthorize("@ss.hasPermi('model:category:edit')")
    @Log(title = "模型分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated ModelCategory modelCategory)
    {
        return toAjax(modelCategoryService.edit(modelCategory));
    }

    /**
     * 删除模型分类
     */
    @PreAuthorize("@ss.hasPermi('model:category:remove')")
    @Log(title = "模型分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{modelCateIds}")
    public AjaxResult remove(@PathVariable Integer[] modelCateIds)
    {
        return toAjax(modelCategoryService.delete(modelCateIds));
    }
}
