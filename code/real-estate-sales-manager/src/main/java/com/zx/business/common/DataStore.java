package com.zx.business.common;

import java.util.HashMap;
import java.util.Map;

public class DataStore {

    public final Map<Integer, Long> expireInfo = new HashMap<>();

    private DataStore() {
    }

    private static class DataStoreInstance {
        private static final DataStore INSTANCE = new DataStore();
    }

    public static DataStore getInstance() {
        return DataStoreInstance.INSTANCE;
    }
}
