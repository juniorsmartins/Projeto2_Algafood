package io.algafoodapi.domain.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.model.Grupo;
import io.algafoodapi.domain.model.Produto;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.model.mixin.CidadeMixin;
import io.algafoodapi.domain.model.mixin.CozinhaMixin;
import io.algafoodapi.domain.model.mixin.GrupoMixin;
import io.algafoodapi.domain.model.mixin.ProdutoMixin;
import io.algafoodapi.domain.model.mixin.RestauranteMixin;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        super.setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        super.setMixInAnnotation(Cidade.class, CidadeMixin.class);
        super.setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        super.setMixInAnnotation(Grupo.class, GrupoMixin.class);
        super.setMixInAnnotation(Produto.class, ProdutoMixin.class);
    }
}

