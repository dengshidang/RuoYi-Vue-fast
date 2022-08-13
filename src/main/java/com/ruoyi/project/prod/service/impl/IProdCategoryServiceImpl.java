package com.ruoyi.project.prod.service.impl;

import com.ruoyi.project.prod.domain.ProdCategory;
import com.ruoyi.project.prod.mapper.ProdCategoryMapper;
import com.ruoyi.project.prod.service.IProdCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengsd
 * @date 2022/8/11 15:40
 */
@Service
public class IProdCategoryServiceImpl implements IProdCategoryService {
    @Autowired
    ProdCategoryMapper prodCategoryMapper;
    @Override
    public List<ProdCategory> list(ProdCategory prodCategory) {
        return prodCategoryMapper.list(prodCategory);
    }

    @Override
    public String getInfo(Integer prodCateId) {
        return null;
    }

    @Override
    public boolean add(ProdCategory prodCategory) {
        return false;
    }

    @Override
    public boolean edit(ProdCategory prodCategory) {
        return false;
    }

    @Override
    public boolean delete(Integer... odCateIds) {
        return false;
    }

    @Override
    public boolean exists(ProdCategory prodCategory) {
        return false;
    }

    @Override
    public void verifyDelete(Integer... cateId) {

    }

    @Override
    public void verify(ProdCategory prodCategory) {

    }

    @Override
    public boolean hasChrildren(Integer cateId) {
        return false;
    }
}
