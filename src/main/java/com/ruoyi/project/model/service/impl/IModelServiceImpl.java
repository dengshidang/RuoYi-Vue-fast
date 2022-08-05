package com.ruoyi.project.model.service.impl;

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
        criteria.andEqualTo(Model::getCateId,model.getCateId());
        return false;
    }
}
