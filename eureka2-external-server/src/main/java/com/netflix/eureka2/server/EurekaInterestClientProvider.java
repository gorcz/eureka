/*
 * Copyright 2014 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.eureka2.server;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import com.netflix.eureka2.client.EurekaInterestClient;
import com.netflix.eureka2.client.EurekaInterestClientBuilder;
import com.netflix.eureka2.metric.EurekaRegistryMetricFactory;
import com.netflix.eureka2.metric.client.EurekaClientMetricFactory;
import com.netflix.eureka2.server.config.EurekaCommonConfig;

/**
 * @author Tomasz Bak
 */
@Singleton
public class EurekaInterestClientProvider implements Provider<EurekaInterestClient> {

    private final EurekaCommonConfig config;
    private final EurekaClientMetricFactory clientMetricFactory;
    private final EurekaRegistryMetricFactory registryMetricFactory;

    @Inject
    public EurekaInterestClientProvider(EurekaCommonConfig config,
                                        EurekaClientMetricFactory clientMetricFactory,
                                        EurekaRegistryMetricFactory registryMetricFactory) {
        this.config = config;
        this.clientMetricFactory = clientMetricFactory;
        this.registryMetricFactory = registryMetricFactory;
    }

    @Override
    public EurekaInterestClient get() {
        return new EurekaInterestClientBuilder()
                .withTransportConfig(config)
                .withRegistryConfig(config)
                .withClientMetricFactory(clientMetricFactory)
                .withRegistryMetricFactory(registryMetricFactory)
                .withServerResolver(WriteClusterResolvers.createInterestResolver(config))
                .build();
    }
}