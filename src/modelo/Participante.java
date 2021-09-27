package modelo;

import java.util.ArrayList;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programa��o Orientada a Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

public class Participante {
    private String nome;
    private String email;
    private ArrayList<Reuniao> reunioes = new ArrayList<>();

    public Participante(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() { return nome;}
    public void setNome(String nome) { this.nome = nome;}
    public String getEmail() { return email;}
    public void setEmail(String email) { this.email = email;}

    public ArrayList<Reuniao> getReunioes() {
        return reunioes;
    }

    public void adicionar(Reuniao r){ reunioes.add(r);}

    public void remover(Reuniao r) { reunioes.remove(r);}
}
