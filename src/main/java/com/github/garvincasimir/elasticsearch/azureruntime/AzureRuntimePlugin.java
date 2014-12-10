package com.github.garvincasimir.elasticsearch.azureruntime;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugins.AbstractPlugin;
import java.util.Collection;




/**
 *
 */
public class AzureRuntimePlugin extends AbstractPlugin {

    private final Settings settings;
    private final ESLogger logger = ESLoggerFactory.getLogger(AzureRuntimePlugin.class.getName());

    public AzureRuntimePlugin(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String name() {
        return "cloud-azure-runtime";
    }

    @Override
    public String description() {
        return "Cloud Azure Runtime Plugin";
    }



}
