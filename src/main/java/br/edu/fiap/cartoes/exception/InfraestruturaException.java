package br.edu.fiap.cartoes.exception;

public class InfraestruturaException extends RuntimeException {
    public InfraestruturaException(String message) {
        super(message);
    }

    public InfraestruturaException( String message, Throwable causa ) {
        super( message, causa );
    }
}
