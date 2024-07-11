package com.cubmask.distributed_startup;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {

    @Value("${zookeeper.connection-string}")
    private String connectionString;

    @Value("${zookeeper.retry-policy.base-sleep-time}")
    private int baseSleepTime;

    @Value("${zookeeper.retry-policy.max-retries}")
    private int maxRetries;

    @Bean
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTime, maxRetries);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        client.start();
        return client;
    }
}
