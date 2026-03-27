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

package org.apache.shardingsphere.infra.datasource.pool.destroyer.detector.type;

import lombok.SneakyThrows;
import org.apache.shardingsphere.infra.datasource.pool.destroyer.detector.DataSourcePoolActiveDetector;

import javax.sql.DataSource;

/**
 * Hikari data source pool active detector.
 */
public final class DruidDataSourcePoolActiveDetector implements DataSourcePoolActiveDetector {
    
    @Override
    public boolean containsActiveConnection(final DataSource dataSource) {
        return 0 != getActiveConnections(dataSource);
    }
    
    @SneakyThrows(ReflectiveOperationException.class)
    private int getActiveConnections(final DataSource dataSource) {
        Object result = dataSource.getClass()
                .getMethod("getActiveCount")
                .invoke(dataSource);
        return null == result ? 0 : (int) result;
    }
    
    @Override
    public String getType() {
        return "com.alibaba.druid.pool.DruidDataSource";
    }
}
