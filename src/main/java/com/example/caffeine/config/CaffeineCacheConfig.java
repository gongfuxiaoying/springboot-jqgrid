package com.example.caffeine.config;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import lombok.NonNull;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * <p>java方式：caffeine缓存配置</p>
 * Created by zhezhiyong@163.com on 2017/9/22.
 */
@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    private static final int DEFAULT_MAXSIZE = 1000;
    private static final int DEFAULT_TTL = 3600;

    /**
     * 个性化配置缓存
     */
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        //把各个cache注册到cacheManager中，CaffeineCache实现了org.springframework.cache.Cache接口
        ArrayList<CaffeineCache> caches = new ArrayList<>();
        for (Caches c : Caches.values()) {
            caches.add(new CaffeineCache(c.name(),
                      Caffeine.newBuilder()
                              .recordStats(() -> new StatsCounter() {
                                          @Override
                                          public void recordHits(@NonNegative int count) {
                                              System.out.println("recordHits:" + count);
                                          }

                                          @Override
                                          public void recordMisses(@NonNegative int count) {
                                              System.out.println("recordMisses:" + count);
                                          }

                                          @Override
                                          public void recordLoadSuccess(@NonNegative long loadTime) {
                                              System.out.println("recordLoadSuccess:" + loadTime);
                                          }

                                          @Override
                                          public void recordLoadFailure(@NonNegative long loadTime) {
                                              System.out.println("recordLoadFailure:" + loadTime);
                                          }

                                          @Override
                                          public void recordEviction() {
                                              System.out.println("recordEviction...");
                                          }

                                          @Override
                                          public @NonNull CacheStats snapshot() {
                                              return null;
                                          }
                                      })
                            .expireAfterWrite(c.getTtl(), TimeUnit.SECONDS)
                            .maximumSize(c.getMaxSize())
                            .build())
            );

        }
        manager.setCaches(caches);
        return manager;
    }

    /**
     * 定义cache名称、超时时长秒、最大个数
     * 每个cache缺省3600秒过期，最大个数1000
     */
    public enum Caches {
        user1(1, 2),
        user5(5, 2),
        user10(10, 10),
        info(5),
        role;

        private int maxSize = DEFAULT_MAXSIZE;    //最大數量
        private int ttl = DEFAULT_TTL;        //过期时间（秒）

        Caches() {
        }

        Caches(int ttl) {
            this.ttl = ttl;
        }

        Caches(int ttl, int maxSize) {
            this.ttl = ttl;
            this.maxSize = maxSize;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

        public int getTtl() {
            return ttl;
        }

        public void setTtl(int ttl) {
            this.ttl = ttl;
        }
    }

}