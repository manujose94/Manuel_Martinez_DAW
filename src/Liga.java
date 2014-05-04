import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.sql.*;

public class Liga implements Serializable {
	private int numEquipos;
	private String nombreLiga;
	
//private Equipo equipos[];
	
	private ArrayList <Equipo> equipoLista;
	private int 	idLiga;
	Statement	instruccion = null ; //instruccion de consulta
	ResultSet conjuntoResultado = null ; // maneja resultados 

//------------------------------------------------------------------------------------
	//                      **DATABASE**
	
	private Connection  conexion= null;
	public Liga(Connection  conexion) {
		nombreLiga="Liga_Inglesa";
		numEquipos=0;
		this.conexion=conexion;
		leerLiga();
		// Inicializamos el arrayList
				equipoLista = new ArrayList <Equipo> ();
				// No debemos inicializar un  el arrayList
					//Ya iremos añadiendo conforme queramos a nuestra array lista 
				
		
	}
	private void leerLiga() {
		try{
		 instruccion = (Statement) conexion.createStatement();
		 // consulta la base de datos 
		 	
		// insercion en base de datos
		 conjuntoResultado = instruccion.executeQuery("SELECT idLiga,Nombre,numEquipos  FROM ligas LIMIT 1 ");
		 conjuntoResultado.next(); //dar sigguiente resultado
		 //Almacenar Liga 
		 this.nombreLiga=(String)conjuntoResultado.getObject("nombre");
		 this.numEquipos=(int)conjuntoResultado.getObject("numEquipos");
		 this.idLiga=(int)conjuntoResultado.getObject("idLiga");
		}catch( SQLException excepcionSql ){
			excepcionSql.printStackTrace();
			
			
		}
	}
	
	public void NuevoEquipoBBDD(Equipo equipo){
		try{
			 instruccion = (Statement) conexion.createStatement();
			 // consulta la base de datos 
			 	String insert_SQL ="INSERT INTO equipos(idLiga,nombreEquipo,golesFavor,golesEnContra,partidosGanados,partidosPerdidos)";
			 	insert_SQL=insert_SQL+"VALUES ("+idLiga+",'"+equipo.getNombre()+"',"+equipo.getGolesFavor()+","+""+equipo.getGolesContra()+","+equipo.getPartidosGanados()+","+""+equipo.getPartidosPerdidos()+")";
			 	System.out.println(insert_SQL);
			 // insercion en base de datos
			 	instruccion.executeUpdate(insert_SQL);
			 	
			 
			
			 
			
			}catch( SQLException excepcionSql ){
				excepcionSql.printStackTrace();
				
				
			}
	}
   
	//Metodo para Elminiar Equipo de BBDDD 
	public void DeleteEquipoBBDD(Equipo equipo){
		try{	 
			 //orden BBDDD eliminar 
				String delete_sql= "DELETE FROM 'equipos' WHERE 'idEquipo' = ?";
				//delete_sql=delete_sql+equipo.getNombre();
				System.out.println(delete_sql);
				instruccion.executeUpdate(delete_sql);//Ejeccion
	
			}catch( SQLException excepcionSql ){
				excepcionSql.printStackTrace();
				
				
			}
	}
   
	
//----------------------------------------------------------------------------------
	
public Liga ( Connection  conexion ,String Liga) { //indicamos en Numero maximos de equipos de una LIga
	nombreLiga = Liga ;
	numEquipos = 0;
	this.conexion=conexion;
	// Inicializamos el arrayList
	equipoLista = new ArrayList <Equipo> ();
	

		
	}

public void setNombreLiga(String nombre){
		
	nombreLiga = nombre;
		}

public   String getnombreLiga(){
	return nombreLiga;

}
public   int  getnumEquipos( ){
	return numEquipos;

}

public   Equipo  getEquipo( int numPosicion ){

	 return equipoLista.get(numPosicion);
	
	

}
//1.ComBox: Poder crear o elimiar equipo 
//Métodos para añadir y eliminar equipos.
public void NewEquipo() {
	equipoLista.add(new Equipo());
	numEquipos++;
	
}


public void removeEquipo(int position){
	equipoLista.remove(position);
	numEquipos=numEquipos-1;
	
}

public void setEquipo( Equipo equipoModificar ,  int posicion){
	
	equipoLista.set(posicion, equipoModificar);

		}


}
