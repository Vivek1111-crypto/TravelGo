package com.travelgo_payment_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> {

            ServletRequestAttributes attributes =
                    (ServletRequestAttributes)
                            RequestContextHolder.getRequestAttributes();

            if (attributes != null) {

                String authorization =
                        attributes.getRequest()
                                .getHeader(HttpHeaders.AUTHORIZATION);

                if (authorization != null) {

                    requestTemplate.header(
                            HttpHeaders.AUTHORIZATION,
                            authorization
                    );

                    System.out.println(
                            "🔥 Authorization forwarded from Payment → Booking");
                }
            }
        };
    }
}