package com.example.day23.config;

import com.example.day23.domain.Task;
import com.example.day23.domain.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 相关 Bean 配置。
 *
 * <p><b>为什么不用默认 JDK 序列化？</b> 默认会把对象变成二进制乱码，CLI 里看不懂、换语言读不了。
 * 这里 Key 用 String、Value 用 JSON，既人类可读又跨语言。</p>
 */
@Configuration
public class RedisConfig {

    /**
     * 通用模板：Key 固定为 String，Value 为任意 JSON 可序列化对象。
     * 教学上显式声明，便于看清序列化器选择；生产可再细化。
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(buildMapper(), Object.class);

        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /** 类型化模板：避免强转，编译期更安全。 */
    @Bean
    public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory cf) {
        return typedTemplate(cf, User.class);
    }

    @Bean
    public RedisTemplate<String, Task> taskRedisTemplate(RedisConnectionFactory cf) {
        return typedTemplate(cf, Task.class);
    }

    private <T> RedisTemplate<String, T> typedTemplate(RedisConnectionFactory cf, Class<T> clazz) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(cf);
        StringRedisSerializer keySer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<T> valSer = new Jackson2JsonRedisSerializer<>(buildMapper(), clazz);
        template.setKeySerializer(keySer);
        template.setHashKeySerializer(keySer);
        template.setValueSerializer(valSer);
        template.setHashValueSerializer(valSer);
        template.afterPropertiesSet();
        return template;
    }

    private ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 只认字段、忽略 getter，避免 Kotlin/data class 之类可见性坑
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper;
    }
}
