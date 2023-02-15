package io.algafoodapi.domain.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    @Bean
    public LocalValidatorFactoryBean integrarMensagensDeBeanValidationComSpring(final MessageSource messageSource) {
        var bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}

