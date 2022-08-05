package com.ruoyi.framework.config.mybatis;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.*;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

@MappedTypes({Arrays.class})
@MappedJdbcTypes(JdbcType.ARRAY)
public class FastjsonArrayHandler extends BaseTypeHandler<List> {

    public FastjsonArrayHandler() {
    }
    public void setNonNullParameter(PreparedStatement ps, int i, List parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSONObject.toJSONString(parameter));
    }

    public List getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return StringUtils.isBlank(json) ? null : JSONObject.parseObject(json,new TypeReference<List>(){}.getType());
    }

    public List getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return StringUtils.isBlank(json) ? null :JSONObject.parseObject(json,new TypeReference<List>(){}.getType());
    }

    public List getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return StringUtils.isBlank(json) ? null :JSONObject.parseObject(json,new TypeReference<List>(){}.getType());
    }

}
