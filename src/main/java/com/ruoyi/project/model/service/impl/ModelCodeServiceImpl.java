package com.ruoyi.project.model.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.model.domain.Model;
import com.ruoyi.project.model.domain.ModelCategory;
import com.ruoyi.project.model.domain.ModelCode;
import com.ruoyi.project.model.mapper.ModelCodeMapper;
import com.ruoyi.project.model.service.IModelCategoryService;
import com.ruoyi.project.model.service.IModelCodeService;
import com.ruoyi.project.model.service.IModelService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 模型编码Service业务层处理
 *
 * @author dengsd
 * @date 2022-08-06
 */
@Service
public class ModelCodeServiceImpl implements IModelCodeService {
    @Autowired
    private ModelCodeMapper modelCodeMapper;
    @Autowired
    IModelCategoryService modelCategoryService;
    @Autowired
    @Lazy
    IModelService modelService;

    /**
     * 查询模型编码
     *
     * @param modeCode 模型编码主键
     * @return 模型编码
     */
    @Override
    public ModelCode selectModelCodeByModeCode(String modeCode) {
        return modelCodeMapper.selectByPrimaryKey(modeCode).orElse(null);
    }

    /**
     * 查询模型编码列表
     *
     * @param modelCode 模型编码
     * @return 模型编码
     */
    @Override
    public List<ModelCode> selectModelCodeList(ModelCode modelCode) {
        String modelCateName = modelCode.getModelCateName();
       if(StringUtils.isNotEmpty(modelCateName)){
            String[] split = modelCateName.split("/");
            if(split.length > 1){
                modelCode.setParentCateName(split[0]);
                modelCode.setModelCateName(split[1]);
            }
        }
        List<ModelCode> dbmodelCodes = modelCodeMapper.list(modelCode);
        if (StringUtils.isNotEmpty(dbmodelCodes)) {
            ModelCategory category = new ModelCategory();
            category.setCateIds(dbmodelCodes.stream().map(ModelCode::getModelCateId).collect(Collectors.toSet()));
            List<ModelCategory> categoryList = modelCategoryService.list(category, ModelCategory::getModelCateId, ModelCategory::getOrigins);
            if (StringUtils.isNotEmpty(categoryList)) {
                Map<Integer, ModelCategory> categoryMap = categoryList.stream().collect(Collectors.toMap(ModelCategory::getModelCateId, Function.identity()));
                dbmodelCodes.forEach(f ->
                {
                    ModelCategory item = categoryMap.get(f.getModelCateId());
                    if (StringUtils.isNotNull(item)) {
                        f.setModelCateName(item.getOrigins().replaceAll(",", "/"));
                    }
                });
            }
        }
        return dbmodelCodes;
    }

    /**
     * 新增模型编码
     *
     * @param modelCode 模型编码
     * @return 结果
     */
    @Override
    public int insertModelCode(ModelCode modelCode) {
        modelCode.setCreateTime(DateUtils.getNowDate());
        verify(modelCode);
        return modelCodeMapper.insertModelCode(modelCode);
    }

    private void verify(ModelCode modelCode) {
        ModelCategory dbcategory = modelCategoryService.getInfo(modelCode.getModelCateId());
        if (StringUtils.isNull(dbcategory)) {
            throw new ServiceException("模型分类不存在或已删除");
        }
        if (StringUtils.isEmpty(modelCode.getModelCode())) {
            modelCode.setModelCode(dbcategory.getCode());
        }
        // 是否存在
        if (exists(modelCode)) {
            throw new ServiceException("编码已存在");
        }
    }

    public boolean exists(ModelCode modelCode) {
        return modelCodeMapper.countByExample(modelCodeMapper.wrapper()
//                .eq(StringUtils.isNotEmpty(modelCode.getModelName()), ModelCode::getModelName, modelCode.getModelName())
                .ne(StringUtils.isNotEmpty(modelCode.getModelCode()), ModelCode::getModelCode, modelCode.getModelCode())
                .eq(ObjectUtils.isNotEmpty(modelCode.getModelCateId()), ModelCode::getModelCateId, modelCode.getModelCateId())
                .eq(StringUtils.isNotEmpty(modelCode.getInterfaceCode()), ModelCode::getInterfaceCode, modelCode.getInterfaceCode())
                .eq(StringUtils.isNotEmpty(modelCode.getModelAttrIds()), ModelCode::getModelAttrIds, modelCode.getModelAttrIds())
                .eq(StringUtils.isNotEmpty(modelCode.getModelAttrValues()), ModelCode::getModelAttrValues, modelCode.getModelAttrValues())
                .example()) > 0;
    }

    /**
     * 修改模型编码
     *
     * @param modelCode 模型编码
     * @return 结果
     */
    @Override
    public int updateModelCode(ModelCode modelCode) {
        modelCode.setUpdateTime(DateUtils.getNowDate());
        verify(modelCode);
//        // 组合码
//        String modelGroup = dbmodelCode.getModelGroup();
//        if (StringUtils.isNotEmpty(modelGroup)) {
//            if (StringUtils.isNotEmpty(modelCode.getModelGroup())) {
//                String[] grouparr = modelCode.getModelGroup().split(DOUHAO.getSeparator());
//                if (grouparr.length < 2) {
//                    // 清除组合码
//                    modelCode.setModelGroup("");
//                    List<String> list = Stream.of(modelGroup.split(DOUHAO.getSeparator())).collect(Collectors.toList());
//                    list.removeIf(f -> f.equals(modelCode.getModelCode()));
//                    // 解除
//                    deleteGroup(list);
//                }
//            }
//        }
        return modelCodeMapper.updateByPrimaryKey(modelCode);
    }

    @Override
    public void verifyDelete(String... modelCodes) {
        if (StringUtils.isEmpty(modelCodes)) return;
        for (String modelCode : modelCodes) {
            Model model = new Model();
            model.setModelCode(modelCode);
            if (modelService.exists(model)) {
                throw new ServiceException("当前编码有模型数据,不能删除");
            }

        }
    }

    /**
     * 批量删除模型编码
     *
     * @param modeCodes 需要删除的模型编码主键
     * @return 结果
     */
    @Override
    public int deleteModelCodeByModeCodes(String... modeCodes) {
        verifyDelete(modeCodes);
        return modelCodeMapper.deleteByFieldList(ModelCode::getModelCode, Stream.of(modeCodes).collect(Collectors.toList()));
    }

    @Override
    @Deprecated
    public boolean saveToGroup(String[] modelCodes) {
        if (ObjectUtils.isEmpty(modelCodes) || modelCodes.length < 2) throw new ServiceException("至少添加两条记录");
        // 是否存在
        for (String code : modelCodes) {
            int exists = modelCodeMapper.existsByModelGroup(code);
            if (exists > 0) {
                throw new ServiceException("请勿重复添加分组:" + code);
            }
        }
        // 相同的接口才能组合
        ModelCode modelCode = new ModelCode();
        List<String> codes = Arrays.stream(modelCodes).collect(Collectors.toList());
        String modelgroup = Arrays.stream(modelCodes).collect(Collectors.joining(","));
        modelCode.setModelGroup(modelgroup);
        return modelCodeMapper.updateByExampleSelective(modelCode, modelCodeMapper.wrapper()
                .in(ModelCode::getModelCode, codes)
                .example()) > 0;

    }

    @Override
    @Deprecated
    public boolean deleteGroup(List<String> modelCodes) {
        if (ObjectUtils.isEmpty(modelCodes)) return false;
        ModelCode modelCode = new ModelCode();
        modelCode.setModelGroup("");
        return modelCodeMapper.updateByExampleSelective(modelCode, modelCodeMapper.wrapper()
                .in(ModelCode::getModelCode, modelCodes)
                .example()) > 0;

    }
}
