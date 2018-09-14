package com.example.demo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
 
      
    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver()  {
        CookieLocaleResolver resolver= new CookieLocaleResolver();
        resolver.setCookieDomain("myAppLocaleCookie");
        // 60 minutes 
        resolver.setCookieMaxAge(60*60); 
        return resolver;
    } 
     
    @Bean(name = "messageSource")
    public MessageSource getMessageResource()  {
        ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();
         
        // Read i18n/messages_xxx.properties file.
        // For example: i18n/messages_en.properties
        messageResource.setBasename("classpath:i18n/messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // Load file: validation.properties
        messageSource.setBasename("classpath:validation");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
         
         
        registry.addInterceptor(localeInterceptor).addPathPatterns("/*");
    }
    
//    @Bean(name = "messageSource")
//    public MessageSource getMessageResource() {
//        ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
// 
//        // Read i18n/messages_xxx.properties file.
//        // For example: i18n/messages_en.properties
//        messageResource.setBasename("classpath:i18n/messages");
//        messageResource.setDefaultEncoding("UTF-8");
//        return messageResource;
//    }
// 
//    // To solver URL like:
//    // /SomeContextPath/en/login2
//    // /SomeContextPath/vi/login2
//    // /SomeContextPath/fr/login2
//    @Bean(name = "localeResolver")
//    public LocaleResolver getLocaleResolver() {
//        LocaleResolver resolver = new UrlLocaleResolver();
//        return resolver;
//    }
// 
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
// 
//        UrlLocaleInterceptor localeInterceptor = new UrlLocaleInterceptor();
// 
//        registry.addInterceptor(localeInterceptor).addPathPatterns("/en/*", "/fr/*", "/vi/*");
//    }
     
}
