package com.ruoyi.project.model.mapper;

import com.ruoyi.project.model.domain.Model;
import io.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dengsd
 * @date 2022/7/29 16:23
 */
@Mapper
public interface ModelMapper extends BaseMapper<Model,Integer> {
}
