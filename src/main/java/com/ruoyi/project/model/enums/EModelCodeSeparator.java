package com.ruoyi.project.model.enums;

/**
 * @Author dengsd
 * @Date 2022/8/6 21:14
 */

public enum EModelCodeSeparator {
    DEFAULT("_");
    String separator;

    EModelCodeSeparator(String separator) {
        this.separator = separator;
    }
    public String getSeparator(){
        return separator;
    }

}
