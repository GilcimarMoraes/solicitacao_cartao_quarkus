package br.edu.fiap.cartoes.usecase;

import br.edu.fiap.cartoes.dto.TipoCartaoResponse;
import br.edu.fiap.cartoes.repository.ConsultaTiposDisponiveis;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Camada de aplicação a implementar: coordena uma operação, suas regras e transação.
 * O controller já chama estas assinaturas. Na migração para Clean, substituam
 * a dependência de DTO HTTP por entradas/saídas da aplicação e portas de saída.
 */
@ApplicationScoped
public class TipoCartaoUseCase {

    private final ConsultaTiposDisponiveis consultaTiposDisponiveis;

    @Inject
    public TipoCartaoUseCase(ConsultaTiposDisponiveis consultaTiposDisponiveis) {
        this.consultaTiposDisponiveis = consultaTiposDisponiveis;
    }

    /** Delegar à única consulta JDBC e mapear a resposta. */
    @Transactional
    public List<TipoCartaoResponse> listarDisponiveis() {
        return consultaTiposDisponiveis.listarDisponiveis();
    }
}
