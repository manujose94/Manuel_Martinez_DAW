import java.awt.BorderLayout;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.sql.*;
public class Apuestas extends JFrame {
	private VentajaLiga frameLiga;
	private JPanel contentPane;
	private JTextField textField_NombreLiga;
 
	//BD
	Connection conexion= null; //manejar conexion
	Statement	instruccion = null ; //instruccion de consulta
	ResultSet conjuntoResultado = null ; // maneja resultados 
	
	


	
	/*** Launch the application.*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Apuestas frame = new Apuestas();
					frame.setVisible(true);
				
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Apuestas() {
		
		try{
			Class.forName("com.mysql.jdbc.Driver");//importar de forma dinamica "miesntras se ejecuta "	
			//Lograr la conexion con la base de datos
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/apuestas","root","");
		}catch( SQLException excepcionSql ){
			excepcionSql.printStackTrace();
			
			
		}
		catch( ClassNotFoundException noEncontroClase )
		{
			noEncontroClase.printStackTrace();
		}
	

		
	
Liga	liga= new Liga(conexion);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAdministracionLigas = new JLabel("Administracion Ligas ");
		lblAdministracionLigas.setFont(new Font("Gadugi", Font.BOLD, 15));
		lblAdministracionLigas.setForeground(Color.RED);
		lblAdministracionLigas.setBounds(130, 11, 165, 14);
		contentPane.add(lblAdministracionLigas);
		
		JLabel lblNombreLiga = new JLabel("Nombre Liga");
		lblNombreLiga.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombreLiga.setBackground(Color.BLACK);
		lblNombreLiga.setBounds(162, 47, 85, 14);
		contentPane.add(lblNombreLiga);
		
		textField_NombreLiga = new JTextField();
		textField_NombreLiga.setBounds(89, 72, 229, 20);
		contentPane.add(textField_NombreLiga);
		textField_NombreLiga.setColumns(10);
		textField_NombreLiga.setText(String.valueOf(liga.getnombreLiga()));
		
		JButton btnAdministrar = new JButton("Administrar");
		btnAdministrar.setBounds(158, 103, 89, 36);
		contentPane.add(btnAdministrar);
		btnAdministrar.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent adm) {
				
				//Equipo equipo1 = new Equipo("", 0, 0, 0, 0);
				Liga liga= new Liga(conexion);
				VentajaLiga frameLiga = new VentajaLiga(liga);
				frameLiga.setVisible(true);
				frameLiga.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			});
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(50, 150, 354, 14);
		contentPane.add(separator);
		
		JButton btnGenerarApuestas = new JButton("Generar Apuestas");
		btnGenerarApuestas.setBounds(158, 175, 119, 23);
		contentPane.add(btnGenerarApuestas);
		
		JButton btnSeguimientoApuestas = new JButton("Seguimiento Apuestas");
		btnSeguimientoApuestas.setBounds(147, 211, 148, 23);
		contentPane.add(btnSeguimientoApuestas);
		
		
	}

}
