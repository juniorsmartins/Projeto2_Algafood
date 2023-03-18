package io.algafoodapi.camada2_business.exception.http409;

public final class SenhaIncompativelException extends RegraDeNegocioVioladaException {

  private static final long serialVersionUID = 1L;

  public SenhaIncompativelException(String mensagem) {
    super(mensagem);
  }

  public SenhaIncompativelException() {
    this("A senha informada é incompatível com a atual.");
  }
}
