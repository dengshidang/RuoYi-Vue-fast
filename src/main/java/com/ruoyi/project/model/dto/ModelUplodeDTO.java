package com.ruoyi.project.model.dto;

import lombok.Data;

/**
 * 组合模型的 dto
 * @author dengsd
 * @date 2022/8/31 11:01
 */
@Data
public class ModelUplodeDTO {
 private  String fileName;
 private String fileType;
 private Boolean handler;
 private String icon;
 private Integer id;
 private String modelCode;
 private  String modelName;
 private String originalName;
 private String filePath;
 private Long size;
}

