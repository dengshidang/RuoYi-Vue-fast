package com.ruoyi.project.model.service;

import java.util.List;
import com.ruoyi.project.model.domain.ModelUpload;
import io.mybatis.service.BaseService;

/**
 * 素材Service接口
 *
 * @author dengsd
 * @date 2022-08-19
 */
 public  interface IModelUploadService extends BaseService<ModelUpload,String>
{
    /**
     * 查询素材
     *
     * @param id 素材主键
     * @return 素材
     */
     ModelUpload selectModelUploadById(String id);

    /**
     * 查询素材列表
     *
     * @param modelUpload 素材
     * @return 素材集合
     */
     List<ModelUpload> selectModelUploadList(ModelUpload modelUpload);

    /**
     * 新增素材
     *
     * @param modelUpload 素材
     * @return 结果
     */
     int insertModelUpload( List<ModelUpload> modelUpload);

    /**
     * 修改素材
     *
     * @param modelUpload 素材
     * @return 结果
     */
     int updateModelUpload(ModelUpload modelUpload);

    /**
     * 批量删除素材
     *
     * @param ids 需要删除的素材主键集合
     * @return 结果
     */
     int deleteModelUploadByIds(String[] ids);

    boolean insertBatch(List<ModelUpload> uploadList);
}
