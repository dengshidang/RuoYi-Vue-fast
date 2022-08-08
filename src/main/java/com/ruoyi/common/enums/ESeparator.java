package com.ruoyi.common.enums;

/**
 * @Author dengsd
 * @Date 2022/8/6 21:14
 */

public enum ESeparator {
    DEFAULT("_"),
    DOUHAO(",");
    String separator;

    ESeparator(String separator) {
        this.separator = separator;
    }
    public String getSeparator(){
        return separator;
    }

}
