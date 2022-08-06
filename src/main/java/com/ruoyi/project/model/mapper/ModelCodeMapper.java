package com.ruoyi.project.model.mapper;

import com.ruoyi.project.model.domain.ModelCode;
import io.mybatis.mapper.BaseMapper;

/**
 * 模型编码Mapper接口
 *
 * @author dengsd
 * @date 2022-08-06
 */
public interface ModelCodeMapper  extends BaseMapper<ModelCode,String> {

    int insertModelCode(ModelCode modelCode);
}
