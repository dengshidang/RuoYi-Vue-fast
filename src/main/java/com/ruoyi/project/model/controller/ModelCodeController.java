package com.ruoyi.project.model.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.model.domain.ModelCode;
import com.ruoyi.project.model.service.IModelCodeService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模型编码Controller
 *
 * @author dengsd
 * @date 2022-08-06
 */
@RestController
@RequestMapping("/model/code")
public class ModelCodeController extends BaseController {
    @Autowired
    private IModelCodeService modelCodeService;

    /**
     * 查询模型编码列表
     */
    @PreAuthorize("@ss.hasPermi('model:code:list')")
    @GetMapping("/list")
    public TableDataInfo list(ModelCode modelCode) {
        startPage();
        List<ModelCode> list = modelCodeService.selectModelCodeList(modelCode);
        return getDataTable(list);
    }


    /**
     * 导出模型编码列表
     */
    @PreAuthorize("@ss.hasPermi('model:code:export')")
    @Log(title = "模型编码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ModelCode modelCode) {
        ExcelUtil<ModelCode> util = new ExcelUtil<ModelCode>(ModelCode.class);
        List<ModelCode> list = modelCodeService.selectModelCodeList(modelCode);
        if (ObjectUtils.isNotEmpty(list)) {
            List<ModelCode> result = new ArrayList<>();
            list.parallelStream().forEach(f -> {
                List modelAttrs = f.getModelAttrs();
                if (ObjectUtils.isNotEmpty(modelAttrs)) {
                    AtomicInteger jieshu = new AtomicInteger(1);
                    modelAttrs.parallelStream().forEach(d -> {
                        if(d instanceof JSONObject){
                            JSONObject object  =   ((JSONObject) d);
                            if (object.getString("attrCode").equals("jieshu")) {
                                String attrValue = object.getString("attrValue");
                                jieshu.set(Integer.parseInt(attrValue));

                            }
                        }

                    });
                    if (jieshu.get() == 1) {
                        result.add(f);
                    } else {
                        for (int i = 0; i < jieshu.get(); i++) {
                            result.add(f.copy(i));
                        }
                    }
                }
            });
            util.exportExcel(response, result, "模型编码数据");
            return;
        }

        util.exportExcel(response, list, "模型编码数据");
    }

    /**
     * 获取模型编码详细信息
     */
    @PreAuthorize("@ss.hasPermi('model:code:query')")
    @GetMapping(value = "/{modeCode}")
    public AjaxResult getInfo(@PathVariable("modeCode") String modeCode) {
        return AjaxResult.success(modelCodeService.selectModelCodeByModeCode(modeCode));
    }

    /**
     * 新增模型编码
     */
    @PreAuthorize("@ss.hasPermi('model:code:add')")
    @Log(title = "模型编码", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ModelCode modelCode) {
        return toAjax(modelCodeService.insertModelCode(modelCode));
    }

    /**
     * 修改模型编码
     */
    @PreAuthorize("@ss.hasPermi('model:code:edit')")
    @Log(title = "模型编码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ModelCode modelCode) {
        return toAjax(modelCodeService.updateModelCode(modelCode));
    }

    /**
     * 删除模型编码
     */
    @PreAuthorize("@ss.hasPermi('model:code:remove')")
    @Log(title = "模型编码", businessType = BusinessType.DELETE)
    @DeleteMapping("/{modeCodes}")
    public AjaxResult remove(@PathVariable String[] modeCodes) {
        return toAjax(modelCodeService.deleteModelCodeByModeCodes(modeCodes));
    }

    @PreAuthorize("@ss.hasPermi('model:code:edit')")
    @GetMapping("/saveToGroup/{modelCodes}")
    public AjaxResult saveToGroup(@PathVariable String[] modelCodes) {
        return toAjax(modelCodeService.saveToGroup(modelCodes));
    }

}
