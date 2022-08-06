package com.ruoyi.project.model.service;

import com.ruoyi.project.model.domain.ModelCode;

import java.util.List;

/**
 * 模型编码Service接口
 *
 * @author dengsd
 * @date 2022-08-06
 */
public interface IModelCodeService
{
    /**
     * 查询模型编码
     *
     * @param modeCode 模型编码主键
     * @return 模型编码
     */
     ModelCode selectModelCodeByModeCode(String modeCode);

    /**
     * 查询模型编码列表
     *
     * @param modelCode 模型编码
     * @return 模型编码集合
     */
     List<ModelCode> selectModelCodeList(ModelCode modelCode);

    /**
     * 新增模型编码
     *
     * @param modelCode 模型编码
     * @return 结果
     */
     int insertModelCode(ModelCode modelCode);

    /**
     * 修改模型编码
     *
     * @param modelCode 模型编码
     * @return 结果
     */
     int updateModelCode(ModelCode modelCode);

    /**
     * 校验是否可以删除
     * @param modelCode
     */
    void verifyDelete(String...modelCode);

    /**
     * 批量删除模型编码
     *
     * @param modeCodes 需要删除的模型编码主键集合
     * @return 结果
     */
     int deleteModelCodeByModeCodes(String... modeCodes);


}
