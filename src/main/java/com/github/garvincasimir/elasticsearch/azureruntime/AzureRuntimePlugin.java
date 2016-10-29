package com.github.garvincasimir.elasticsearch.azureruntime;


import org.elasticsearch.common.inject.Module;


import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.discovery.DiscoveryModule;



/**
 *
 */
public class AzureRuntimePlugin extends Plugin {

    private final Settings settings;


    public AzureRuntimePlugin(Settings settings) {

        this.settings = settings;

    }

    public void onModule(DiscoveryModule discoveryModule) {


            discoveryModule.addDiscoveryType(AzureRuntimeDiscovery.AZURERUNTIME, AzureRuntimeDiscovery.class);
            discoveryModule.addUnicastHostProvider(AzureRuntimeDiscovery.AZURERUNTIME,AzureRuntimeUnicastHostsProvider.class);

    }
}
