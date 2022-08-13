package com.ruoyi.project.prod.mapper;

import com.ruoyi.project.prod.domain.ProdCategory;
import io.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dengsd
 * @date 2022/8/12 16:05
 */
@Mapper
public interface ProdCategoryMapper extends BaseMapper<ProdCategory,Integer> {
    List<ProdCategory> list(ProdCategory prodCategory);

}
