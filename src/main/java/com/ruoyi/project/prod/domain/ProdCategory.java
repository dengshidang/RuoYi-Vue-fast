package com.ruoyi.project.prod.domain;

import com.ruoyi.common.verify.Update;
import com.ruoyi.framework.config.mybatis.FastjsonArrayHandler;
import com.ruoyi.project.model.domain.ModelInterface;
import io.mybatis.provider.Entity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 产品分类对象 prod_category
 *
 * @author dengsd
 * @date 2022-08-11
 */
@Entity.Table(value = "prod_category",autoResultMap = true)
@Data
public class ProdCategory {

    private  static  final long serialVersionUID = 1L;

    @NotNull(groups = Update.class)
    @Entity.Column(id = true,value = "prod_cate_id")
    private Integer prodCateId;
    @NotBlank
    @Entity.Column("prod_cate_name")
    private String prodCateName;
    @Entity.Column("prod_cate_icon")
    private String prodCateIcon;
    @Entity.Column("origins")
    private String origins;
    @Entity.Column("prod_cate_level")
    private Integer prodCateLevel;
    @Entity.Column("parent_id")
    private Integer parentId;
    @Entity.Column("`status`")
    private Integer status;
    @Entity.Column("`order_num`")
    private Integer orderNum;
    @Entity.Column("`attr_ids`")
    private String attrIds;
    private Boolean haschild;
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
    private transient List<ProdCategory> children;
    private transient Set<Integer> cateIds;

}
