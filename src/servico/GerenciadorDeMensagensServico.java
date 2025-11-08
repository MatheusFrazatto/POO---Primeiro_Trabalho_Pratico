package servico;

import modelo.Consulta;
import modelo.Contato;
import modelo.Paciente;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ListIterator;

public class GerenciadorDeMensagensServico {
    public GerenciadorDeMensagensServico() {}

    public void enviarEmails(List<Consulta> listaConsultasDiaSeguinte) {
        ListIterator<Consulta> it = listaConsultasDiaSeguinte.listIterator();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");

        while (it.hasNext()) {
            Consulta consulta = it.next();
            Paciente paciente = consulta.getPaciente();
            Contato contato = paciente.getContato();

            Boolean temEmail = contato.getEmail() != null && !contato.getEmail().isEmpty();
            if (!temEmail) {
                System.out.println("Falha ao enviar email para: " + paciente.getNome());
            } else {
                System.out.println(
                    "Enviando email para: " + contato.getEmail() +
                    " Mensagem: Consulta agendada para amanhã as " + consulta.getDataHora().format(formatador)
                );
            }

        }
    }

    public void enviarMensagensSMS(List<Consulta> listaConsultasDiaSeguinte) {
        ListIterator<Consulta> it = listaConsultasDiaSeguinte.listIterator();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");

        while (it.hasNext()) {
            Consulta consulta = it.next();
            Paciente paciente = consulta.getPaciente();
            Contato contato = paciente.getContato();

            Boolean temTelefone = contato.getTelefone() != null && !contato.getTelefone().isEmpty();
            if (!temTelefone) {
                System.out.println("Falha ao enviar SMS para: " + paciente.getNome());
            } else {
                System.out.println(
                    "Enviando SMS para: " + contato.getTelefone() +
                    " Mensagem: Consulta agendada para amanhã as " + consulta.getDataHora().format(formatador)
                );
            }

        }
    }
}
