package servico;

import modelo.Consulta;
import modelo.Paciente;
import modelo.Medico;
import utilitario.TipoConsulta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaServico {
    private List<Consulta> listaConsultas = new ArrayList<>();
    private int proximoIdConsulta = 1;

    public ConsultaServico() {}

    public Consulta cadastrarConsulta(LocalDateTime dataHora, Medico medico, Paciente paciente, TipoConsulta tipo) {
        Consulta novaConsulta = new Consulta(dataHora, medico, paciente, tipo);
        novaConsulta.setId(proximoIdConsulta++);
        this.listaConsultas.add(novaConsulta);
        return novaConsulta;
    }

    public Boolean atualizarConsulta(int id, LocalDateTime dataHora, Medico medico, Paciente paciente, TipoConsulta tipo) {
        Consulta consulta = buscarConsultaPorId(id);
        if (consulta != null) {
            consulta.setDataHora(dataHora);
            consulta.setMedico(medico);
            consulta.setPaciente(paciente);
            consulta.setTipo(tipo);
            return true;
        }
        return false;
    }

    public boolean removerConsulta(int id) {
        Consulta consulta = buscarConsultaPorId(id);
        if (consulta != null) {
            this.listaConsultas.remove(consulta);
            return true;
        }
        return false;
    }

    public Consulta buscarConsultaPorId(int id) {
        for (Consulta consulta : listaConsultas) {
            if (consulta.getId() == id) {
                return consulta;
            }
        }
        return null;
    }

    public List<Consulta> getListaConsultas() {
        return listaConsultas;
    }
}
