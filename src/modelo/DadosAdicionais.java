package modelo;

/**
 * Armazena o histórico de saúde e hábitos de vida do {@link Paciente} (Anamnese).
 * Esta classe é preenchida e gerenciada pelo {@link Medico} e contém informações
 * cruciais para o diagnóstico e tratamento, como histórico de tabagismo,
 * consumo de álcool, condições crônicas (diabetes, colesterol),
 * cirurgias prévias e alergias.
 */
public class DadosAdicionais {
    private boolean fuma;
    private boolean bebe;
    private boolean colesterol;
    private boolean diabete;
    private String doencasCardiacas;
    private String cirurgias;
    private String alergias;

    /**
     * Construtor padrão.
     * Cria uma instância de DadosAdicionais vazia (com todos os campos
     * booleanos como {@code false} e Strings como {@code null}),
     * pronta para ser preenchida, geralmente na criação de um novo Paciente.
     */
    public DadosAdicionais() {
    }

    /**
     * Construtor completo para criar um histórico de saúde com dados predefinidos.
     *
     * @param fuma Se fuma.
     * @param bebe Se bebe.
     * @param colesterol Se tem colesterol.
     * @param diabete Se tem diabetes.
     * @param doencaCardiaca Doenças cardíacas.
     * @param cirurgias Cirurgias prévias.
     * @param alergias Alergias.
     */
    public DadosAdicionais(boolean fuma, boolean bebe, boolean colesterol, boolean diabete, String doencaCardiaca, String cirurgias, String alergias) {
        this.fuma = fuma;
        this.bebe = bebe;
        this.colesterol = colesterol;
        this.diabete = diabete;
        this.doencasCardiacas = doencaCardiaca;
        this.cirurgias = cirurgias;
        this.alergias = alergias;
    }

    public boolean isFuma() {
        return fuma;
    }

    public void setFuma(boolean fuma) {
        this.fuma = fuma;
    }

    public boolean isBebe() {
        return bebe;
    }

    public void setBebe(boolean bebe) {
        this.bebe = bebe;
    }

    public boolean isColesterol() {
        return colesterol;
    }

    public void setColesterol(boolean colesterol) {
        this.colesterol = colesterol;
    }

    public boolean isDiabete() {
        return diabete;
    }

    public void setDiabete(boolean diabete) {
        this.diabete = diabete;
    }

    public String getDoencasCardiacas() {
        return doencasCardiacas;
    }

    public void setDoencasCardiacas(String doencasCardiacas) {
        this.doencasCardiacas = doencasCardiacas;
    }

    public String getCirurgias() {
        return cirurgias;
    }

    public void setCirurgias(String cirurgias) {
        this.cirurgias = cirurgias;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }
}