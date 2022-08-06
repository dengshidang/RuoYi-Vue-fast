package com.ruoyi.project.model.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.model.domain.ModelCode;
import com.ruoyi.project.model.service.IModelCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 模型编码Controller
 *
 * @author dengsd
 * @date 2022-08-06
 */
@RestController
@RequestMapping("/model/code")
public class ModelCodeController extends BaseController
{
    @Autowired
    private IModelCodeService modelCodeService;

    /**
     * 查询模型编码列表
     */
    @PreAuthorize("@ss.hasPermi('model:code:list')")
    @GetMapping("/list")
    public TableDataInfo list(ModelCode modelCode)
    {
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
    public void export(HttpServletResponse response, ModelCode modelCode)
    {
        List<ModelCode> list = modelCodeService.selectModelCodeList(modelCode);
        ExcelUtil<ModelCode> util = new ExcelUtil<ModelCode>(ModelCode.class);
        util.exportExcel(response, list, "模型编码数据");
    }

    /**
     * 获取模型编码详细信息
     */
    @PreAuthorize("@ss.hasPermi('model:code:query')")
    @GetMapping(value = "/{modeCode}")
    public AjaxResult getInfo(@PathVariable("modeCode") String modeCode)
    {
        return AjaxResult.success(modelCodeService.selectModelCodeByModeCode(modeCode));
    }

    /**
     * 新增模型编码
     */
    @PreAuthorize("@ss.hasPermi('model:code:add')")
    @Log(title = "模型编码", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ModelCode modelCode)
    {
        return toAjax(modelCodeService.insertModelCode(modelCode));
    }

    /**
     * 修改模型编码
     */
    @PreAuthorize("@ss.hasPermi('model:code:edit')")
    @Log(title = "模型编码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ModelCode modelCode)
    {
        return toAjax(modelCodeService.updateModelCode(modelCode));
    }

    /**
     * 删除模型编码
     */
    @PreAuthorize("@ss.hasPermi('model:code:remove')")
    @Log(title = "模型编码", businessType = BusinessType.DELETE)
	@DeleteMapping("/{modeCodes}")
    public AjaxResult remove(@PathVariable String[] modeCodes)
    {
        return toAjax(modelCodeService.deleteModelCodeByModeCodes(modeCodes));
    }
}
