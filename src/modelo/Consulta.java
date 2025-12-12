package modelo;

import utilitario.TipoConsulta;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Representa o agendamento de uma Consulta na clínica.
 * Esta classe é central para o sistema, pois armazena a relação entre
 * um {@link Paciente}, um {@link Medico} e a data/hora em que o atendimento ocorrerá.
 */
@Entity
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dataHora;

    @ManyToOne
    private Medico medico;

    @ManyToOne
    private Paciente paciente;

    @Enumerated(EnumType.STRING)
    private TipoConsulta tipo;

    public Consulta() {
    }

    /**
     * Construtor principal para criar uma nova instância de Consulta.
     *
     * @param dataHora Data e hora exatas do agendamento.
     * @param medico   O objeto Medico que realizará o atendimento.
     * @param paciente O objeto Paciente que será atendido.
     * @param tipo     O tipo da consulta (definido pelo enum TipoConsulta, ex: NORMAL ou RETORNO).
     */
    public Consulta(LocalDateTime dataHora, Medico medico, Paciente paciente, TipoConsulta tipo) {
        this.dataHora = dataHora;
        this.medico = medico;
        this.paciente = paciente;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public TipoConsulta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConsulta tipo) {
        this.tipo = tipo;
    }
}