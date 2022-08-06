package com.ruoyi.project.model.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.project.model.domain.Model;
import com.ruoyi.project.model.domain.ModelCategory;
import com.ruoyi.project.model.domain.ModelInterface;
import com.ruoyi.project.model.mapper.ModelCategoryMapper;
import com.ruoyi.project.model.service.IModelCategoryService;
import com.ruoyi.project.model.service.IModelService;
import io.mybatis.mapper.example.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dengsd
 * @date 2022-07-29
 */

@Service
public class IModelCategoryServiceImpl implements IModelCategoryService {
    @Autowired
    ModelCategoryMapper modelCategoryMapper;

    @Autowired
    @Lazy
    IModelService modelService;

    @Override
    public List<ModelCategory> list(ModelCategory modelCategory) {
        Example<ModelCategory> example = new Example<>();
        Example.Criteria<ModelCategory> criteria = example.createCriteria();
        if (StringUtils.isNotBlank(modelCategory.getModelCateName())) {
            criteria.andLike(ModelCategory::getModelCateName, modelCategory.getModelCateName());
        }
        if (!Objects.isNull(modelCategory.getModelCateId())) {
            criteria.andEqualTo(ModelCategory::getModelCateId, modelCategory.getModelCateId());
        }
        if (!Objects.isNull(modelCategory.getParentId())) {
            criteria.andEqualTo(ModelCategory::getParentId, modelCategory.getParentId());
        }
        if (!Objects.isNull(modelCategory.getModelCateLevel())) {
            criteria.andEqualTo(ModelCategory::getModelCateLevel, modelCategory.getModelCateLevel());
        }

        return modelCategoryMapper.selectByExample(example);

    }

    @Override
    public ModelCategory getInfo(Integer modelCateId) {
        return modelCategoryMapper.selectByPrimaryKey(modelCateId).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(ModelCategory modelCategory) {
        verify(modelCategory);
        return modelCategoryMapper.insertSelective(modelCategory) > 0;
    }

    @Override
    public boolean exists(ModelCategory modelCategory) {
        Example<ModelCategory> example = new Example<>();
        example.selectColumns(ModelCategory::getModelCateId);
        Example.Criteria<ModelCategory> criteria = example.createCriteria();
        criteria.andEqualTo(ModelCategory::getModelCateName, modelCategory.getModelCateName());
        if (Objects.nonNull(modelCategory.getModelCateId())) {
            criteria.andNotEqualTo(ModelCategory::getModelCateId, modelCategory.getModelCateId());
        }
        if (Objects.nonNull(modelCategory.getParentId())) {
            criteria.andEqualTo(ModelCategory::getParentId, modelCategory.getParentId());
        }
        return Optional.of(modelCategoryMapper.selectByExample(example)).orElse(Collections.EMPTY_LIST).size() > 0;
    }

    @Override
    public void verifyDelete(Integer... cateIds) {
        if (StringUtils.isEmpty(cateIds)) return;
        for (Integer cateId : cateIds) {
            Model model = new Model();
            model.setCateId(cateId);
            if (modelService.exists(model)) {
                throw new ServiceException("当前分类下有模型数据,不能删除");
            }
            if (this.hasChrildren(cateId)) {
                throw new ServiceException("当前分类有下级,不能删除");
            }
        }
    }

    @Override
    public void verify(ModelCategory modelCategory) {
        // 处理父级
        ModelCategory parentCategory = null;
        if (StringUtils.isNotNull(modelCategory.getParentId())) {
            parentCategory = getInfo(modelCategory.getParentId());
        }
        if (exists(modelCategory)) throw new ServiceException("分类名称已存在");
        String cateName = modelCategory.getModelCateName();
        String pinYin = StringUtils.convertToPinYin(cateName);
        if (StringUtils.isNotNull(parentCategory)) {
            modelCategory.setModelCateLevel(parentCategory.getModelCateLevel() + 1);
            String code = parentCategory.getCode();
            if(StringUtils.isNotEmpty(code)){
                pinYin =    code.concat("_").concat(pinYin);
            }
            //祖籍
            if(StringUtils.isNotEmpty(parentCategory.getOrigins())){
                cateName =   parentCategory.getOrigins().concat(",").concat(cateName);
            }
            //父类是否存在子类
            boolean haschrildren = hasChrildren(modelCategory.getParentId());
            if(!haschrildren){
                // 创建本体
                ModelCategory categorycopy = new ModelCategory();
                BeanUtils.copyBeanProp(categorycopy,modelCategory);
                categorycopy.setModelCateName("本体");
                categorycopy.setOrderNum(0);
                if (modelCategoryMapper.insertSelective(categorycopy) < 1){
                    throw new ServiceException("保存异常");
                }

            }

        }
        //处理接口名称
        if(StringUtils.isNotEmpty(modelCategory.getInterfaces())){
            List<ModelInterface> interfaces = modelCategory.getInterfaces();
            interfaces.stream().forEach(m->m.setCode(StringUtils.convertToPinYin(m.getName())));
        }
        modelCategory.setCode(pinYin);
        modelCategory.setOrigins(cateName);
        // 是否有子类
        modelCategory.setHaschild(hasChrildren(modelCategory.getModelCateId()));

    }

    public boolean hasChrildren(Integer cateId) {
        Example<ModelCategory> example = new Example<>();
        example.selectColumns(ModelCategory::getModelCateId);
        Example.Criteria<ModelCategory> criteria = example.createCriteria();
        criteria.andEqualTo(ModelCategory::getParentId, cateId);
        return Optional.of(modelCategoryMapper.selectByExample(example)).orElse(Collections.EMPTY_LIST).size() > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(ModelCategory modelCategory) {
        verify(modelCategory);
        return modelCategoryMapper.updateByPrimaryKeySelective(modelCategory) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer... modelCateIds) {
        if (StringUtils.isEmpty(modelCateIds)) return false;
        //是否可以删除
        verifyDelete(modelCateIds);
        return modelCategoryMapper.deleteByFieldList(ModelCategory::getModelCateId, Stream.of(modelCateIds).collect(Collectors.toList())) > 0;
    }

}
