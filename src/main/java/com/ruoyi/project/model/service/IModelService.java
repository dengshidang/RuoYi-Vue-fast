package com.ruoyi.project.model.service;

import com.ruoyi.project.model.domain.Model;

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
}
