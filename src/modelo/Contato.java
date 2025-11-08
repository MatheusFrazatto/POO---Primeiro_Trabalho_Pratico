package modelo;

/**
 * Guarda o telefone e email de alguém.
 */
public class Contato {
    private String telefone;
    private String email;

    /**
     * Cria um novo contato.
     *
     * @param telefone O número de telefone.
     * @param email O endereço de email.
     */
    public Contato(String telefone, String email) {
        this.telefone = telefone;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}