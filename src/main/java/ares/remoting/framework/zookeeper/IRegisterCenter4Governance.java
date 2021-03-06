package ares.remoting.framework.zookeeper;

import ares.remoting.framework.model.InvokerService;
import ares.remoting.framework.model.ProviderService;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 服务治理接口
 */
public interface IRegisterCenter4Governance {

    /**
     * 获取服务提供者列表与服务消费者列表
     * @param serviceName
     * @param appKey
     * @return
     */
    Pair<List<ProviderService>, List<InvokerService>> queryProvidersAndInvokers(String serviceName, String appKey);
}
