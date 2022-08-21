package com.ruoyi.project.model.domain;

import io.mybatis.provider.Entity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 【请填写功能名称】对象 model_upload
 *
 * @author ruoyi
 * @date 2022-08-19
 */
@Data
@Entity.Table("model_upload")
public class ModelUpload
{
    private static final long serialVersionUID = 1L;

    /** 上传id */
    @Entity.Column(id = true)
    private Integer id;

    /** 源文件名称 */
    @Excel(name = "源文件名称")
    private String originalName;

    /** 路径 */
    @Excel(name = "路径")
    private String filePath;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;
    private String fileType;
    private String modelCode;

    /** 文件唯一id */
    @Excel(name = "文件唯一id")
    private String fileMd5;
    private Long size;
    private Date createTime;
    private Date updateTime;
    private String  createBy;
    private String updateBy;
}