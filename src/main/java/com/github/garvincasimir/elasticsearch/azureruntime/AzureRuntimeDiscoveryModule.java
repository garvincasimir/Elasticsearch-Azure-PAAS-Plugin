/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.garvincasimir.elasticsearch.azureruntime;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.Discovery;
import org.elasticsearch.discovery.zen.ZenDiscoveryModule;

/**
 *
 */
public class AzureRuntimeDiscoveryModule extends ZenDiscoveryModule {

    protected final ESLogger logger;
    private Settings settings;

    @Inject
    public AzureRuntimeDiscoveryModule(Settings settings) {
        super();
        this.logger = Loggers.getLogger(getClass(), settings);
        this.settings = settings;
    }



    @Override
    protected void bindDiscovery() {
        if (isDiscoveryReady(settings, logger)) {
            bind(Discovery.class).to(AzureRuntimeDiscovery.class).asEagerSingleton();
        } else {
            logger.debug("disabling azure discovery features");
        }
    }

    public static boolean isDiscoveryReady(Settings settings, ESLogger logger) {
          /*
        if(isPropertyMissing(settings,"cloud.azureruntime." + AzureRuntimeUnicastHostsProvider.BRIDGE ,logger)){
            logger.error("Property missing: {}",AzureRuntimeUnicastHostsProvider.BRIDGE);
            return false;
        }

        if(isPropertyMissing(settings,"discovery.type" ,logger)){
            logger.warn("No discovery type set");
            return false;
        }

        if(settings.get("discovery.type") != "azureruntime") {
            logger.warn("Azure Runtime not set as discovery method");
            return false;
        }
            */

        logger.trace("all required properties for azure discovery are set!");

        return  (settings.getAsBoolean("cloud.enabled", true));
    }

    public static boolean isPropertyMissing(Settings settings, String name, ESLogger logger)  {
        if (!Strings.hasText(settings.get(name))) {

            logger.warn("{} is not set or is incorrect.", name);

            return true;
        }
        return false;
    }


}
