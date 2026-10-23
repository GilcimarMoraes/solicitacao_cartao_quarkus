package br.edu.fiap.cartoes.mapper;

import br.edu.fiap.cartoes.dto.CartaoResponse;
import br.edu.fiap.cartoes.entity.Cartao;
import br.edu.fiap.cartoes.entity.SolicitacaoCartao;
import br.edu.fiap.cartoes.entity.TipoCartao;

public final class CartaoMapper {

    private CartaoMapper() {}

    public static CartaoResponse paraResponse(Cartao cartao) {
        if( cartao == null ) {
            return null;
        }
        SolicitacaoCartao solicitacao = cartao.getSolicitacao();
        TipoCartao tipo = solicitacao != null ? solicitacao.getTipoCartao() : null;

        return new CartaoResponse(
                cartao.getId(),
                solicitacao != null ? solicitacao.getId() : null,
                tipo != null ? tipo.getId() : null,
                tipo != null ? tipo.getNome() : null,
                cartao.getEmitidoEm(),
                cartao.getStatus()

        );
    }
}
