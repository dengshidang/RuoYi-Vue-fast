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
@Entity.Table(value = "model_code", autoResultMap = true)
public class ModelCode {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @Entity.Column(id = true)
    @Excel(name = "模型编码",mergeLevel = 2,merge = 0)
    private String modelCode;
    @Entity.Column("model_name")
    @Excel(name = "模型名称",merge = 1)
    private String modelName;

    /**
     * 分类ID
     */
    @Entity.Column("model_cate_id")
    private Integer modelCateId;

    /**
     * 接口编码
     */
    @Entity.Column("interface_code")
    private String interfaceCode;

    /**
     * 接口名称
     */
    @Entity.Column("interface_name")
    @Excel(name = "接口名称", merge = 2)

    private String interfaceName;
    @Entity.Column("model_group")
    private String modelGroup;

    /**
     * 接口ID
     */
    @Entity.Column("interface_id")
    private Integer interfaceId;

    /**
     * 属性
     */
    @Entity.Column(typeHandler = FastjsonArrayHandler.class, value = "model_attrs")
    private List modelAttrs;
    @Entity.Column(value = "model_attr_ids")
    private String modelAttrIds;
    @Entity.Column(value = "model_attr_value")
    @Excel(name = "属性值", merge =3)
    private String modelAttrValues;
    @Entity.Column("create_by")
    private String createBy;
    @Entity.Column("update_by")
    private String updateBy;
    @Entity.Column("update_time")
    private Date updateTime;
    @Entity.Column("create_time")
    private Date createTime;
    @Excel(name = "备注", merge = 4)
    private String remark;
    @Excel(name = "模型分类", merge = 5)
    private transient String modelCateName;
    private transient String parentCateName;
    private transient String used;

    public ModelCode copy(int i) {
        ModelCode code = new ModelCode();
        code.setModelCode(modelCode+"_"+(i+1));
        code.setModelName(modelName);
        code.setModelCateName(modelCateName);
        code.setInterfaceName(interfaceName);
        code.setModelAttrValues(modelAttrValues);
        code.setRemark(remark);
        return code;
    }
}
