package com.senla.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
    /*
    * Без этого класса моq фильтр просто игнорируется
    * This would simply only register the springSecurityFilterChain Filter for every URL in your application.
    * */
}
