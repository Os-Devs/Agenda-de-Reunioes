/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

package aplicacaoSwing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import fachada.Fachada;
import modelo.Participante;
import modelo.Reuniao;

public class TelaParticipante {
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JLabel label_3;
	private JLabel label_1;
	private JLabel label_2;
	private JTextField textField;
	private JTextField textField_1;
	private JButton button;
	private JButton button_1;



	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					TelaReuniao window = new TelaReuniao();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public TelaParticipante() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Participante");
		frame.setBounds(100, 100, 586, 368);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 11, 431, 207);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {"nome", "email", "reuniao"}
				));
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(21, 302, 539, 14);
		frame.getContentPane().add(label);

		label_3 = new JLabel("selecione");
		label_3.setBounds(21, 216, 431, 14);
		frame.getContentPane().add(label_3);

		label_1 = new JLabel("nome:");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(21, 252, 71, 14);
		frame.getContentPane().add(label_1);

		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBackground(Color.WHITE);
		textField.setBounds(76, 249, 134, 20);
		frame.getContentPane().add(textField);

		label_2 = new JLabel("email:");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(21, 277, 71, 14);
		frame.getContentPane().add(label_2);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(76, 271, 195, 20);
		frame.getContentPane().add(textField_1);

		button_1 = new JButton("Criar");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField.getText().isEmpty() || textField_1.getText().isEmpty()) {
						label.setText("campo vazio");
						return;
					}
					String nome = textField.getText();
					String email = textField_1.getText();
					Fachada.criarParticipante(nome, email);
					label.setText("participante criado");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_1.setBounds(302, 252, 86, 23);
		frame.getContentPane().add(button_1);
		
		button = new JButton("Listar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button.setBounds(462, 68, 89, 23);
		frame.getContentPane().add(button);

		frame.setVisible(true);

	}

	public void listagem() {
		try{
			ArrayList<Participante> lista = Fachada.listarParticipantes();

			//objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();

			//criar as colunas (0,1,2) da tabela
			model.addColumn("nome");
			model.addColumn("email");
			model.addColumn("reuniao");

			//criar as linhas da tabela
			String texto;
			for(Participante p : lista) {
				for(Reuniao r : p.getReunioes()) {
					texto = r.getDatahora() +" - "+r.getAssunto() ;
					model.addRow(new Object[]{p.getNome(), p.getEmail(), texto});
				}
			}
			table.setModel(model);
			label_3.setText("resultados: "+lista.size()+ " participantes   - selecione uma linha");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
}
