package com.ruoyi.common.enums;

import com.ruoyi.common.exception.ServiceException;

import java.util.Arrays;

/**
 * @Author dengsd
 * @Date 2022/8/6 21:14
 */

public enum ECategoryGroup {
    MODEL("model"),
    PROD("prod"),
    MATERIAL("material");
    String group;

    ECategoryGroup(String group) {
        this.group = group;
    }
    public String getGroup(){
        return group;
    }

    public static ECategoryGroup verify(String group){
      return   Arrays.stream(values()).filter(a->a.getGroup().equals(group)).findFirst().orElseThrow(()-> new ServiceException("未定义的类型["+group+"]"));
    }

}
