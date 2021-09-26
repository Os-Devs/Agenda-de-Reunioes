package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programa��o Orientada a Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

public class Reuniao {
    private int id;
    LocalDateTime datahora = LocalDateTime.now();
    private String assunto;
    private ArrayList<Participante> participantes = new ArrayList<>();

    public Reuniao(int id, String datahora, String assunto) {
        this.id = id;
        this.datahora = LocalDateTime.parse(datahora);
        this.assunto = assunto;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    public LocalDateTime getDatahora() {return this.datahora;}
    public void setDatahora(LocalDateTime datahora) {this.datahora = datahora;}
    public String getAssunto(){ return assunto;}
    public void setAssunto(String assunto){this.assunto = assunto;}

    public ArrayList<Participante> getParticipantes() {return participantes;}

}
