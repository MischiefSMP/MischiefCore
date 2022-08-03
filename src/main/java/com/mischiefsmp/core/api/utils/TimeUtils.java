package com.mischiefsmp.core.api.utils;

import java.time.Instant;

public class TimeUtils {
    public static long getUnixTime() {
        return Instant.now().getEpochSecond();
    }

    public static long minutesToUnix(int minutes) {
        return 60L * minutes;
    }

    public static long hoursToUnix(int hours) {
        return minutesToUnix(60 *  hours);
    }
}
