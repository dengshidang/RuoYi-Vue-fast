package com.ruoyi.project.model.service;

import com.ruoyi.common.enums.ECategoryGroup;
import com.ruoyi.project.model.domain.Attribute;
import io.mybatis.mapper.example.Example;

import java.util.List;
import java.util.function.Consumer;

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
     Attribute selectModelAttributeByModelAttrId(Integer modelAttrId);

    /**
     * 查询模型属性列表
     *
     * @param attribute 模型属性
     * @return 模型属性集合
     */
     List<Attribute> selectModelAttributeList(Attribute attribute, Consumer<Example<Attribute>>...consumers);

    /**
     * 新增模型属性
     *
     * @param attribute 模型属性
     * @return 结果
     */
    public int insertModelAttribute(Attribute attribute);

    /**
     * 修改模型属性
     *
     * @param attribute 模型属性
     * @return 结果
     */
     int updateModelAttribute(Attribute attribute);

    /**
     * 批量删除模型属性
     *
     * @param modelAttrIds 需要删除的模型属性主键集合
     * @return 结果
     */
     int deleteModelAttributeByModelAttrId(Integer modelAttrIds);

    /**
     * 是否可以删除
     * @param group
     * @param modelAttrId
     * @return
     */
    boolean candelete(ECategoryGroup group, Integer  modelAttrId);

}
