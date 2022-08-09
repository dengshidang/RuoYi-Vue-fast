package com.ruoyi.project.model.domain;

import com.ruoyi.common.verify.Update;
import com.ruoyi.framework.config.mybatis.FastjsonArrayHandler;
import io.mybatis.provider.Entity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 模型分类对象 model_category
 *
 * @author dengsd
 * @date 2022-07-29
 */
@Entity.Table(value = "model_category",autoResultMap = true)
@Data
public class ModelCategory {

    private  static  final long serialVersionUID = 1L;

    @NotNull(groups = Update.class)
    @Entity.Column(id = true,value = "model_cate_id")
    private Integer modelCateId;
    @NotBlank
    @Entity.Column("model_cate_name")
    private String modelCateName;
    @Entity.Column(value = "interfaces",typeHandler = FastjsonArrayHandler.class)
    private List<ModelInterface> interfaces;
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
   private transient List<ModelCategory> children;
   private transient Set<Integer> cateIds;
   // 删除接口名称需要校验时，请求后台的参数
   private transient String interfaceCode;


}
