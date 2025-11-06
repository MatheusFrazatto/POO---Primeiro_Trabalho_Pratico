/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import utilitario.TipoConvenio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fraza
 */
public class Paciente {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private Endereco endereco;
    private Contato contato;
    private TipoConvenio tipoConvenio;
    private DadosAdicionais dadosAdicionais;
    private List<Prontuario> prontuarios;
    private int proximoIdProntuario = 1;

    public Paciente(int id, String cpf, String nome, LocalDate dataNascimento, Endereco endereco, Contato contato, TipoConvenio tipoConvenio) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.contato = contato;
        this.tipoConvenio = tipoConvenio;
        this.dadosAdicionais = new DadosAdicionais();
        this.prontuarios = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public TipoConvenio getTipoConvenio() {
        return tipoConvenio;
    }

    public void setTipoConvenio(TipoConvenio tipoConvenio) {
        this.tipoConvenio = tipoConvenio;
    }

    public DadosAdicionais getDadosAdicionais() {
        return dadosAdicionais;
    }

    public void setDadosAdicionais(DadosAdicionais dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    public List<Prontuario> getHistoricoProntuarios() {
        return prontuarios;
    }

    public void adicionarProntuario(Prontuario prontuario) {
        prontuario.setId(proximoIdProntuario++);
        this.prontuarios.add(prontuario);
    }

    public Prontuario buscarProntuarioPorId(int idProntuario) {
        for (Prontuario p : this.prontuarios) {
            if (p.getId() == idProntuario) {
                return p;
            }
        }
        return null;
    }

    public boolean removerProntuarioPorId(int idProntuario) {
        Prontuario p = buscarProntuarioPorId(idProntuario);
        if (p != null) {
            return this.prontuarios.remove(p);
        }
        return false;
    }
}


