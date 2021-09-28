package modelo;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programação Orientada a Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Reuniao {
    private int id;
    LocalDateTime datahora = LocalDateTime.now();
    private String assunto;
    private ArrayList<Participante> participantes = new ArrayList<>();
    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Reuniao(int id, String datahora, String assunto) {
        this.id = id;
        this.datahora = LocalDateTime.parse(datahora, formatador);
        this.assunto = assunto;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    public LocalDateTime getDatahora() {return this.datahora;}
    public void setDatahora(LocalDateTime datahora) {this.datahora = datahora;}
    public String getAssunto(){ return assunto;}
    public void setAssunto(String assunto){this.assunto = assunto;}


    public ArrayList<Participante> getParticipantes() {return participantes;}

    public void adicionar(Participante p){ participantes.add(p);}

    public void remover(Participante p) { participantes.remove(p); }

    public String toString() {
        String texto = "Data=" + getDatahora().format(formatador)+ ", Assunto=" + getAssunto();
        if (participantes != null)
            texto +=  getId();
        else
            texto += "nao tem";

        return texto ;
    }

}
