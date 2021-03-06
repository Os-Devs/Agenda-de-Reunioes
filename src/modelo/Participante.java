package modelo;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programação Orientada a Objetos
 * Prof. Fausto Maranhão Ayres
 * Grupo: John Ewerton Marques , Diego Figueiredo
 **********************************/

import java.util.ArrayList;

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

    public ArrayList<Reuniao> getReunioes() { return reunioes; }

    public void adicionar(Reuniao r){ reunioes.add(r);}

    public void remover(Reuniao r) { reunioes.remove(r);}

    public String toString() {
        String texto = "Nome = " + getNome()+ ", Email = " + getEmail() + ", Reuniões = ";
        if (!reunioes.isEmpty()){
            for (Reuniao r: reunioes) {
                texto +=  r.getId() + " ";
            }
        }
        else
            texto += "nao tem";

        return texto ;
    }
}
