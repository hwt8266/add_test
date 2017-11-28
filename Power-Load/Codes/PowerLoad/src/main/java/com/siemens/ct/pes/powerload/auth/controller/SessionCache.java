/**
 * 
 */
package com.siemens.ct.pes.powerload.auth.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * A single instance for caching the session information
 * 
 * @author Hao Liu
 *
 */
public class SessionCache {

    private volatile static SessionCache instance;

    /**
     * Key: Session ID, Value: Last operate time
     */
    private Map<String, Long> cache;

    /**
     * Constructor
     */
    private SessionCache() {
        cache = new HashMap<String, Long>();
    }

    /**
     * Get instance
     * 
     * @return
     */
    public static SessionCache getInstance() {
        if (instance == null) {
            synchronized (SessionCache.class) {
                if (instance == null) {
                    instance = new SessionCache();
                }
            }
        }

        return instance;
    }

    /**
     * @return the cache
     */
    public Map<String, Long> getCache() {
        return cache;
    }
}