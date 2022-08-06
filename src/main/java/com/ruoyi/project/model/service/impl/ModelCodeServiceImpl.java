package com.ruoyi.project.model.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.model.domain.Model;
import com.ruoyi.project.model.domain.ModelCategory;
import com.ruoyi.project.model.domain.ModelCode;
import com.ruoyi.project.model.enums.EModelCodeSeparator;
import com.ruoyi.project.model.mapper.ModelCodeMapper;
import com.ruoyi.project.model.service.IModelCategoryService;
import com.ruoyi.project.model.service.IModelCodeService;
import com.ruoyi.project.model.service.IModelService;
import io.mybatis.mapper.example.Example;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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
        Example<ModelCode> example = modelCodeMapper.wrapper()
                .eq(StringUtils.isNotEmpty(modelCode.getModelCode()), ModelCode::getModelCode, modelCode.getModelCode())
                .eq(ObjectUtils.allNotNull(modelCode.getModelCateId()), ModelCode::getModelCateId, modelCode.getModelCateId())
                .like(StringUtils.isNotEmpty(modelCode.getInterfaceName()), ModelCode::getModelCode, modelCode.getModelCode())
                .like(StringUtils.isNotEmpty(modelCode.getInterfaceCode()), ModelCode::getInterfaceCode, modelCode.getInterfaceCode())
                .orderByDesc(ModelCode::getModelCode, ModelCode::getCreateTime)
                .example();
        return modelCodeMapper.selectByExample(example);
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
        if(StringUtils.isNotEmpty(modelCode.getModelCode())){
            String[] split = modelCode.getModelCode().split(EModelCodeSeparator.DEFAULT.getSeparator());
            split[0] = dbcategory.getCode();
            modelCode.setModelCode(Arrays.stream(split).collect(Collectors.joining(EModelCodeSeparator.DEFAULT.getSeparator())));
        }else{
            modelCode.setModelCode(dbcategory.getCode());
        }
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

}
