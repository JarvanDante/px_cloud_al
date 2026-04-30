package com.example.px_bff_service.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson 全局配置。
 *
 * <p>BFF 是 HTTP 接口出口，Controller 返回对象时会由这里配置的 Jackson
 * 把 LocalDateTime 统一转换成前端需要的时间格式。</p>
 */
@Configuration
public class JacksonConfig {
    /** 接口统一时间格式，例如：2026-04-30 21:45:10。 */
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

        return builder -> {
            // Java 对象返回给前端时：LocalDateTime -> JSON 字符串。
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            // 前端提交请求体时：JSON 字符串 -> LocalDateTime。
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        };
    }
}
