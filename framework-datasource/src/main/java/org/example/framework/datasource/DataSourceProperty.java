package org.example.framework.datasource;

import lombok.Getter;
import lombok.Setter;
import org.example.framework.datasource.druid.DruidDataSourceProperties;
import org.example.framework.datasource.hikari.HikariDataSourceProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@Setter
public class DataSourceProperty {
    /**
     * JDBC type,如果不设置自动查找 Druid > HikariCp
     */
    private String type;

    /**
     * JDBC driver
     */
    private String driverClassName;

    /**
     * JDBC url 地址
     */
    private String url;

    /**
     * JDBC 用户名
     */
    private String username;

    /**
     * JDBC 密码
     */
    private String password;

    /**
     * Druid 参数配置
     */
    @NestedConfigurationProperty
    private DruidDataSourceProperties druid = new DruidDataSourceProperties();

    /**
     * Hikari 参数配置
     */
    @NestedConfigurationProperty
    private HikariDataSourceProperties hikari = new HikariDataSourceProperties();
}
