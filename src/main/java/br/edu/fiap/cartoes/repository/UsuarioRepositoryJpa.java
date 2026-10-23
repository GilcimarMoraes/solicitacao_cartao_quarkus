package br.edu.fiap.cartoes.repository;
import br.edu.fiap.cartoes.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

/**
 * Adaptador JPA: injete EntityManager, escreva JPQL parametrizada e retorne entidades.
 * Não chamar controller nem gerar token nesta camada. Gravações participam da transação aberta pelo use case.
 */
@ApplicationScoped
public class UsuarioRepositoryJpa implements UsuarioRepository {

    private static final String BUSCAR_POR_EMAIL = """
            SELECT usuario FROM Usuario usuario
            WHERE usuario.email = :email
            """;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Usuario salvar(Usuario usuario) {
        if( usuario.getId() == null ) {
            entityManager.persist( usuario );
            return usuario;
        }
        return entityManager.merge( usuario );
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        String normalizado = Usuario.normalizarEmail( email );
        if( normalizado == null || normalizado.isBlank() ) {
            return Optional.empty();
        }
        return entityManager.createQuery( BUSCAR_POR_EMAIL, Usuario.class )
                .setParameter( "email", normalizado )
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        if( id == null ) {
            return Optional.empty();
        }

        return Optional.ofNullable( entityManager.find( Usuario.class, id ) );
    }
}
