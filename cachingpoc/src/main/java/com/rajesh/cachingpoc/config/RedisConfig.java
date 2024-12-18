package com.rajesh.cachingpoc.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

	
//	@Bean
//	  LettuceConnectionFactory connectionFactory() {
//	    return new LettuceConnectionFactory();
//	  }
	@Bean
	public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
	  RedisTemplate<?, ?> template = new RedisTemplate<>();
	  template.setConnectionFactory(connectionFactory);

	  return template;
	}
	

	  @Bean
	  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
	    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig() //
	        .prefixCacheNameWith(this.getClass().getPackageName() + ".") //
	        .entryTtl(Duration.ofHours(1)) //
	        .disableCachingNullValues();

	    return RedisCacheManager.builder(connectionFactory) //
	        .cacheDefaults(config) //
	        .build();
	  }
}

