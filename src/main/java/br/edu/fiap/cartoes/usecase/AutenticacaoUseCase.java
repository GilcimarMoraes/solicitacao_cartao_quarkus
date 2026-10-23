package br.edu.fiap.cartoes.usecase;

import br.edu.fiap.cartoes.dto.LoginRequest;
import br.edu.fiap.cartoes.dto.TokenResponse;
import br.edu.fiap.cartoes.entity.Usuario;
import br.edu.fiap.cartoes.exception.CredenciaisInvalidasException;
import br.edu.fiap.cartoes.repository.UsuarioRepository;
import br.edu.fiap.cartoes.security.JwtService;
import br.edu.fiap.cartoes.security.SenhaBCrypt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Camada de aplicação a implementar: coordena uma operação, suas regras e transação.
 * O controller já chama estas assinaturas. Na migração para Clean, substituam
 * a dependência de DTO HTTP por entradas/saídas da aplicação e portas de saída.
 */
@ApplicationScoped
public class AutenticacaoUseCase {

    private final UsuarioRepository usuarioRepository;
    private final SenhaBCrypt senhaBCrypt;
    private final JwtService jwtService;

    @Inject
    public AutenticacaoUseCase(UsuarioRepository usuarioRepository, SenhaBCrypt senhaBCrypt, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.senhaBCrypt = senhaBCrypt;
        this.jwtService = jwtService;
    }

    /** Buscar e-mail normalizado, conferir BCrypt, negar genericamente ou emitir JWT. */
    public TokenResponse login(LoginRequest request) {
        String normalizado = Usuario.normalizarEmail( request.email() );

        Usuario usuario = usuarioRepository.buscarPorEmail( normalizado )
                .orElseThrow( CredenciaisInvalidasException::new );

        if(!senhaBCrypt.confere( request.senha(), usuario.getSenhaHash() ) ) {
            throw new CredenciaisInvalidasException();
        }

        return jwtService.emitir( usuario );
    }
}
