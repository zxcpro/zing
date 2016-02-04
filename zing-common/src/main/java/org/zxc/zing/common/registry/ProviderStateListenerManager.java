package org.zxc.zing.common.registry;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by xuanchen.zhao on 16-1-19.
 */
public class ProviderStateListenerManager implements ProviderStateListener{

    private static final ProviderStateListenerManager INSTANCE = new ProviderStateListenerManager();

    public static ProviderStateListenerManager getInstance() {
        return INSTANCE;
    }

    private final List<ProviderStateListener> listeners = Lists.newArrayList();

    public void registerProviderStateListener(ProviderStateListener listener) {
        listeners.add(listener);
    }


    @Override
    public void onProviderChange(String serviceName) {
        for (ProviderStateListener listener : listeners) {
            listener.onProviderChange(serviceName);
        }
    }
}
