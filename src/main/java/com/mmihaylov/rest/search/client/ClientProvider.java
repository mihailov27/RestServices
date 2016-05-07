package com.mmihaylov.rest.search.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import javax.inject.Provider;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Provider of elastic-search client entity.
 */
public class ClientProvider implements Provider<Client> {

    private static final Logger LOGGER = LogManager.getLogger(ClientProvider.class);

    Client client;

    public Client get() {
        if(client == null) {
            init();
        }
        return client;
    }

    private void init() {
        try {
            Map<String, String> properties = new HashMap();
            properties.put("cluster.name", "singleNodeCluster");
            Settings settings = Settings.builder().put(properties).build();
            client = TransportClient.builder().settings(settings)
                    .build().addTransportAddress(
                            new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException uhe) {
            LOGGER.error("Failed to init an elastic client.", uhe);
        }
    }
}
