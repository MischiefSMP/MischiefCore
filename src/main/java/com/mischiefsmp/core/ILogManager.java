package com.mischiefsmp.core;

import java.util.logging.Level;

public interface ILogManager {
    void log(Object message);
    void log(Object message, Level level);
    void logF(Object message, Object... args);
    void logF(Object message, Level level, Object... args);
}
