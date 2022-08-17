package com.ruoyi.project.model.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.config.mybatis.FastjsonArrayHandler;
import io.mybatis.provider.Entity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 模型编码对象 model_code
 *
 * @author dengsd
 * @date 2022-08-06
 */
@Data
@Entity.Table(value = "model_code",autoResultMap = true)
public class ModelCode
{
    private static final long serialVersionUID = 1L;

    /** 编码 */
    @Entity.Column(id = true)
    @Excel(name = "模型编码")
    private String modelCode;
    @Entity.Column("model_name")
    @Excel(name = "模型名称")
    private String modelName;

    /** 分类ID */
    @Entity.Column("model_cate_id")
    private Integer modelCateId;

    /** 接口编码 */
    @Entity.Column("interface_code")
    @Excel(name = "接口编码")
    private String interfaceCode;

    /** 接口名称 */
    @Entity.Column("interface_name")
    @Excel(name = "接口名称")
    private String interfaceName;
    @Entity.Column("model_group")
    private String modelGroup;

    /** 接口ID */
    @Entity.Column("interface_id")
    private Integer interfaceId;

    /** 属性 */
    @Entity.Column(typeHandler = FastjsonArrayHandler.class,value = "model_attrs")
    @Excel(name = "属性")
    private List modelAttrs;
    @Entity.Column(value = "model_attr_ids")
    private String modelAttrIds;
    @Entity.Column(value = "model_attr_value")
    private String modelAttrValues;
    @Entity.Column("create_by")
    private String createBy;
    @Entity.Column("update_by")
    private String updateBy;
    @Entity.Column("update_time")
    private Date updateTime;
    @Entity.Column("create_time")
    private Date createTime;
    @Excel(name = "备注")
    private String remark;
    @Excel(name = "模型分类")
    private transient String modelCateName;
    private transient String used;

}
