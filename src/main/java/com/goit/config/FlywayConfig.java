package com.goit.config;

import com.goit.crud.util.Constants;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class FlywayConfig {
    private static final String DEFAULT_FILE_NAME = "application.properties";

    private Flyway flyway;

    public FlywayConfig setup() throws IOException {
        setup(DEFAULT_FILE_NAME);
        return this;
    }

    public FlywayConfig setup(String propertiesFileName) throws IOException {
        Properties properties = new Properties();
        properties.load(FlywayConfig.class.getClassLoader().getResourceAsStream(propertiesFileName));

        String url = properties.getProperty(Constants.FLYWAY_CONNECTION_URL);
        String username = properties.getProperty(Constants.FLYWAY_USER);
        String password = properties.getProperty(Constants.FLYWAY_PASSWORD);

        Location migrations = new Location("db/migration");
        flyway = Flyway.configure()
                .encoding(StandardCharsets.UTF_8)
                .locations(migrations)
                .dataSource(url, username, password)
                .loggers(properties.getProperty(Constants.FLYWAY_LOGGER))
                .placeholderReplacement(false)
                .failOnMissingLocations(true)
                .load();
        return this;
    }

    public void migrate() {
        flyway.migrate();
    }
}