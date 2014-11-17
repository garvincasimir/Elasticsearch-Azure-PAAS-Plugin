

package com.github.garvincasimir.elasticsearch.azureruntime;


import com.google.gson.reflect.TypeToken;
import org.elasticsearch.Version;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.network.NetworkService;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.discovery.zen.ping.unicast.UnicastHostsProvider;
import org.elasticsearch.transport.TransportService;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.google.gson.Gson;

/**
 *
 */
public class AzureRuntimeUnicastHostsProvider extends AbstractComponent implements UnicastHostsProvider {

    public static final String REFRESH = "refresh_interval";
    public static final String BRIDGE = "bridge";

    private TransportService transportService;
    private NetworkService networkService;

    private final TimeValue refreshInterval;
    private String runtimeBridge;
    private long lastRefresh;
    private List<DiscoveryNode> cachedDiscoNodes;



    @Inject
    public AzureRuntimeUnicastHostsProvider(Settings settings,
                                            TransportService transportService,
                                            NetworkService networkService) {
        super(settings);
        this.transportService = transportService;
        this.networkService = networkService;

        this.runtimeBridge = componentSettings.get(BRIDGE,
                settings.get("cloud.azureruntime." + BRIDGE));


        this.refreshInterval = componentSettings.getAsTime(REFRESH,
                settings.getAsTime("cloud.azureruntime." + REFRESH, TimeValue.timeValueSeconds(0)));

    }


    @Override
    public List<DiscoveryNode> buildDynamicNodes() {
        if (refreshInterval.millis() != 0) {
            if (cachedDiscoNodes != null &&
                    (refreshInterval.millis() < 0 || (System.currentTimeMillis() - lastRefresh) < refreshInterval.millis())) {
                if (logger.isTraceEnabled()) logger.trace("using cache to retrieve node list");
                return cachedDiscoNodes;
            }
            lastRefresh = System.currentTimeMillis();
        }
        logger.debug("start building nodes list using Azure API");

        try {
        cachedDiscoNodes = Lists.newArrayList();

        List<RuntimeInstance> response = instances();

        logger.debug("Total instances: " + response.size());


            for (RuntimeInstance instance : response) {
                String networkAddress = null;
                // Let's detect if we want to use public or private IP

                    if (instance.getIp() != null && instance.getPort() !=null) {
                        networkAddress = instance.getIp() + ":" + instance.getPort() ;
                    } else {
                        logger.trace("no public ip provided ignoring {}", instance.getNodeName());
                    }


                if (networkAddress == null) {
                    logger.debug("Can't addd endooint for {}",instance.getNodeName());

                } else {
                    TransportAddress[] addresses = transportService.addressesFromString(networkAddress);
                    // we only limit to 1 addresses, makes no sense to ping 100 ports
                    logger.trace("adding {}, transport_address {}", networkAddress, addresses[0]);
                    cachedDiscoNodes.add(new DiscoveryNode("#cloud-" + instance.getNodeName(), addresses[0], Version.CURRENT));
                }

            }
        } catch (Throwable e) {
            logger.warn("Exception caught during discovery {} : {}", e.getClass().getName(), e.getMessage());
            logger.trace("Exception caught during discovery", e);
        }

        logger.debug("{} node(s) added", cachedDiscoNodes.size());
        logger.debug("using dynamic discovery nodes {}", cachedDiscoNodes);

        return cachedDiscoNodes;
    }

    private List<RuntimeInstance> instances() {
        List<RuntimeInstance> ipset = new ArrayList<RuntimeInstance>();
        Gson gson = new Gson();

        try {
            // Connect to the pipe
            RandomAccessFile pipe = new RandomAccessFile("\\\\.\\pipe\\" + runtimeBridge, "rw");

            String runtimeInfo = pipe.readLine();
            logger.debug(runtimeInfo);

            Type runtimeListType = new TypeToken<List<RuntimeInstance>>() {}.getType();
            ipset = gson.fromJson(runtimeInfo,runtimeListType) ;

            pipe.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        return ipset;


    }

}
