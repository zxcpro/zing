package org.zxc.zing.common.registry.function;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.zxc.zing.common.entity.ProviderInfo;

import java.util.List;

/**
 * Created by xuanchen.zhao on 16-1-17.
 */
public class ServerStr2ProviderInfoTransformer implements Function<String, ProviderInfo>{

    public static final ServerStr2ProviderInfoTransformer INSTANCE = new ServerStr2ProviderInfoTransformer();

    @Override
    public ProviderInfo apply(String serverAddress) {
        if (Strings.isNullOrEmpty(serverAddress)) {
            return null;
        }
        List<String> splitStr = Lists.newArrayList(Splitter.on(":").split(serverAddress));
        if (CollectionUtils.isNotEmpty(splitStr) && splitStr.size() == 2) {
            return new ProviderInfo(splitStr.get(0), Integer.valueOf(splitStr.get(1)));
        }
        return null;
    }
}
