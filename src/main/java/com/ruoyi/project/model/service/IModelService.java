package com.ruoyi.project.model.service;

import com.ruoyi.project.model.domain.Model;

import java.util.List;

/**
 * @author dengsd
 * @date 2022/7/29 16:15
 */
public interface IModelService {
    /**
     * 是否存在
     * @param model
     * @return
     */
    boolean exists(Model model);

    /**
     * 查询模型
     *
     * @param modelId 模型主键
     * @return 模型
     */
    public Model selectModelByModelId(Integer modelId);

    /**
     * 查询模型列表
     *
     * @param model 模型
     * @return 模型集合
     */
    public List<Model> selectModelList(Model model);

    /**
     * 新增模型
     *
     * @param model 模型
     * @return 结果
     */
    public int insertModel(Model model);

    /**
     * 修改模型
     *
     * @param model 模型
     * @return 结果
     */
    public int updateModel(Model model);

    /**
     * 批量删除模型
     *
     * @param modelIds 需要删除的模型主键集合
     * @return 结果
     */
    public int deleteModelByModelIds(Integer[] modelIds);

    /**
     * 删除模型信息
     *
     * @param modelId 模型主键
     * @return 结果
     */
    public int deleteModelByModelId(Integer modelId);
}
