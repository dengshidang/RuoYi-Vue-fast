package com.ruoyi.common.enums;

import com.ruoyi.common.exception.ServiceException;

import java.util.Arrays;

/**
 * @Author dengsd
 * @Date 2022/8/6 21:14
 */

public enum EModelSuffix {
    ICON("icon");

    String suffix;

    EModelSuffix(String suffix) {
        this.suffix = suffix;
    }
    public String getSuffix(){
        return suffix;
    }

    public static EModelSuffix verify(String suffix){
      return   Arrays.stream(values()).filter(a->a.getSuffix().equals(suffix)).findFirst().orElseThrow(()-> new ServiceException("未定义的类型["+suffix+"]"));
    }

}
