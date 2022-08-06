package com.ruoyi.project.model.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.model.domain.Model;
import com.ruoyi.project.model.mapper.ModelMapper;
import com.ruoyi.project.model.service.IModelService;
import io.mybatis.mapper.example.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dengsd
 * @date 2022/7/29 16:15
 */
@Service
public class IModelServiceImpl implements IModelService {
    @Autowired
    ModelMapper modelMapper;
    @Override
    public boolean exists(Model model) {
        Example<Model> example = new Example<>();
        Example.Criteria<Model> criteria = example.createCriteria();
        if(StringUtils.isNotNull(model.getModelId())){
            criteria.andEqualTo(Model::getModelId,model.getModelId());
        }
        if(StringUtils.isNotNull(model.getCateId())){
            criteria.andEqualTo(Model::getCateId,model.getCateId());
        }
        if(StringUtils.isNotEmpty(model.getModelCode())){
            criteria.andEqualTo(Model::getModelCode,model.getModelCode());
        }
        return modelMapper.countByExample(example) > 0;
    }
}
