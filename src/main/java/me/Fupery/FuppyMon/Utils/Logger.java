package me.Fupery.FuppyMon.Utils;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utility
 */
public class Logger {

    private static HashMap<String, AtomicInteger> logged = new HashMap<>();

    public static void limitCount(String id, int maxCount) {
        if (!logged.containsKey(id)) {
            logged.put(id, new AtomicInteger(maxCount));
        }
    }

    public static void log(String id, String message) {
        if (logged.containsKey(id)) {
            AtomicInteger remaining = logged.get(id);
            if (remaining.get() <= 0) {
                return;
            } else {
                remaining.decrementAndGet();
            }
        }
        Bukkit.getLogger().info(id + ": " + message);
    }

    public static void log(String id, String... messages) {
        String message = "";
        for (String s : messages) {
            message += s + ", ";
        }
        log(id, message);
    }

    public static void log(String id, Object... messages) {
        String message = "";
        for (Object s : messages) {
            message += s + ", ";
        }
        log(id, message);
    }
}
