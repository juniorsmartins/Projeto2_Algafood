package io.algafoodapi.domain.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.model.mixin.RestauranteMixin;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        super.setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }
}
