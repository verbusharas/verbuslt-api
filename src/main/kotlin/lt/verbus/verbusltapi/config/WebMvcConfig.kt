package lt.verbus.verbusltapi.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig: WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        val maxAge = 3600L
        registry.addMapping("/**")
            .allowedOrigins("localhost:3000")
            .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE", "PING")
            .allowedHeaders(AUTH_HEADER, "Content-Type", "Accept-Language")
            .maxAge(maxAge);
    }
}