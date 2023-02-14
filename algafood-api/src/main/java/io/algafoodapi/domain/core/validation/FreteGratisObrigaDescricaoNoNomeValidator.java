package io.algafoodapi.domain.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public final class FreteGratisObrigaDescricaoNoNomeValidator implements ConstraintValidator<FreteGratisObrigaDescricaoNoNomeAnotation, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(final FreteGratisObrigaDescricaoNoNomeAnotation constraintAnnotation) {
        this.valorField = constraintAnnotation.valorField();
        this.descricaoField = constraintAnnotation.descricaoField();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(final Object objetoParaValidacao, final ConstraintValidatorContext constraintValidatorContext) {

        try {
            var valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoParaValidacao.getClass(), valorField)
                    .getReadMethod().invoke(objetoParaValidacao);

            if (valor == null) {
                return false;
            }

            var descricao = (String) BeanUtils.getPropertyDescriptor(objetoParaValidacao.getClass(), descricaoField)
                    .getReadMethod().invoke(objetoParaValidacao);

            if (BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                return descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }

        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return false;
    }
}

