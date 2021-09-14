package repositorio;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programação Orientada a Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/
import java.util.ArrayList;
import java.util.TreeMap;

import modelo.Participante;
import modelo.Reuniao;


public class Repositorio {
	private TreeMap<String,Participante> participantes = new TreeMap<>();
	private ArrayList<Reuniao> reunioes = new ArrayList<>(); 

	
	public void adicionar(Participante p){
	}
	public void remover(Participante p){
	}
	public Participante localizarParticipante(String nome){
	}

	public void adicionar(Reuniao r){
	}
	public void remover(Reuniao r){
	}
	public Reuniao localizarReuniao(int id){
	}
	
	public ArrayList<Participante> getParticipantes() {
	}
	public ArrayList<Reuniao> getReunioes() {
	}

	
	public int getTotalParticipante(){
	}
	public int getTotalReunioes(){
	}
	
	//...demais metodos
	
}

