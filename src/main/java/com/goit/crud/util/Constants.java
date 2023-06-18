package com.goit.crud.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String CONNECTION_URL = "com.h2.url";
    public static final String CONNECTION_USERNAME = "com.h2.username";
    public static final String CONNECTION_PASSWORD = "com.h2.password";
    public static final String CONNECTION_AUTOCOMMIT = "com.h2.autocommit";
    public static final String TRANSACTION_ISOLATION = "com.h2.isolation.level";
    public static final String FLYWAY_CONNECTION_URL = "org.flywaydb.url";
    public static final String FLYWAY_LOGGER = "org.flywaydb.logger";
    public static final String FLYWAY_USER = "org.flywaydb.user";
    public static final String FLYWAY_PASSWORD = "org.flywaydb.password";
    public static final String LOG_PATTERN = "log4j.conversion.pattern";
    public static final String LOG_FILE = "log4j.appender.file";
    public static final String LOG_LEVEL = "log4j.logger.level";
    public static final String LOG_ENCODING = "log4j.logger.encoding";
}