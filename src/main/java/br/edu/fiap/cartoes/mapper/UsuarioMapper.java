package br.edu.fiap.cartoes.mapper;

import br.edu.fiap.cartoes.dto.UsuarioResponse;
import br.edu.fiap.cartoes.entity.Usuario;

public final class UsuarioMapper {

    private UsuarioMapper() {}

    public static UsuarioResponse paraResponse(Usuario usuario ) {
        if( usuario == null ) return null;

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCriadoEm()
        );
    }
}
