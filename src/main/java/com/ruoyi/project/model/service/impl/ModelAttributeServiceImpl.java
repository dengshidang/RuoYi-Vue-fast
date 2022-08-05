package com.ruoyi.project.model.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import io.mybatis.mapper.example.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.model.mapper.ModelAttributeMapper;
import com.ruoyi.project.model.domain.ModelAttribute;
import com.ruoyi.project.model.service.IModelAttributeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 模型属性Service业务层处理
 *
 * @author dengsd
 * @date 2022-07-31
 */
@Service
public class ModelAttributeServiceImpl implements IModelAttributeService
{
    @Autowired
    private ModelAttributeMapper modelAttributeMapper;

    /**
     * 查询模型属性
     *
     * @param modelAttrId 模型属性主键
     * @return 模型属性
     */
    @Override
    public ModelAttribute selectModelAttributeByModelAttrId(Integer modelAttrId)
    {
        ModelAttribute modelAttribute = modelAttributeMapper.selectByPrimaryKey(modelAttrId).orElse(null);
        return modelAttribute;
    }

    /**
     * 查询模型属性列表
     *
     * @param modelAttribute 模型属性
     * @return 模型属性
     */
    @Override
    public List<ModelAttribute> selectModelAttributeList(ModelAttribute modelAttribute)
    {
        Example<ModelAttribute> example = new Example<>();
        Example.Criteria<ModelAttribute> criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(modelAttribute.getAttrName())){
            criteria.andLike(ModelAttribute::getAttrName,modelAttribute.getAttrName());
        }
        if(StringUtils.isNotEmpty(modelAttribute.getAttrCode())){
            criteria.andLike(ModelAttribute::getAttrCode,modelAttribute.getAttrCode());
        }
        if(StringUtils.isNotEmpty(modelAttribute.getAttrType())){
            criteria.andEqualTo(ModelAttribute::getAttrType,modelAttribute.getAttrType());
        }
        if(StringUtils.isNotEmpty(modelAttribute.getAttrGroup())){
            criteria.andEqualTo(ModelAttribute::getAttrGroup,modelAttribute.getAttrGroup());
        }
        example.orderByDesc(ModelAttribute::getAttrId);
        return modelAttributeMapper.selectByExample(example);
    }

    /**
     * 新增模型属性
     *
     * @param modelAttribute 模型属性
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertModelAttribute(ModelAttribute modelAttribute)
    {
        modelAttribute.setCreateTime(DateUtils.getNowDate());
        verify(modelAttribute);
        return modelAttributeMapper.insertSelective(modelAttribute);
    }

    private void verify(ModelAttribute modelAttribute) {
        modelAttribute.setAttrCode(StringUtils.convertToPinYin(modelAttribute.getAttrName()));
    }

    /**
     * 修改模型属性
     *
     * @param modelAttribute 模型属性
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateModelAttribute(ModelAttribute modelAttribute)
    {
        modelAttribute.setUpdateTime(DateUtils.getNowDate());
        verify(modelAttribute);
        return modelAttributeMapper.updateByPrimaryKey(modelAttribute);
    }

    /**
     * 批量删除模型属性
     *
     * @param modelAttrIds 需要删除的模型属性主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)

    public int deleteModelAttributeByModelAttrIds(Integer... modelAttrIds)
    {
        candelete(modelAttrIds);
        return modelAttributeMapper.deleteByFieldList(ModelAttribute::getAttrId,
                Stream.of(modelAttrIds).collect(Collectors.toList()));
    }

    /**
     * 删除模型属性信息
     *
     * @param modelAttrId 模型属性主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteModelAttributeByModelAttrId(Integer modelAttrId)
    {
        return deleteModelAttributeByModelAttrIds(modelAttrId);
    }

    @Override
    public boolean candelete(Integer... modelAttrIds) {

        return false;
    }
}
