package br.edu.fiap.cartoes.repository;
import br.edu.fiap.cartoes.entity.TipoCartao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

/** Adaptador JPA a implementar: buscar o tipo por ID ao criar uma solicitação. */
@ApplicationScoped
public class TipoCartaoRepositoryJpa implements TipoCartaoRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<TipoCartao> buscarPorId(Long id) {
        if( id == null ) return Optional.empty();
        return Optional.ofNullable( entityManager.find( TipoCartao.class, id ) );
    }
}
