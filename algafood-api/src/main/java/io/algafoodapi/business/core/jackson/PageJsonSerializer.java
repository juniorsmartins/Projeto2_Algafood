package io.algafoodapi.business.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent // A anotação @JsonComponent é usada em aplicativos Spring que usam a biblioteca Jackson para
// processamento de JSON. Essa anotação permite que você crie um componente que será automaticamente registrado
// pelo Jackson e usado para serializar ou desserializar objetos JSON.
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

  // Método que pode ser chamado para solicitar a implementação para serializar valores do tipo que esse
  // serializador manipula.Parâmetros: valor – Valor a ser serializado; não pode ser nulo. gen – Gerador
  // usado para gerar serializadores de conteúdo Json resultantes – Provedor que pode ser usado para obter
  // serializadores para serializar o valor dos objetos contidos, se houver.
  @Override
  public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider serializers) throws IOException {

    gen.writeStartObject();

    gen.writeObjectField("content", page.getContent());
    gen.writeNumberField("size", page.getSize());
    gen.writeNumberField("totalElements", page.getTotalElements());
    gen.writeNumberField("totalPages", page.getTotalPages());
    gen.writeNumberField("number", page.getNumber());

    gen.writeEndObject();
  }
}

