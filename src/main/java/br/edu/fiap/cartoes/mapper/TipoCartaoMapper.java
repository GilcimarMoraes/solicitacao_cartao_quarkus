package br.edu.fiap.cartoes.mapper;

import br.edu.fiap.cartoes.dto.TipoCartaoResponse;
import br.edu.fiap.cartoes.entity.TipoCartao;

public final class TipoCartaoMapper {

    private TipoCartaoMapper() {}

    public static TipoCartaoResponse paraResponse(TipoCartao tipoCartao) {

        return new TipoCartaoResponse(
                tipoCartao.getId(),
                tipoCartao.getNome(),
                tipoCartao.getDescricao(),
                tipoCartao.isDisponivel()
        );
    }
}
