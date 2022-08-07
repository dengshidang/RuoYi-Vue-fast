package com.ruoyi.project.model.service;

import com.ruoyi.project.model.domain.ModelCategory;
import io.mybatis.mapper.fn.Fn;

import java.util.List;

public interface IModelCategoryService {
    List<ModelCategory> list(ModelCategory modelCategory, Fn<ModelCategory,Object>... fns);

    ModelCategory getInfo(Integer modelCateId);

    boolean add(ModelCategory modelCategory);

    /**
     * 是否存在
     */
    boolean exists (ModelCategory modelCategory);
    /**
     * 没有关联的数据时可以删除
     * 可以删除
     *
     */
   void verifyDelete(Integer...cateId);

    /**
     * 可以保存
     * @param modelCategory
     * @return
     */
    void verify(ModelCategory modelCategory);

    /**
     * 是否有子集
     * @param cateId
     * @return
     */
    boolean hasChrildren(Integer cateId);
    /**
     * 编辑
     * @param modelCategory
     * @return
     */
    boolean edit(ModelCategory modelCategory);

    /**
     * 删除分类
     * @param modelCateIds
     * @return
     */
    boolean delete(Integer... modelCateIds);

    /**
     * 接口名称是否被使用
     * @param modelCateId
     * @param interfaceCode
     * @return
     */
    boolean usedInterfaceCode(Integer modelCateId, String interfaceCode);
}
