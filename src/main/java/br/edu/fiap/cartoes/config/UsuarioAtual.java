package br.edu.fiap.cartoes.config;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import org.eclipse.microprofile.jwt.JsonWebToken;
/**
 * Ponte HTTP pronta: lê o titular do JWT já verificado pelo Quarkus.
 * O cliente não escolhe usuarioId no JSON nem na query string. Os use cases ainda
 * precisam comparar esse ID com o dono do recurso; autenticação não basta.
 */
@RequestScoped
public class UsuarioAtual {
    @Inject JsonWebToken jwt;
    public Long id() {
        Object valor = jwt.getClaim("usuarioId");
        if( valor instanceof Number n) {
            long id = n.longValue();
            if( id > 0 ) return id;
        }
        throw new NotAuthorizedException( "Bearer" );
    }
}
