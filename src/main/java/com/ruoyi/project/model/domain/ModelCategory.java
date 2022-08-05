package com.ruoyi.project.model.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.verify.Update;
import io.mybatis.provider.Entity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 模型分类对象 model_category
 *
 * @author dengsd
 * @date 2022-07-29
 */
@Entity.Table("model_category")
@Data
public class ModelCategory {

    private  static  final long serialVersionUID = 1L;

    @NotNull(groups = Update.class)
    @Entity.Column(id = true,value = "model_cate_id")
    private Integer modelCateId;
    @NotBlank
    @Entity.Column("model_cate_name")
    private String modelCateName;
    @Entity.Column("model_cate_icon")
    private String modelCateIcon;
    @Entity.Column("origins")
    private String origins;
    @Entity.Column("model_cate_level")
    private Integer modelCateLevel;
    @Entity.Column("parent_id")
    private Integer parentId;
    @Entity.Column("`status`")
    private Integer status;
    @Entity.Column("`order_num`")
    private Integer orderNum;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Entity.Column("code")
    private String code;
    private String createBy;
    private String updateBy;
    private Date updateTime;
    private Date createTime;
   private transient List<ModelCategory> children;

}
