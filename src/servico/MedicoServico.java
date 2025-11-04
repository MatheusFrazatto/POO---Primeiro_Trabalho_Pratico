package servico;

import modelo.*;

public class MedicoServico {
    private SecretariaServico secretariaServico;

    public MedicoServico() {
        this.secretariaServico = new SecretariaServico();
    }

    public boolean atualizarDadosPaciente(int idPaciente, boolean fuma, boolean bebe, boolean colesterol, boolean diabetes, String doencasCardiacas, String cirurgias, String alergias) {
        Paciente paciente = secretariaServico.buscarPaciente(idPaciente);

        if (paciente == null) {
            DadosAdicionais prontuario = paciente.getDadosAdicionais();
            prontuario.setFuma(fuma);
            prontuario.setBebe(bebe);
            prontuario.setColesterol(colesterol);
            prontuario.setDiabete(diabetes);
            prontuario.setDoencasCardiacas(doencasCardiacas);
            prontuario.setCirurgias(cirurgias);
            prontuario.setAlergias(alergias);
            return true;
        }
        return false;
    }
}
