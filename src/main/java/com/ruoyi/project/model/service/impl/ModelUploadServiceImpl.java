package com.ruoyi.project.model.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import io.mybatis.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.model.mapper.ModelUploadMapper;
import com.ruoyi.project.model.domain.ModelUpload;
import com.ruoyi.project.model.service.IModelUploadService;

/**
 * 素材Service业务层处理
 *
 * @author dengsd
 * @date 2022-08-19
 */
@Service
public class ModelUploadServiceImpl extends AbstractService<ModelUpload,Integer,ModelUploadMapper> implements IModelUploadService
{

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
                .like(StringUtils.isNotEmpty(modelUpload.getModelCode()),ModelUpload::getModelCode,modelUpload.getModelCode())
                .like(StringUtils.isNotEmpty(modelUpload.getFileName()),ModelUpload::getFileName,modelUpload.getFileName())
                .like(StringUtils.isNotEmpty(modelUpload.getOriginalName()),ModelUpload::getOriginalName,modelUpload.getOriginalName())
                .eq(StringUtils.isNotEmpty(modelUpload.getFileType()),ModelUpload::getFileType,modelUpload.getFileType())
                .example().orderByDesc(ModelUpload::getId));
    }

    /**
     * 新增素材
     *
     * @param modelUpload 素材
     * @return 结果
     */
    @Override
    public int insertModelUpload(ModelUpload modelUpload)
    {
        modelUpload.setCreateTime(DateUtils.getNowDate());
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
            super.saveSelective(upload);
        }
        return true;
    }


}
