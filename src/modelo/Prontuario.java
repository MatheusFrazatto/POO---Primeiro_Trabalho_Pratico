package modelo;

public class Prontuario {
    private boolean fuma;
    private boolean bebe;
    private boolean colesterol;
    private boolean diabete;
    private String doencasCardiacas;
    private String cirurgias;
    private String alergias;

    public Prontuario(boolean fuma, boolean bebe, boolean colesterol, boolean diabete, String doencaCardiaca, String cirurgias, String alergias) {
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
