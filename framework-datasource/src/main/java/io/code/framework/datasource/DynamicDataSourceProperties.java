package io.code.framework.datasource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = DynamicDataSourceProperties.PREFIX, ignoreUnknownFields = false)
public class DynamicDataSourceProperties {

    public static final String PREFIX = "multi.database";

    /**
     * 必须设置默认的库,默认master
     */
    private String primary = "master";

    /**
     * 每一个数据源
     */
    private Map<String, DataSourceProperty> connection = new LinkedHashMap<>();

}
