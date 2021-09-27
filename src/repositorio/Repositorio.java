package repositorio;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programa��o Orientada a Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/
import java.util.ArrayList;
import java.util.TreeMap;

import modelo.Participante;
import modelo.Reuniao;


public class Repositorio {
	private TreeMap<String,Participante> participantes = new TreeMap<>();
	private ArrayList<Reuniao> reunioes = new ArrayList<>(); 

	
	public void adicionar(Participante p){
		participantes.put(p.getNome(), p);
	}
	public void remover(Participante p){
		participantes.remove(p.getNome());
	}
	public Participante localizarParticipante(String nome){
		return participantes.get(nome);
	}

	public void adicionar(Reuniao r){ reunioes.add(r);}
	public void remover(Reuniao r){ reunioes.remove(r);}
	public Reuniao localizarReuniao(int id){
		for(Reuniao reuni : reunioes){
			if(reuni.getId() == id){
				return reuni;
			}
		}
		return null;
	}
	
	public ArrayList<Participante> getParticipantes() { return new ArrayList<Participante>(participantes.values());}
	public ArrayList<Reuniao> getReunioes() { return reunioes; }
	public int getTotalParticipante(){ return participantes.size();}
	public int getTotalReunioes(){ return reunioes.size();}


}

