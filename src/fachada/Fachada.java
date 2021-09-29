package fachada;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programação Orientada a Objetos
 * Prof. Fausto Maranhão Ayres
 * Grupo: John Ewerton Marques , Diego Figueiredo
 **********************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import modelo.Participante;
import modelo.Reuniao;
import repositorio.Repositorio;



public class Fachada {
	private static Repositorio repositorio = new Repositorio();	//existe somente um repositorio

	private static int idReuniao=0;

	public static ArrayList<Participante> listarParticipantes() { return repositorio.getParticipantes(); }
	public static ArrayList<Reuniao> listarReunioes() {
		return repositorio.getReunioes();
	}

	public static Participante criarParticipante(String nome, String email) throws Exception {
		nome = nome.trim();
		email = email.trim();
		Participante p = repositorio.localizarParticipante(nome);
		if (p!=null)
			throw new Exception("Criar participante - Participante cadastrado:" + nome);
		p = new Participante(nome, email);
		repositorio.adicionar(p);
		return p;
	}

	public static Reuniao criarReuniao (String datahora, String assunto, ArrayList<String> nomes) throws Exception {
		assunto = assunto.trim();
		if (nomes.size() < 2) {
			throw new Exception("Participante insuficientes para a criação da reunião.");
		}
		else {
			idReuniao++;
			Reuniao r = new Reuniao(idReuniao,datahora, assunto);
			for (String nome: nomes) {
				Participante p = repositorio.localizarParticipante(nome);
				if(p==null){
					throw new Exception("Participante inesxistente");
				}
				else {
					if (p.getReunioes().size() == 0) {
						p.adicionar(r);
						r.adicionar(p);
					}
					else {
						for (Reuniao reuniao: p.getReunioes()) {
							Duration dur = Duration.between(reuniao.getDatahora(), r.getDatahora());
							long horas = dur.toHours();
							if (Math.abs(horas) > 2) {
								p.adicionar(r);
								r.adicionar(p);
								break;
							}
							else {
								throw new Exception("O Participante "+ p.getNome() + " Já possui uma renião nesse horário.");
							}

						}
					}
				}
				}
			if (r.getParticipantes().size() < 2) {
				throw new Exception("Participantes insuficientes para a criação da Reunião.");
			}
			else {
				repositorio.adicionar(r);

			}
			for (Participante participante : r.getParticipantes()) {
				enviarEmail(participante.getEmail(), "Não salvo", "Boas vindas!");
			}
			return r;
		}

	}


	public static void 	adicionarParticipanteReuniao(String nome, int id) throws Exception {
		nome = nome.trim();
		//enviarEmail(emaildestino, assunto, mensagem)
		Participante p = repositorio.localizarParticipante(nome);
		if(p==null)
			throw new Exception("nao pode adicionar - participante inexistente");

		Reuniao r = repositorio.localizarReuniao(id);
		if(r==null)
			throw new Exception("nao pode adicionar - reuniao inexistente");
		for (Reuniao reuniao : p.getReunioes()) {
		    if (reuniao.getId() == r.getId()) {
				throw new Exception("o participante ja foi adicionado a reunião");
			}
		}
		r.adicionar(p);
		p.adicionar(r);
		enviarEmail(p.getEmail(), "Não Salvo", "Mensagem de boas vindas!");
		//...
	}
	
	public static void 	removerParticipanteReuniao(String nome, int id) throws Exception {
		nome = nome.trim();
		//localizar participante e reuniao no repositorio e remove-lo da reunião
		//enviarEmail(emaildestino, assunto, mensagem)
		Participante p = repositorio.localizarParticipante(nome);
		if(p==null)
			throw new Exception("nao pode remover - participante inexistente");

		Reuniao r = repositorio.localizarReuniao(id);
		if(r==null) {
			throw new Exception("nao pode remover - reuniao inexistente");
		}
		r.remover(p);
		p.remover(r);
		enviarEmail(p.getEmail(), "Não Salvo", "Mensagem de boas vindas!");
		if (r.getParticipantes().size() < 2) {
			cancelarReuniao(r.getId());
		}

	}
	public static void	cancelarReuniao(int id) throws Exception {
		//localizar a reunião no repositorio, remove-la de seus participantes e
		//remove-la do repositorio
		//enviarEmail(emaildestino, assunto, mensagem)
		Reuniao r = repositorio.localizarReuniao(id);
		if (r==null)
			throw new Exception(String.format("cancelar reunião - reunião inexistente %d", id));

		for (Participante p : r.getParticipantes()) {
			enviarEmail(p.getEmail(), "Não Salvo", "Mensagem de boas vindas!");
			p.remover(r);
		}
		repositorio.remover(r);

	}

	public static void inicializar() throws Exception {
		//ler dos arquivos textos (formato anexo) os dados dos participantes e 
		//das reuniões e adiciona-los ao repositório

		Scanner arquivo1=null;
		Scanner arquivo2=null;
		try{
			arquivo1 = new Scanner( new File("participantes2.txt"));
		}catch(FileNotFoundException e){
			throw new Exception("arquivo de participantes inexistente:");
		}
		try{
			arquivo2 = new Scanner( new File("reunioes2.txt"));
		}catch(FileNotFoundException e){
			throw new Exception("arquivo de reunioes inexistente:");
		}

		String linha;	
		String[] partes;	
		String nome, email;
		while(arquivo1.hasNextLine()) {
			linha = arquivo1.nextLine().trim();		
			partes = linha.split(";");	
			nome = partes[0];
			email = partes[1];
			Participante p = new Participante(nome,email);
			repositorio.adicionar(p);
		} 
		arquivo1.close();			

		String id, datahora, assunto;
		String[] nomes;
		while(arquivo2.hasNextLine()) {
			linha = arquivo2.nextLine().trim();		
			partes = linha.split(";");	
			id = partes[0];
			datahora = partes[1];
			assunto = partes[2];
			nomes = partes[3].split(",");		
			Reuniao r = new Reuniao(Integer.parseInt(id), datahora, assunto);
			for(String n : nomes){
				Participante p = repositorio.localizarParticipante(n);
				r.adicionar(p);
				p.adicionar(r);
				enviarEmail(p.getEmail(), "Não Salvo", "Mensagem de boas vindas!");
			}
			repositorio.adicionar(r);
			idReuniao = Integer.parseInt(id);
		} 
		arquivo2.close();	
	}

	public static void	finalizar() throws Exception{
		//gravar nos arquivos textos  os dados dos participantes e 
		//das reuniões que estão no repositorio
		
		FileWriter arquivo1 =null;
		FileWriter arquivo2 =null;
		try{
			arquivo1 = new FileWriter( new File("participantes.csv") ); 
		}catch(IOException e){
			throw new Exception("problema na criação do arquivo de participantes");
		}
		try{
			arquivo2 = new FileWriter( new File("reunioes.csv") );
		}catch(IOException e){
			throw new Exception("problema na criação do arquivo de reunioes");
		}

		for(Participante p : repositorio.getParticipantes()) {
			arquivo1.write(p.getNome() +";" + p.getEmail() +"\n");	
		} 
		arquivo1.close();			

		ArrayList<String> lista;
		String nomes;
		for(Reuniao r : repositorio.getReunioes()) {
			lista = new ArrayList<>();
			for(Participante p : r.getParticipantes()) {
				lista.add(p.getNome());
			}
			nomes = String.join(",", lista);
			arquivo2.write(r.getId()+";"+r.getDatahora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))+";"+r.getAssunto()+";"+nomes+"\n");
		} 
		arquivo2.close();	

	}
	
	
	
	/**************************************************************
	 * 
	 * MÉTODO PARA ENVIAR EMAIL, USANDO UMA CONTA (SMTP) DO GMAIL
	 * ELE ABRE UMA JANELA PARA PEDIR A SENHA DO EMAIL DO EMITENTE
	 * ELE USA A BIBLIOTECA JAVAMAIL 1.6.2
	 * Lembrar de: 
	 * 1. desligar antivirus e de 
	 * 2. ativar opcao "Acesso a App menos seguro" na conta do gmail
	 * 
	 **************************************************************/
	public static void enviarEmail(String emaildestino, String assunto, String mensagem) {
		try {
			//configurar emails
			String emailorigem = "roojohndi@gmail.com";
			String senhaorigem = pegarSenha();

			//Gmail
			Properties props = new Properties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");

			Session session;
			session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(emailorigem, senhaorigem);
						}
					});

			MimeMessage message = new MimeMessage(session);
			message.setSubject(assunto);
			message.setFrom(new InternetAddress(emailorigem));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emaildestino));
			message.setText(mensagem); // usar "\n" para quebrar linhas
			Transport.send(message);

			//System.out.println("enviado com sucesso");

		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * JANELA PARA DIGITAR A SENHA DO EMAIL
	 */

	public static String pegarSenha() {
		JPasswordField field = new JPasswordField(10);
		field.setEchoChar('*');
		JPanel painel = new JPanel();
		painel.add(new JLabel("Entre com a senha do seu email:"));
		painel.add(field);
		JOptionPane.showMessageDialog(null, painel, "Senha", JOptionPane.PLAIN_MESSAGE);
		String texto = new String(field.getPassword());
		return texto.trim();
	}
}
