package modelo;

/**
 * Encapsula as informações de contato de uma entidade (como {@link Paciente}).
 * Esta classe armazena dados essenciais para comunicação, como
 * telefone (preferencialmente celular para SMS) e e-mail.
 * É utilizada para lembretes de consulta e outras comunicações.
 */
public class Contato {
    private String telefone;
    private String email;

    /**
     * Cria uma nova instância de Contato.
     *
     * @param telefone O número de telefone (ex: "44998765432").
     * @param email O endereço de e-mail (ex: "paciente@email.com"). Pode ser vazio.
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