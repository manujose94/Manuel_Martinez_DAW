
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class EquipoVentana extends JFrame {

	private JPanel contentPane;
	private  JTextField textField_Nombre;
	private JTextField textField_NumPatidosGan;
	private JTextField textField_PartidosPerdidos;
	private JTextField textField_Contra;
	private JTextField textField_Favor;
	private Equipo equipo;
	private Liga liga;
	private String nombre ;
	
	private int Partidos_Ganados;
	private int Partidos_Peridos;
	private int Goles_Contra ;
	private int Goles_Favor ;
	private JLabel lblGolescontra;
	private JLabel lblGolesfavor;
	private JLabel lblPartidosPerdios;
	private JLabel lblPartidosGanados;
	private boolean modifica;
	private JComboBox ComboBox;
	

//
public EquipoVentana(Equipo equipoModificar ,JComboBox ComboBox, boolean modifica, Liga liga) {
	
				equipo = equipoModificar;
				this.liga=liga;
				this.ComboBox=ComboBox;
				this.modifica=modifica;
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombreTeam = new JLabel("Nombre TEAM");
		lblNombreTeam.setBounds(38, 24, 86, 14);
		contentPane.add(lblNombreTeam);
		
		textField_Nombre = new JTextField();
		textField_Nombre.setBounds(27, 49, 86, 20);
		contentPane.add(textField_Nombre);
		textField_Nombre.setColumns(10);
		
		JButton btnGuardarDisco = new JButton("Guardar en Disco");
		btnGuardarDisco.setBounds(312, 197, 120, 30);
		contentPane.add(btnGuardarDisco);
		
		JButton btnLeerDeDisco = new JButton("Leer de Disco");
		btnLeerDeDisco.setBounds(60, 197, 140, 30);
		contentPane.add(btnLeerDeDisco);
		
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(212, 197, 89, 23);
		contentPane.add(btnGuardar);
		
		JButton btnLeer = new JButton("Leer");
		btnLeer.setBounds(21, 100, 89, 23);
		contentPane.add(btnLeer);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(73, 137, 10, 10);
		contentPane.add(panel);
		
		textField_NumPatidosGan = new JTextField();
		textField_NumPatidosGan.setBounds(312, 108, 89, 20);
		contentPane.add(textField_NumPatidosGan);
		textField_NumPatidosGan.setColumns(10);
		
		textField_PartidosPerdidos = new JTextField();
		textField_PartidosPerdidos.setBounds(169, 108, 86, 20);
		contentPane.add(textField_PartidosPerdidos);
		textField_PartidosPerdidos.setColumns(10);
		
		textField_Contra = new JTextField();
		textField_Contra.setBounds(169, 49, 86, 20);
		contentPane.add(textField_Contra);
		textField_Contra.setColumns(10);
		
		textField_Favor = new JTextField();
		textField_Favor.setBounds(312, 49, 86, 20);
		contentPane.add(textField_Favor);
		textField_Favor.setColumns(10);
		
		lblGolescontra = new JLabel("GolesContra");
		lblGolescontra.setBounds(177, 24, 78, 14);
		contentPane.add(lblGolescontra);
		
		lblGolesfavor = new JLabel("GolesFavor");
		lblGolesfavor.setBounds(309, 24, 70, 14);
		contentPane.add(lblGolesfavor);
		
		lblPartidosPerdios = new JLabel("Partidos Perdios");
		lblPartidosPerdios.setBounds(169, 80, 86, 14);
		contentPane.add(lblPartidosPerdios);
	
		lblPartidosGanados = new JLabel("Partidos Ganados ");
		lblPartidosGanados.setBounds(315, 83, 86, 14);
		contentPane.add(lblPartidosGanados);
		
		leerEquipo() ;
		
		btnGuardar.addActionListener(new ActionListener() { // Ponemos nuestro Boton y la accion
			 public void actionPerformed(ActionEvent guardar) {
				 guardarEquipo();
			 		}
				});
		 
		btnGuardarDisco.addActionListener(new ActionListener() { // Ponemos nuestro Boton y la accion
			 public void actionPerformed(ActionEvent Ecribirarchivo) {
				 guardarEnFichero() ;
				
				
			 		}
				});
		btnLeer.addActionListener(new ActionListener() { // Ponemos nuestro Boton y la accion
			 public void actionPerformed(ActionEvent Leer) {
				leerEquipo() ;
				
				
			 		}
				});
		
		
		btnLeerDeDisco.addActionListener(new ActionListener() { // Ponemos nuestro Boton y la accion
			 public void actionPerformed(ActionEvent LeerDisco) {
				 leerArchvivo();
			 		}
				});
		 }


private void leerEquipo(){
	textField_Nombre.setText(equipo.getNombre());
	textField_Favor.setText(String.valueOf(equipo.getGolesFavor()));
	textField_Contra.setText(String.valueOf(equipo.getGolesContra()));
	textField_NumPatidosGan.setText(String.valueOf(equipo.getPartidosGanados()));
	textField_PartidosPerdidos.setText(String.valueOf(equipo.getPartidosPerdidos()));
	
}

private void guardarEquipo(){
	equipo.setNombre(textField_Nombre.getText());
	equipo.setGolesFavor(Integer.valueOf(textField_Favor.getText()));
	equipo.setGolesContra(Integer.valueOf(textField_Contra.getText()));
	equipo.setPartidosGanados(Integer.valueOf(textField_NumPatidosGan.getText()));
	equipo.setPartidosPerdidos(Integer.valueOf(textField_PartidosPerdidos.getText()));

	//3.ComboBox:Guardar Equipo .
		//Si Equipo no esta modificado 
	if(!modifica){ 
		
		//Añadir nuevo item(equipo) a ComboBox
		ComboBox.addItem(equipo);
		this.liga.NuevoEquipoBBDD(equipo);
		//Esta modificado
	}else{ 
		//Seleccionamos el equipo_eleguido 
		
		Equipo equipoElegido=(Equipo)ComboBox.getSelectedItem();
		//Le cambiamos el nombre 
		equipoElegido.setNombre(equipo.getNombre());
		
	}
	}


private  void guardarEnFichero(){
	ObjectOutputStream salida;
	try{
	nombre=textField_Nombre.getText();
	Partidos_Ganados = Integer.parseInt( textField_NumPatidosGan.getText() );
	Partidos_Peridos = Integer.parseInt( textField_PartidosPerdidos.getText() );
	Goles_Contra = Integer.parseInt( textField_Contra.getText() );
	Goles_Favor = Integer.parseInt(  textField_Favor.getText() );
	Equipo equipos = new Equipo(nombre,Goles_Favor,Goles_Contra,Partidos_Ganados,Partidos_Peridos);
	try {
		 salida =new ObjectOutputStream(new FileOutputStream ("ArchivoHalo.txt"));
			salida.writeObject( equipos );
			if( salida != null)
				salida.close();
	}
	catch ( IOException ioExeception )
	{
		System.err.println("Error al abrir archivo");
	
	}
	}catch(NumberFormatException numberFormatException ) {
	JOptionPane.showMessageDialog(null, "Se Deben introducir números");
		
	}
	
}

private void leerArchvivo(){
	// equipos = null; //No vamos a instaciar equipos  . 
	 try {
		 //Vamos a realizaor un estrem en el qual se reciban DATOs
			FileInputStream fis = new FileInputStream("ArchivoHalo.txt");//Indicamos donde debe de leer
			ObjectInputStream Obj = new ObjectInputStream(fis); //Indicamos que se trata de Objete ( siempre serializable )
			equipo = (Equipo)Obj.readObject(); //Vamos   INDICAR que nos lo muestre como LISTA 
			//Para salvar errores
				if( equipo != null){
				System.out.println(equipo.getNombre()+" "+"Goles Favor:" +equipo.getGolesFavor()+" Goles Contra:"+equipo.getGolesContra()+ " Partidos Ganados:"+equipo.getPartidosGanados()+ " PartidosPerdidos:"+equipo.getPartidosPerdidos());
				System.out.println(liga.getnombreLiga()+" "+"Num_Equipos:" +liga.getnumEquipos());
				}
					
				//Cerramos
				 fis.close();
				 Obj.close();
		}catch ( Exception ex ){
			 
		}	 
	
 }

}
