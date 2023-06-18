package com.goit.config;

import org.apache.log4j.*;

import java.util.Properties;

import static com.goit.crud.util.Constants.LOG_ENCODING;
import static com.goit.crud.util.Constants.LOG_FILE;
import static com.goit.crud.util.Constants.LOG_LEVEL;
import static com.goit.crud.util.Constants.LOG_PATTERN;
import java.io.IOException;

public class LogConfig {
    private static final String DEFAULT_FILE_NAME = "application.properties";

    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(LogConfig.class.getClassLoader().getResourceAsStream(DEFAULT_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // creates pattern layout
        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern(PROPERTIES.getProperty(LOG_PATTERN));

        // creates console appender
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(layout);
        consoleAppender.setEncoding(PROPERTIES.getProperty(LOG_ENCODING));
        consoleAppender.activateOptions();

        // creates file appender
        DailyRollingFileAppender rollingFileAppender = new DailyRollingFileAppender();
        rollingFileAppender.setEncoding(PROPERTIES.getProperty(LOG_ENCODING));
        rollingFileAppender.setFile(PROPERTIES.getProperty(LOG_FILE));
        rollingFileAppender.setLayout(layout);
        rollingFileAppender.setDatePattern("'.'yyyy-MM-dd");
        rollingFileAppender.activateOptions();

        // configures the root logger
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.toLevel(PROPERTIES.getProperty(LOG_LEVEL)));
        rootLogger.removeAllAppenders();
        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(rollingFileAppender);
    }
}