
import java.io.FileOutputStream;
import java.io.Serializable;
import java.sql.*;



public class Equipo implements Serializable { // intterfaz SERIALIZABLE permitiendo conventir un objecto en una cadena de bytes 
													//con estos se lograr poder almacenar los valor de atributos que se almacenan en un "arhivo"

	
	private String nombreEquipo;
	private int golesFavor;
	private int  golesContra;
	private int partidosGanados;
	private int  partidosPerdidos;
	private int 	idEquipo;
	
	
	
		public Equipo(String nombreEquipo, int golesFavor , int golesContra , int partidosGanados , int partidosPerdidos) {	
			//Solo indicamos que  valor X es Valor y fe esta clase (this.)
			this.nombreEquipo = nombreEquipo;
			this.golesFavor = golesFavor; 
			this.golesContra = golesContra;
			this.partidosGanados = partidosGanados; 
			this.partidosPerdidos = partidosPerdidos;
		
		
		}
	
		public Equipo() {
			this("", 0, 0, 0, 0);
			
		}
		
		
		
		public void setNombre(String nombreEquipo){
			this.nombreEquipo=nombreEquipo; 
			//almacenar el atributo nombreEquipo  
			//para no confundire con la variable que entra ponemos this.
		
		
		}
	
		public  String getNombre(){
		return nombreEquipo;
		
		
		}
	
		public void setGolesFavor(int golesFavor){
		this.golesFavor=golesFavor;
		}

		public   int getGolesFavor(){
			return golesFavor;
		
		}

		public void setGolesContra(int golesContra ){
			this.golesContra=golesContra;
		}

		public   int getGolesContra(){
			return golesContra;
		
		}
	
		public void setPartidosGanados(int partidosGanados){
			this.partidosGanados=partidosGanados;
		}

		public   int getPartidosGanados(){
			return partidosGanados;
		
		}
	
		public void setPartidosPerdidos(int partidosPerdidos){
			this.partidosPerdidos=partidosPerdidos;
		}
		
		public   int getPartidosPerdidos(){
		return partidosPerdidos;
		
		}

		
		public String toString(){
			return nombreEquipo;
		}


}
