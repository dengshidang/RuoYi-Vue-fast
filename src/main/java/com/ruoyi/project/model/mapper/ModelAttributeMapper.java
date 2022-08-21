package com.ruoyi.project.model.mapper;

import com.ruoyi.project.model.domain.Attribute;
import io.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * 模型属性Mapper接口
 *
 * @author dengsd
 * @date 2022-07-31
 */
public interface ModelAttributeMapper  extends BaseMapper<Attribute,Integer>
{

    List<Attribute> list(Attribute attribute);
}