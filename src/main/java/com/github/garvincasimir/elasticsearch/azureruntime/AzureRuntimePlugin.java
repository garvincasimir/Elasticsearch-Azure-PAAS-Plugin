package com.github.garvincasimir.elasticsearch.azureruntime;


import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.discovery.DiscoveryModule;



/**
 *
 */
public class AzureRuntimePlugin extends Plugin {

    private final Settings settings;
    private final ESLogger logger = ESLoggerFactory.getLogger(AzureRuntimePlugin.class.getName());

    public AzureRuntimePlugin(Settings settings) {

        this.settings = settings;
        logger.debug("discovery.type not set to {}",settings.get("discovery.type"));
    }

    @Override
    public String name() {
        return "cloud-azure-runtime";
    }

    @Override
    public String description() {
        return "Cloud Azure Runtime Plugin";
    }


    public void onModule(DiscoveryModule discoveryModule) {


            discoveryModule.addDiscoveryType(AzureRuntimeDiscovery.AZURERUNTIME, AzureRuntimeDiscovery.class);
            discoveryModule.addUnicastHostProvider(AzureRuntimeUnicastHostsProvider.class);

    }
}
