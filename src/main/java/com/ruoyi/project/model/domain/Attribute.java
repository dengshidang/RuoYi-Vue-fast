package com.ruoyi.project.model.domain;

import com.ruoyi.framework.config.mybatis.FastjsonArrayHandler;
import com.ruoyi.project.common.domain.AttrValue;
import io.mybatis.provider.Entity;
import lombok.Data;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 模型属性对象 model_attribute
 *
 * @author dengsd
 * @date 2022-07-31
 */
@Entity.Table(value = "attribute",autoResultMap = true)
@Data
public class Attribute implements Serializable
{
    private static final Long serialVersionUID = 1L;

    /** 模型属性id */
    @Entity.Column(id = true,value = "attr_id")
    private Integer attrId;

    /** 模型属性名称 */
    @Excel(name = "模型属性名称")
    @NotBlank
    @Entity.Column(value = "attr_name")
    private String attrName;
    @Entity.Column(value = "attr_group",remark = "prod,model,material")
    @NotBlank
    private String attrGroup;

    @Entity.Column(value = "attr_type")
    private String attrType;
    @Entity.Column(value = "attr_values",typeHandler = FastjsonArrayHandler.class)
    private List<AttrValue> attrValues;
    private  transient  String attrValue;

    /** 模型属性图标 */
    @Excel(name = "模型属性图标")
    @Entity.Column(value = "attr_icon")

    private String attrIcon;

    /** 模型属性编码 */
    @Excel(name = "模型属性编码")
    @Entity.Column(value = "attr_code")
    private String attrCode;
    /** special attr */
    @Excel(name = "专用属性：和分类挂钩")
    @Entity.Column(value = "attr_special")
    private String attrSpecial;
    private String createBy;
    private String updateBy;
    private Date updateTime;
    private Date createTime;
    // 查询当前类目下的专用属性
    private transient String modelCateId;
}
