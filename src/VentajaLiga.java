import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class VentajaLiga extends JFrame {
	private Liga liga;
	private JPanel contentPane;
	private Equipo equipo;
	private JTextField textField_NomLiga;
	private JTextField textField_Posicion_EquipoModif;
	private JTextField textField_numEquiq;
	private EquipoVentana frameEquipo;
	private JComboBox <Equipo> ComboBox;

//2 COMBOX : Creamos metodo EquipoWindow para lanzar ventana. 
		//Estara : el Equipo y si/no esta modificado (Equipo equipo,boolean modifica)
	private void EquipoWindow(Equipo equipo,boolean modifica){
											//Indicamos que esta en el ComboBox
		frameEquipo = new EquipoVentana(equipo,this.ComboBox,modifica,liga);
		frameEquipo.setVisible(true);
		frameEquipo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public VentajaLiga(Liga Modificar){
		liga = Modificar;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombreLiga = new JLabel("Nombre Liga");
		lblNombreLiga.setBounds(41, 34, 74, 21);
		contentPane.add(lblNombreLiga);

		JLabel lblLigaAModificar = new JLabel("TEAM NUM");
		lblLigaAModificar.setBounds(41, 104, 83, 38);
		contentPane.add(lblLigaAModificar);

		textField_NomLiga = new JTextField();
		textField_NomLiga.setBounds(29, 60, 86, 20);
		contentPane.add(textField_NomLiga);
		textField_NomLiga.setColumns(10);
		
		textField_NomLiga.setText(String.valueOf(liga.getnombreLiga()));
		
		textField_Posicion_EquipoModif = new JTextField();
		textField_Posicion_EquipoModif.setText("");
		textField_Posicion_EquipoModif.setBounds(38, 153, 86, 20);
		contentPane.add( textField_Posicion_EquipoModif);
		textField_Posicion_EquipoModif.setColumns(10);
		

		JLabel lblNumeroEquipos = new JLabel("Numero Equipos");
		lblNumeroEquipos.setBounds(227, 41, 100, 14);
		contentPane.add(lblNumeroEquipos);

		textField_numEquiq = new JTextField();
		textField_numEquiq.setEnabled(false);
		textField_numEquiq.setBounds(227, 60, 86, 20);
		// Añadir numero de Equipos
		textField_numEquiq.setText(String.valueOf(liga.getnumEquipos()));
		contentPane.add(textField_numEquiq);
		textField_numEquiq.setColumns(10);
		
		// 1 ComboBox: Añadimos ComboBox de Equipo
		ComboBox = new JComboBox<Equipo>();
		ComboBox.setBounds(50, 195, 314, 20);
		contentPane.add(ComboBox);
		
		JButton btnModificar = new JButton("MODIFICAR");
		btnModificar.setBounds(134, 231, 125, 23);
		contentPane.add(btnModificar);
		
		btnModificar.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent modificar) {
					EquipoWindow(liga.getEquipo(liga.getnumEquipos()-1),true);
				}
			});
		//2.ComBox:  Añadir la accion para poder Crear o Eliminar
		JButton btnSumar = new JButton("+");
		btnSumar.setBounds(259, 231, 103, 23);
		contentPane.add(btnSumar);
	
		//2a.ComBox:Crear Equipo
		btnSumar.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent sumar) {
				//Cramos un nevo equipo con el metodo creado en Liga 
				liga.NewEquipo();
					//Lanzamos una venta 
								//Indicamos el Equipo 
											 //No es una modificacion -> boolean modifica 
					EquipoWindow(liga.getEquipo(liga.getnumEquipos()-1),false);		
					System.out.println(liga.getnumEquipos());
				}
			});

		
		JButton btnRestar = new JButton("-");
		btnRestar.setBounds(31, 231, 103, 23);
		contentPane.add(btnRestar);
		
		btnRestar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Llamamos al eliminar equipo

				
				liga.removeEquipo(ComboBox.getSelectedIndex());
			
				
				ComboBox.removeItemAt(ComboBox.getSelectedIndex());	
				
			}
		});
		
	
		

	}
	
	
	
}
