package com.ruoyi.project.prod.service;

import com.ruoyi.project.model.domain.ModelCategory;
import com.ruoyi.project.prod.domain.ProdCategory;

import java.util.List;

/**
 * @author dengsd
 * @date 2022/8/11 15:40
 */
public interface IProdCategoryService {
    List<ProdCategory> list(ProdCategory prodCategory);

    String getInfo(Integer prodCateId);

    boolean add(ProdCategory prodCategory);

    boolean edit(ProdCategory prodCategory);

    boolean delete(Integer... odCateIds);

    /**
     * 是否存在
     */
    boolean exists (ProdCategory  prodCategory);
    /**
     * 没有关联的数据时可以删除
     * 可以删除
     *
     */
    void verifyDelete(Integer...cateId);

    /**
     * 可以保存
     * @param prodCategory
     * @return
     */
    void verify(ProdCategory  prodCategory);

    /**
     * 是否有子集
     * @param cateId
     * @return
     */
    boolean hasChrildren(Integer cateId);
}
