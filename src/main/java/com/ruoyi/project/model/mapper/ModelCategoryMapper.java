package com.ruoyi.project.model.mapper;

import com.ruoyi.project.model.domain.ModelCategory;
import io.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author dengsd
 * @date 2022-07-29
 */
@Mapper
public interface ModelCategoryMapper extends BaseMapper<ModelCategory, Integer> {
    @Select("select count(*) from model_category where find_in_set(#{modelAttrId},attr_ids) ")
    int existsByAttrId(@Param("modelAttrId") Integer modelAttrId);
}
