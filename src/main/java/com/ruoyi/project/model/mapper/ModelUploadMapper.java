package com.ruoyi.project.model.mapper;

import com.ruoyi.project.model.domain.ModelUpload;
import io.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import javax.jws.WebParam;

/**
 * @author dengsd
 * @date 2022/8/19 10:26
 */
@Mapper
public interface ModelUploadMapper extends BaseMapper<ModelUpload, String> {

  int  insertBack(ModelUpload modelUpload);
  int  updateBack(ModelUpload modelUpload);
  ModelUpload  selectBack(String filePath);
}
