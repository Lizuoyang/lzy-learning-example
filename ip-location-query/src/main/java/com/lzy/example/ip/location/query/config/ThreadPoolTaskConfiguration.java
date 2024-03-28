package com.lzy.example.ip.location.query.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池自动配置类
 * @author Lizuoyang
 * @date 2024/03/28
 */
@Configuration
@EnableAsync
public class ThreadPoolTaskConfiguration {
    @Value("${thread.pool.core-pool-size}")
    private Integer corePoolSize;
    @Value("${thread.pool.max-pool-size}")
    private Integer maxPoolSize;
    @Value("${thread.pool.keep-alive-time}")
    private Integer keepAliveTime;
    @Value("${thread.pool.queue-capacity}")
    private Integer queueCapacity;
    @Value("${thread.pool.thread-name-prefix}")
    private String threadNamePrefix;

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }
}
