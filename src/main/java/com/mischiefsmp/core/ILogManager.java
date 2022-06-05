package com.mischiefsmp.core;

import java.util.logging.Level;

public interface ILogManager {
    void log(Object message, Object... args);
    public void log(Object message, Level level, Object... args);
}
