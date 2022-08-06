package com.ruoyi.project.model.service;

import com.ruoyi.project.model.domain.ModelAttribute;

import java.util.List;

/**
 * 模型属性Service接口
 *
 * @author dengsd
 * @date 2022-07-31
 */
public interface IModelAttributeService
{
    /**
     * 查询模型属性
     *
     * @param modelAttrId 模型属性主键
     * @return 模型属性
     */
     ModelAttribute selectModelAttributeByModelAttrId(Integer modelAttrId);

    /**
     * 查询模型属性列表
     *
     * @param modelAttribute 模型属性
     * @return 模型属性集合
     */
     List<ModelAttribute> selectModelAttributeList(ModelAttribute modelAttribute);

    /**
     * 新增模型属性
     *
     * @param modelAttribute 模型属性
     * @return 结果
     */
    public int insertModelAttribute(ModelAttribute modelAttribute);

    /**
     * 修改模型属性
     *
     * @param modelAttribute 模型属性
     * @return 结果
     */
     int updateModelAttribute(ModelAttribute modelAttribute);

    /**
     * 批量删除模型属性
     *
     * @param modelAttrIds 需要删除的模型属性主键集合
     * @return 结果
     */
     int deleteModelAttributeByModelAttrIds(Integer... modelAttrIds);

    /**
     * 是否可以删除
     *
     */
    boolean candelete(Integer...modelAttrIds);
}
