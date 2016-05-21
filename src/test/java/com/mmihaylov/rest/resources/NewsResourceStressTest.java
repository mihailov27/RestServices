package com.mmihaylov.rest.resources;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class NewsResourceStressTest {

    private static Logger LOG = Logger.getLogger(NewsResourceStressTest.class.getName());

    private static final String NEWS_SERVICE_ADDRESS = "http://localhost:8080/RestServices/news/%d";
    private static final String RESPONSE_MESSAGE = "%s: Invoke URL '%s' returned status '%d'";
    private static final String SUCCESS = "Success";
    private static final String FAILURE = "Failure";

    Client client;
    ExecutorService executorService;

    int numOfSuccessfulRequests = 0;
    int numOfFailures = 0;
    int totalAttempts = 0;

    @Before
    public void setUp() {
        LOG.info("Set up news resource stress test.");
        client = Client.create();
        executorService = Executors.newFixedThreadPool(8);
    }

    @After
    public void tearDown() throws InterruptedException {
        LOG.info("Terminate unit test and close resources.");
        executorService.shutdown();
        while(!executorService.isShutdown()) {
            LOG.info("Waiting for the service to complete.");
            Thread.currentThread().sleep(1000);
        }
        LOG.info(String.format("Results - total request: %d, successful requests: %d, failed: %d",
                totalAttempts, numOfSuccessfulRequests, numOfFailures));
    }

    @Test
    public void stressTest() {
        //int start = 354650;
        for(int i = 352000; i < 354650; i++) {
            executorService.submit(new NewsRequest(i));
            if(i % 10 == 0) {
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException ie) {
                    LOG.severe("The thread was awaken early." + ie);
                }
            }
        }
    }

    private void getNews(int id) {

        String address = String.format(NEWS_SERVICE_ADDRESS, id);
        LOG.info(String.format("Call now '%s'", address));
        WebResource webResource = client.resource(address);

        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        int status = response.getStatus();
        if (status == 200) {
            LOG.info(String.format(RESPONSE_MESSAGE, SUCCESS, address, status));
            numOfSuccessfulRequests+=1;
        } else {
            LOG.info(String.format(RESPONSE_MESSAGE, FAILURE, address, status));
            numOfFailures+=1;
        }
        totalAttempts+=1;
    }

    private class NewsRequest implements Runnable {

        int id;

        public NewsRequest(int id) { this.id = id; }

        @Override
        public void run() {
            getNews(id);
        }
    }
}
