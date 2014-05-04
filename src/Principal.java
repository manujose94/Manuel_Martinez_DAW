import java.awt.EventQueue;
import java.sql.*;

public class Principal {
	
	private Connection  conexion= null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub.
		
						//Creamos el objeto equipo
						Equipo equipo1 = new Equipo("", 0, 0, 0, 0);
					
						
							Apuestas frame = new Apuestas ();
						
						
						//System.out.println(liga.getnombreLiga() +liga.getnumEquipos());
							frame.setVisible(true);
					
				
			
		}
	}

