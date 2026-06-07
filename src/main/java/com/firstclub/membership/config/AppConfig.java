package com.firstclub.membership.config;

import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    /**
     * Enables Virtual Threads (Project Loom) for Tomcat request handling.
     *
     * Without this: each HTTP request uses a platform (OS) thread — expensive,
     *               thread pool limited to ~200 concurrent requests.
     *
     * With this: each HTTP request gets a virtual thread — lightweight,
     *            supports thousands of concurrent requests with no extra config.
     *
     * This directly addresses the concurrency requirement in the problem statement.
     */
    @Bean
    public TomcatProtocolHandlerCustomizer<?> virtualThreadsForTomcat() {
        return protocolHandler ->
                protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }
}
