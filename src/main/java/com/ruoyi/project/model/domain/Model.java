package com.ruoyi.project.model.domain;


import com.ruoyi.framework.config.mybatis.FastjsonArrayHandler;
import io.mybatis.provider.Entity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author dengsd
 * @date 2022/7/29 16:20
 */
@Entity.Table("model")
@Data
public class Model {
    @Entity.Column(value = "model_id",id = true)

    private Integer modelId;
    @Entity.Column(value = "cate_id")
    private Integer cateId;
    /** $column.columnComment */
    @Entity.Column(value = "model_name")
    private String modelName;
    @Entity.Column(value = "model_code")
    private String modelCode;
    @Entity.Column(value = "model_img")
    private String modelImg;

    @Entity.Column(value = "model_icon")
    private String modelIcon;

    @Entity.Column(value = "cate_name")
    private String cateName;

    @Entity.Column(value = "attr_list")
    private String attrList;
    private String createBy;
    private String updateBy;
    private Date updateTime;
    private Date createTime;
    @Entity.Column(value = "children",typeHandler = FastjsonArrayHandler.class)
    private List<Model> children;
}
