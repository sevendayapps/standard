package com.microl.standard.logging.log4j2;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.AbstractLogger;

/**
 * Created by vietlk on 01/06/2016.
 */
public class StandardLogger extends AbstractLogger {

    @Override
    public boolean isEnabled(Level level, Marker marker, Message message, Throwable throwable) {
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Object o, Throwable throwable) {
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String s, Throwable throwable) {
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String s) {
        return false;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String s, Object... objects) {
        return false;
    }

    @Override
    public void logMessage(String s, Level level, Marker marker, Message message, Throwable throwable) {

    }

    @Override
    public Level getLevel() {
        return null;
    }
}
