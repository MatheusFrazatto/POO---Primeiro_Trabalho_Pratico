package modelo;

/**
 * Representa um endereço postal completo.
 * Esta classe é utilizada para armazenar os dados de localização de
 * entidades, como {@link Paciente}.
 */
public class Endereco {
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    /**
     * Cria uma nova instância de Endereco.
     *
     * @param rua         A rua.
     * @param numero      O número.
     * @param complemento O complemento (ex: "Apto 101"). Pode ser vazio.
     * @param bairro      O bairro.
     * @param cidade      A cidade.
     * @param uf          A sigla do estado (UF).
     */
    public Endereco(String rua, String numero, String complemento, String bairro, String cidade, String uf) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    /**
     * Formata o endereço em uma única String legível.
     * Exemplo: "Rua A, 100 - Apto 101, Centro, Maringa/PR"
     * O complemento só é incluído se não estiver vazio.
     *
     * @return A string do endereço formatado.
     */
    @Override
    public String toString() {
        boolean temComplemento = complemento != null && !complemento.isEmpty();
        return rua + ", " + numero + (complemento.isEmpty() ? "" : " - " + complemento) + ", " + bairro + ", " + cidade + "/" + uf;
    }
}