package com.ruoyi.framework.config.mybatis;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import io.mybatis.mapper.base.AbstractTypeHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.Arrays;
import java.util.List;

@MappedTypes({Arrays.class})
@MappedJdbcTypes(JdbcType.ARRAY)
public class FastjsonArrayHandler extends AbstractTypeHandler<List> {

    @Override
    protected List parse(String json) {
        return  StringUtils.isBlank(json) ? null : JSONObject.parseObject(json,new TypeReference<List>(){}.getType());
    }

    @Override
    protected String toJson(List list) {
        return  JSONObject.toJSONString(list);
    }
}
