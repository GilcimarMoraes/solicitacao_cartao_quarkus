package br.edu.fiap.cartoes.usecase;

import br.edu.fiap.cartoes.dto.UsuarioRequest;
import br.edu.fiap.cartoes.dto.UsuarioResponse;
import br.edu.fiap.cartoes.entity.Usuario;
import br.edu.fiap.cartoes.exception.EmailJaCadastradoException;
import br.edu.fiap.cartoes.exception.EntradaInvalidaException;
import br.edu.fiap.cartoes.mapper.UsuarioMapper;
import br.edu.fiap.cartoes.repository.UsuarioRepository;
import br.edu.fiap.cartoes.security.SenhaBCrypt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.nio.charset.StandardCharsets;

/**
 * Camada de aplicação a implementar: coordena uma operação, suas regras e transação.
 * O controller já chama estas assinaturas. Na migração para Clean, substituam
 * a dependência de DTO HTTP por entradas/saídas da aplicação e portas de saída.
 */
@ApplicationScoped
public class UsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final SenhaBCrypt senhaBCrypt;

    @Inject
    public UsuarioUseCase(UsuarioRepository usuarioRepository, SenhaBCrypt senhaBCrypt) {
        this.usuarioRepository = usuarioRepository;
        this.senhaBCrypt = senhaBCrypt;
    }

    /** Normalizar e-mail, rejeitar duplicado, gerar hash BCrypt, persistir e mapear sem senha. */
    @Transactional
    public UsuarioResponse cadastrar(UsuarioRequest request) {

        String normalizado = Usuario.normalizarEmail( request.email() );
        if( normalizado == null || normalizado.isBlank() ) {
            throw new EntradaInvalidaException( "Email não pode ser nula ou vazia." );
        }

        if( request.senha().getBytes(StandardCharsets.UTF_8).length > 72 ) {
            throw new EntradaInvalidaException( "Senha excede 72 bytes em UTF-8." );
        }

        if( usuarioRepository.buscarPorEmail( normalizado ).isPresent() ) {
            throw new EmailJaCadastradoException();
        }

        String senhaHash = senhaBCrypt.criarHash( request.senha() );

        Usuario usuarioSalvo = usuarioRepository.salvar( new Usuario( request.nome(), normalizado, senhaHash ) );

        return UsuarioMapper.paraResponse( usuarioSalvo );
    }
}
