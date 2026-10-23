package br.edu.fiap.cartoes.repository;

import br.edu.fiap.cartoes.dto.TipoCartaoResponse;
import br.edu.fiap.cartoes.exception.InfraestruturaException;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador JDBC: use AgroalDataSource, PreparedStatement e try-with-resources.
 * Não chamar controller nem gerar token nesta camada. Esta é a única implementação JDBC obrigatória.
 */
@ApplicationScoped
public class TipoCartaoRepositoryJdbc implements ConsultaTiposDisponiveis {

    private static final String SQL = """
            SELECT id, nome, descricao, disponivel
            FROM tipos_cartao
            WHERE disponivel = ?
            ORDER BY nome
            """;

    private final AgroalDataSource dataSource;

    @Inject
    public TipoCartaoRepositoryJdbc(AgroalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<TipoCartaoResponse> listarDisponiveis() {
        List<TipoCartaoResponse> lista = new ArrayList<>();

        try( Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement( SQL ) ) {
            stmt.setBoolean( 1, true );
            try(ResultSet rs = stmt.executeQuery()) {
                while ( rs.next() ) {
                    lista.add( new TipoCartaoResponse(
                            rs.getLong( "id"),
                            rs.getString( "nome"),
                            rs.getString( "descricao"),
                            rs.getBoolean( "disponivel")
                    ));
                }
            }
        } catch (SQLException ex ) {
            throw new InfraestruturaException( "Falha ao listar tipos disponiveis.", ex );
        }
        return lista;
    }
}
