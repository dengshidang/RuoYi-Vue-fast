package com.ruoyi.project.model.service.impl;

import com.ruoyi.common.enums.ECategoryGroup;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.model.domain.Attribute;
import com.ruoyi.project.model.mapper.ModelAttributeMapper;
import com.ruoyi.project.model.mapper.ModelCodeMapper;
import com.ruoyi.project.model.service.IModelAttributeService;
import io.mybatis.mapper.example.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

/**
 * 模型属性Service业务层处理
 *
 * @author dengsd
 * @date 2022-07-31
 */
@Service
public class ModelAttributeServiceImpl implements IModelAttributeService {
    @Autowired
    private ModelAttributeMapper modelAttributeMapper;
    @Autowired
    ModelCodeMapper modelCodeMapper;

    /**
     * 查询模型属性
     *
     * @param modelAttrId 模型属性主键
     * @return 模型属性
     */
    @Override
    public Attribute selectModelAttributeByModelAttrId(Integer modelAttrId) {
        Attribute attribute = modelAttributeMapper.selectByPrimaryKey(modelAttrId).orElse(null);
        return attribute;
    }

    /**
     * 查询模型属性列表
      * 这个排序不能修改
     * @param attribute 模型属性
     * @return 模型属性
     */
    @Override
    public List<Attribute> selectModelAttributeList(Attribute attribute) {
        return modelAttributeMapper.list(attribute);
    }

    /**
     * 新增模型属性
     *
     * @param attribute 模型属性
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertModelAttribute(Attribute attribute) {
        attribute.setCreateTime(DateUtils.getNowDate());
        verify(attribute);
        return modelAttributeMapper.insertSelective(attribute);
    }

    private void verify(Attribute attribute) {
        attribute.setAttrCode(StringUtils.convertToPinYin(attribute.getAttrName()));
    }

    /**
     * 修改模型属性
     *
     * @param attribute 模型属性
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateModelAttribute(Attribute attribute) {
        attribute.setUpdateTime(DateUtils.getNowDate());
        verify(attribute);
        return modelAttributeMapper.updateByPrimaryKey(attribute);
    }

    /**
     * 批量删除模型属性
     *
     * @param modelAttrId 需要删除的模型属性主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteModelAttributeByModelAttrId(Integer  modelAttrId) {
        Attribute dbattribute = modelAttributeMapper.selectByPrimaryKey(modelAttrId).orElseThrow(() -> new ServiceException("记录不存在或已删除"));
        if(!candelete(ECategoryGroup.verify(dbattribute.getAttrGroup()), modelAttrId)){
            throw new ServiceException("已被使用,无法删除");
        }
        return modelAttributeMapper.deleteByPrimaryKey(modelAttrId);
    }
    @Override
    public boolean candelete(ECategoryGroup group, Integer  modelAttrId) {
        switch (group) {
            case MODEL:
                return modelCodeMapper.existsByAttrId(modelAttrId) < 1;
            // todo:补全
//            case PROD:
//                break;
//            case MATERIAL:
//                break;
            default:
                throw new ServiceException("不可能到这里");
        }
    }

}
