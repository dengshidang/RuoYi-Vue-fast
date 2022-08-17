package com.ruoyi.project.model.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.model.domain.Model;
import com.ruoyi.project.model.mapper.ModelMapper;
import com.ruoyi.project.model.service.IModelService;
import io.mybatis.mapper.example.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    /**
     * 查询模型
     *
     * @param modelId 模型主键
     * @return 模型
     */
    @Override
    public Model selectModelByModelId(Integer modelId)
    {
        return modelMapper.selectModelByModelId(modelId);
    }

    /**
     * 查询模型列表
     *
     * @param model 模型
     * @return 模型
     */
    @Override
    public List<Model> selectModelList(Model model)
    {
        return modelMapper.selectModelList(model);
    }

    /**
     * 新增模型
     *
     * @param model 模型
     * @return 结果
     */
    @Override
    public int insertModel(Model model)
    {
        model.setCreateTime(DateUtils.getNowDate());
        return modelMapper.insertModel(model);
    }

    /**
     * 修改模型
     *
     * @param model 模型
     * @return 结果
     */
    @Override
    public int updateModel(Model model)
    {
        model.setUpdateTime(DateUtils.getNowDate());
        return modelMapper.updateModel(model);
    }

    /**
     * 批量删除模型
     *
     * @param modelIds 需要删除的模型主键
     * @return 结果
     */
    @Override
    public int deleteModelByModelIds(Integer[] modelIds)
    {
        return modelMapper.deleteModelByModelIds(modelIds);
    }

    /**
     * 删除模型信息
     *
     * @param modelId 模型主键
     * @return 结果
     */
    @Override
    public int deleteModelByModelId(Integer modelId)
    {
        return modelMapper.deleteModelByModelId(modelId);
    }
}
