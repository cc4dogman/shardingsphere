/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.infra.datasource.pool.metadata.type.druid;

import org.apache.shardingsphere.infra.datasource.pool.metadata.DataSourcePoolMetaData;
import org.apache.shardingsphere.infra.datasource.pool.metadata.DataSourcePoolPropertiesValidator;
import org.apache.shardingsphere.infra.datasource.pool.metadata.DefaultDataSourcePoolPropertiesValidator;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Druid data source pool meta data.
 */
public final class DruidDataSourcePoolMetaData implements DataSourcePoolMetaData {
    
    private static final Map<String, Object> DEFAULT_PROPS = new HashMap<>(6, 1);
    
    private static final Map<String, Object> INVALID_PROPS = new HashMap<>(2, 1);
    
    private static final Map<String, String> PROP_SYNONYMS = new HashMap<>(6, 1);
    
    private static final Collection<String> TRANSIENT_FIELD_NAMES = new LinkedList<>();
    
    static {
        buildDefaultProperties();
        buildInvalidProperties();
        buildPropertySynonyms();
        buildTransientFieldNames();
    }
    
    private static void buildDefaultProperties() {
        // ===== 连接池核心参数 =====
        DEFAULT_PROPS.put("maxWait", 60 * 1000L);
        DEFAULT_PROPS.put("maxActive", 30);
        DEFAULT_PROPS.put("initialSize", 5);
        DEFAULT_PROPS.put("minIdle", 5);
        // ===== 连接回收 & 保活 =====
        DEFAULT_PROPS.put("timeBetweenEvictionRunsMillis", 60 * 1000L);
        DEFAULT_PROPS.put("minEvictableIdleTimeMillis", 300 * 1000L);
        DEFAULT_PROPS.put("maxEvictableIdleTimeMillis", 900 * 1000L);
        // ===== 校验 =====
        DEFAULT_PROPS.put("validationQuery", "SELECT 1");
        DEFAULT_PROPS.put("testWhileIdle", true);
        DEFAULT_PROPS.put("testOnBorrow", false);
        DEFAULT_PROPS.put("testOnReturn", false);
        
        // ===== 监控 & 防护 =====
        // DEFAULT_PROPS.put("filters", "stat,wall,slf4j");
        // ===== 慢SQL =====
        DEFAULT_PROPS.put("connectionProperties", "druid.stat.slowSqlMillis=3000;druid.stat.logSlowSql=true");
        // ===== 连接泄漏检测（可选）=====
        DEFAULT_PROPS.put("keepAlive", true);
        DEFAULT_PROPS.put("logAbandoned", true);
        DEFAULT_PROPS.put("removeAbandonedTimeout", 120);
    }
    
    private static void buildInvalidProperties() {
        INVALID_PROPS.put("minIdle", -1);
        INVALID_PROPS.put("maxActive", -1);
        INVALID_PROPS.put("initialSize", -1);
    }
    
    private static void buildPropertySynonyms() {
        // PROP_SYNONYMS.put("url", "jdbcUrl");
        PROP_SYNONYMS.put("connectionTimeoutMilliseconds", "connectionTimeout");
        PROP_SYNONYMS.put("idleTimeoutMilliseconds", "idleTimeout");
        PROP_SYNONYMS.put("maxLifetimeMilliseconds", "maxLifetime");
        PROP_SYNONYMS.put("maxPoolSize", "maximumPoolSize");
        PROP_SYNONYMS.put("minPoolSize", "minimumIdle");
    }
    
    private static void buildTransientFieldNames() {
        TRANSIENT_FIELD_NAMES.add("running");
        TRANSIENT_FIELD_NAMES.add("poolName");
        TRANSIENT_FIELD_NAMES.add("closed");
    }
    
    @Override
    public Map<String, Object> getDefaultProperties() {
        return DEFAULT_PROPS;
    }
    
    @Override
    public Map<String, Object> getInvalidProperties() {
        return INVALID_PROPS;
    }
    
    @Override
    public Map<String, String> getPropertySynonyms() {
        return PROP_SYNONYMS;
    }
    
    @Override
    public Collection<String> getTransientFieldNames() {
        return TRANSIENT_FIELD_NAMES;
    }
    
    @Override
    public DruidDataSourcePoolFieldMetaData getFieldMetaData() {
        return new DruidDataSourcePoolFieldMetaData();
    }
    
    @Override
    public DataSourcePoolPropertiesValidator getDataSourcePoolPropertiesValidator() {
        return new DefaultDataSourcePoolPropertiesValidator();
    }
    
    @Override
    public String getType() {
        return "com.alibaba.druid.pool.DruidDataSource";
    }
}
