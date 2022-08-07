package com.ruoyi.project.model.mapper;

import com.ruoyi.project.model.domain.ModelCode;
import io.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 模型编码Mapper接口
 *
 * @author dengsd
 * @date 2022-08-06
 */
public interface ModelCodeMapper extends BaseMapper<ModelCode, String> {

    int insertModelCode(ModelCode modelCode);

    @Select("select count(*) from model_code where find_in_set(#{modelAttrId},model_attr_ids)")
    int existsByAttrId(@Param("modelAttrId") Integer modelAttrId);

    List<ModelCode> list(ModelCode modelCode);
}
