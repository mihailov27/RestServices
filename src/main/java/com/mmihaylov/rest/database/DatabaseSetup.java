package com.mmihaylov.rest.database;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hsqldb.server.Server;
import javax.annotation.PreDestroy;
import java.io.File;

@Deprecated
public class DatabaseSetup {

    private static final Logger LOG = LogManager.getLogger(DatabaseSetup.class);

    private Server server;

    //@Inject
    public void setUp() {
        LOG.info("");
        // set up server
        server = new Server();
        server.setAddress("127.0.0.1");
        server.setPort(1234);
        server.setDatabaseName(0, "NEWS_DB");
        server.setDatabasePath(0, getDbFolder().getAbsolutePath() + "/NEWS_DB");
        server.setTrace(true);
        server.stop();
        server.start();
    }

    private File getDbFolder() {
        File file = new File("database");
        boolean exists = file.exists() && file.isDirectory();
        if(!exists) {
            file.mkdir();
        }
        return file;
    }

    @PreDestroy
    public void stop() {
        LOG.info("Stop db server.");
        server.stop();
    }
}