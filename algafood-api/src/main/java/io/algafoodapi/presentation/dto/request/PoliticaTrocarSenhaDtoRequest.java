package io.algafoodapi.presentation.dto.request;

public interface PoliticaTrocarSenhaDtoRequest<I, S> {

  I getId();

  void setId(I id);

  S getSenhaAtual();

  void setSenhaAtual(S senhaAtual);

  S getSenhaNova();

  void setSenhaNova(S senhaNova);
}
