package com.ruoyi.project.model.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.model.domain.ModelCode;
import com.ruoyi.project.model.service.IModelCodeService;
import io.mybatis.mapper.example.Example;
import io.mybatis.service.AbstractService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.model.mapper.ModelUploadMapper;
import com.ruoyi.project.model.domain.ModelUpload;
import com.ruoyi.project.model.service.IModelUploadService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 素材Service业务层处理
 *
 * @author dengsd
 * @date 2022-08-19
 */
@Service
public class ModelUploadServiceImpl extends AbstractService<ModelUpload,Integer,ModelUploadMapper> implements IModelUploadService
{

    @Autowired
    IModelCodeService modelCodeService;
    /**
     * 查询素材
     *
     * @param id 素材主键
     * @return 素材
     */
    @Override
    public ModelUpload selectModelUploadById(Integer id)
    {
        return this.baseMapper.selectByPrimaryKey(id).orElse(null);
    }

    /**
     * 查询素材列表
     *
     * @param modelUpload 素材
     * @return 素材
     */
    @Override
    public List<ModelUpload> selectModelUploadList(ModelUpload modelUpload)
    {
       return this.baseMapper.selectByExample( this.baseMapper.wrapper()
                .like(StringUtils.isNotEmpty(modelUpload.getModelCode()),ModelUpload::getModelCode,StringUtils.format("{}%",modelUpload.getModelCode()))
                .like(StringUtils.isNotEmpty(modelUpload.getFileName()),ModelUpload::getFileName,modelUpload.getFileName())
                .like(StringUtils.isNotEmpty(modelUpload.getOriginalName()),ModelUpload::getOriginalName,modelUpload.getOriginalName())
                .eq(StringUtils.isNotEmpty(modelUpload.getFileType()),ModelUpload::getFileType,modelUpload.getFileType())
                .eq(StringUtils.isNotNull(modelUpload.getHandler()),ModelUpload::getHandler,modelUpload.getHandler())
                .example().orderByDesc(ModelUpload::getId));
    }

    /**
     * 新增素材
     *
     * @param modelUpload 素材
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertModelUpload(ModelUpload modelUpload)
    {
        modelUpload.setCreateTime(DateUtils.getNowDate());
        Example<ModelUpload> example = baseMapper.wrapper().eq(ModelUpload::getOriginalName, modelUpload.getOriginalName()).example();
        List<ModelUpload> dbmodelUploads = baseMapper.selectByExample(example);
        if(ObjectUtils.isNotEmpty(dbmodelUploads)){
            ModelUpload dbupload = dbmodelUploads.get(0);
            modelUpload.setId(dbupload.getId());
            String filePath = dbupload.getFilePath();
            //查询备份文件，按每日更新
            ModelUpload modelBack = this.baseMapper.selectBack(filePath);
            if(StringUtils.isNotNull(modelBack)){
                baseMapper.updateBack(dbupload);
            }else{
                this.baseMapper.insertBack(dbupload);
            }
            modelUpload.setUpdateTime(DateUtils.getNowDate());
            return this.baseMapper.updateByPrimaryKeySelective(modelUpload);
        }
        return this.baseMapper.insertSelective(modelUpload);
    }

    /**
     * 修改素材
     *
     * @param modelUpload 素材
     * @return 结果
     */
    @Override
    public int updateModelUpload(ModelUpload modelUpload)
    {
        modelUpload.setUpdateTime(DateUtils.getNowDate());
        return this.baseMapper.updateByPrimaryKeySelective(modelUpload);
    }

    /**
     * 批量删除素材
     *
     * @param ids 需要删除的素材主键
     * @return 结果
     */
    @Override
    public int deleteModelUploadByIds(Integer[] ids)
    {
        return this.baseMapper.deleteByFieldList(ModelUpload::getId, Stream.of(ids).collect(Collectors.toList()));
    }

    @Override
    public boolean insertBatch(List<ModelUpload> uploadList) {
        for (ModelUpload upload : uploadList) {
            // 查询编码名称
            ModelCode modelCode = modelCodeService.selectModelCodeByModeCode(upload.getModelCode());
            if(ObjectUtils.isNotEmpty(modelCode)){
                upload.setModelName(modelCode.getModelName());
            }
            super.saveSelective(upload);
        }
        return true;
    }


}
