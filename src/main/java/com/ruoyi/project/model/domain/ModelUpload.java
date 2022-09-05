package com.ruoyi.project.model.domain;

import com.ruoyi.common.enums.EModelSuffix;
import com.ruoyi.common.enums.ESeparator;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.config.mybatis.FastjsonArrayHandler;
import io.mybatis.provider.Entity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 【请填写功能名称】对象 model_upload
 *
 * @author ruoyi
 * @date 2022-08-19
 */
@Data
@Entity.Table(value = "model_upload",autoResultMap = true)
public class ModelUpload {

    private static final long serialVersionUID = 1L;

    /**
     * 上传id
     */
    @Entity.Column(id = true)
    private String id;

    /**
     * 源文件名称
     */
    @Excel(name = "源文件名称")
    private String originalName;

    /**
     * 路径
     */
    @Excel(name = "路径")
    private String filePath;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String fileName;
    private String fileType;
    private String modelCode;
    private String modelName;
    private String icon;
    @Entity.Column(typeHandler = FastjsonArrayHandler.class)
    private List<String> tietus;

    /**
     * 文件唯一id
     */
    @Excel(name = "文件唯一id")
    private String fileMd5;
    private Long size;
    private Date createTime;
    private Date updateTime;
    private String createBy;
    private String updateBy;
    private Boolean handler;
    private String state;

    private transient List<String> iconCodes;
    @Entity.Column(typeHandler = FastjsonArrayHandler.class)
    private List<ModelUpload> children;
    @Entity.Column(typeHandler = FastjsonArrayHandler.class)
    private List<ModelUpload> self;
    @Entity.Column(typeHandler = FastjsonArrayHandler.class,value = "`match`")
    private List<String> match;

    /**
     * 特殊内置方法 获取模型  icon
     *
     * @return
     */
    public String getModelIconCode() {
        if (StringUtils.isEmpty(modelCode)) {
            return modelCode;
        }
        return modelCode + ESeparator.DEFAULT.getSeparator() + EModelSuffix.ICON;
    }
}
