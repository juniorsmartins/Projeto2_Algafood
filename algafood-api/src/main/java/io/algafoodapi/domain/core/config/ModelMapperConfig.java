package io.algafoodapi.domain.core.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Determina o grau de precisão entre os nomes dos campos de origem e destino (Strict é totalmente igual)
//        modelMapper.getConfiguration().setAmbiguityIgnored(true); // Ignora casos de ambiguidade, como o nome de uma propriedade de destino corresponder a mais de uma de origem
        modelMapper.getConfiguration().setPreferNestedProperties(true); // Determina se deve mapear as propriedades aninhadas
        modelMapper.getConfiguration().setSkipNullEnabled(true); // Determina se um campo deve ser ignorado em caso de seu valor ser null
        return modelMapper;
    }
}

