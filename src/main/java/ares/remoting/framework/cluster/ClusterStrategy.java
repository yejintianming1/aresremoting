package ares.remoting.framework.cluster;

import ares.remoting.framework.model.ProviderService;

import java.util.List;

public interface ClusterStrategy {

    /**
     * 负载均衡算法
     * @param providerServices
     * @return
     */
    public ProviderService select(List<ProviderService> providerServices);
}
