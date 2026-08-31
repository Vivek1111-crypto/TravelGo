package com.travelgo_Booking_service.config;

//package com.travelgo_Booking_service.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> {

            ServletRequestAttributes attributes =
                    (ServletRequestAttributes)
                            RequestContextHolder.getRequestAttributes();

            if (attributes != null) {

                HttpServletRequest request =
                        attributes.getRequest();

                String authorization =
                        request.getHeader("Authorization");

                if (authorization != null &&
                        authorization.startsWith("Bearer ")) {

                    requestTemplate.header(
                            "Authorization",
                            authorization);
                }
            }
        };
    }
}