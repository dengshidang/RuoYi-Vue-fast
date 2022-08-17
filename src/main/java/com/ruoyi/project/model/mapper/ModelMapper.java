package com.ruoyi.project.model.mapper;

import com.ruoyi.project.model.domain.Model;
import io.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dengsd
 * @date 2022/7/29 16:23
 */
@Mapper
public interface ModelMapper extends BaseMapper<Model,Integer> {
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
     * 删除模型
     *
     * @param modelId 模型主键
     * @return 结果
     */
    public int deleteModelByModelId(Integer modelId);

    /**
     * 批量删除模型
     *
     * @param modelIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteModelByModelIds(Integer[] modelIds);
}
