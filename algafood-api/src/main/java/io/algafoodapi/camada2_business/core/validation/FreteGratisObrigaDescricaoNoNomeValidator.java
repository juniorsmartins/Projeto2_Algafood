package io.algafoodapi.camada2_business.core.validation;

import io.algafoodapi.camada1_presentation.dto.request.RestauranteDtoRequest;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public final class FreteGratisObrigaDescricaoNoNomeValidator implements ConstraintValidator<FreteGratisObrigaDescricaoNoNomeAnotation, RestauranteDtoRequest> {

    private String valorTaxaFrete;
    private String descricaoNome;
    private String descricaoObrigatoria;

    @Override
    public void initialize(final FreteGratisObrigaDescricaoNoNomeAnotation constraintAnnotation) {
        this.valorTaxaFrete = constraintAnnotation.valorTaxaFrete();
        this.descricaoNome = constraintAnnotation.descricaoNome();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(final RestauranteDtoRequest restauranteDtoRequest,
                           final ConstraintValidatorContext constraintValidatorContext) {

        var isValido = true;

        try {
            var valorDoFrete = (BigDecimal) BeanUtils.getPropertyDescriptor(restauranteDtoRequest.getClass(), valorTaxaFrete)
                    .getReadMethod().invoke(restauranteDtoRequest);

            var descricao = (String) BeanUtils.getPropertyDescriptor(restauranteDtoRequest.getClass(), descricaoNome)
                    .getReadMethod().invoke(restauranteDtoRequest);

            if (valorDoFrete != null && BigDecimal.ZERO.compareTo(valorDoFrete) == 0 && descricao != null) {
                isValido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }

        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return isValido;
    }
}

