package ioc.dam.m6.exemples.gestiofitxers;


import ioc.dam.m6.exemples.fluxos.Utilitats;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import UtilitatCompararDirectoris.ResultatComparacio;
import UtilitatCompararDirectoris.DiffFolder;
import enumerados.OpcionesNumerados;

public class MainPlus {

	private static final String String = null;
	
	private static File folder1,folder2;
	
	public static void main(String[] args) throws GestioFitxersException {
	    Scanner teclado = new Scanner(System.in);
		String fichero = null;
		GestioFitxersPlusImpl GestioPlus = new GestioFitxersPlusImpl();
		Utilitats utilitats = new Utilitats();
		String exit = "no";
		String adrecaTreball="C:\\Users\\ManuelJose\\Documents\\Unidad01\\";
		String adreca=null;;String desti=null;
		String nomFile=null;String nomFile2=null;
		
		String Contenido=null;
		int opcion;
		int opcion2;
		
		String ruta=File.listRoots()[0].toString();
		
		//Implementa donar com a CarpetadeTreball adreca
				//Ademes de mostrar el seu contingut getAdreca
		GestioPlus.adrecaDetalls(adrecaTreball);
		 
		DiffFolder difffolder = new DiffFolder();
		//Para caundo obtenagamos el resultado de la comparativa de DIFFolder;
		Iterator<ResultatComparacio> result=null;
		
		
		
		
	
		
		do{
			opcion=menu();
			  switch(opcion){
			  
			  case 1:

					System.out.println("   Selecciona la carpeta que quieres ir: "+adrecaTreball);
					adreca=adrecaTreball+teclado.next();
					GestioPlus.mostrarContingut(adreca);
					System.out.println("   Selecciona un fichero que desea mover  : ");
					nomFile=teclado.next();
					
					fichero=adreca+"\\"+nomFile;
					File FicheroMov = new File(fichero);
					System.out.println("El Fichero que vamos a mover :  "+FicheroMov.getAbsolutePath()+"--->"+FicheroMov.getParentFile());
					System.out.println("Por Ultimo : "+"\n");
					System.out.println(" Seleccione la Carpeta Destino:  ");
					
					adreca=adrecaTreball+teclado.next();
					GestioPlus.mostrarContingut(adreca);
					GestioPlus.setAdrecaCarpeta(adreca);
							
							
					File CarpetaDesti = new File(adreca+"\\"+nomFile);	
							//System.out.println(CarpetaDesti);
					GestioPlus.moure(FicheroMov, CarpetaDesti);
					//File f = new File(adreca, fichero );
					
			  case 2:
				  
				  System.out.println("   Selecciona la carpeta que quieres ir: "+adrecaTreball);
					adreca=/**adrecaTreball+**/teclado.next();
					GestioPlus.entraA(adreca);
					System.out.println(adrecaTreball);
					GestioPlus.mostrarContingut(adrecaTreball+adreca);
						System.out.println("Seleccione el FICHERO que desea copiar :");
							nomFile=adrecaTreball+adreca+"\\"+teclado.next();
							
							File FicheroOri = new File(nomFile);
					
							desti="C:\\Users\\ManuelJose\\Documents\\Unidad01\\carpeta1\\";
						
							File Filedesti = new File(desti);
						
						GestioPlus.copiar(FicheroOri, Filedesti);
				  break;
				  
			  	case 3:
				  
			  		String origen="C:\\Users\\ManuelJose\\Documents\\Unidad01\\carpeta1\\halo";
					desti="C:\\Users\\ManuelJose\\Documents\\Unidad01\\carpeta1\\halo3";
					File Desti= new File(desti);
					File Origen= new File(origen);
					try {
						utilitats.recodificaFitxer(Origen, Desti, "UTF-8");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  break;
			  	case 5:
			  		System.out.println("Seleccione Fichero para Metodo  EscribirFichero de GestioImpPlus :");
					nomFile="C:\\Users\\ManuelJose\\Documents\\Unidad01\\carpeta2\\"+teclado.next();
					
					fichero = "C:\\Users\\ManuelJose\\Documents\\Unidad01\\carpeta2\\forza.txt";
					 FileWriter writer = null;
					 
						try {
							/** Escritura temporal mediante Writes y Reader **/
							
							writer = new FileWriter(nomFile);
							System.out.println("Escribe Contenido Deseado : ");
							Contenido=teclado.next();
							
							GestioPlus.EscribirFichero(writer, Contenido );
							FileReader	reader = new FileReader(nomFile);
							System.out.println("Leido contenido Añadido :");GestioPlus.LeerFichero(reader);
							reader.close();
							/** Escritura en el Propi fitxero FORZA.TXT**/
							System.out.println("Vamos a escribir dicho contenio en el fichero :"+fichero);
							FileOutputStream out= new  FileOutputStream(fichero);
							OutputStreamWriter OutputStreamWriter = new OutputStreamWriter(out);
							
							
							String Contenido2= "MUUUUUUSSTANG";
							///Hay que realizar que añada continido , al contenido ya existente .
								GestioPlus.EcribirTtx(OutputStreamWriter, Contenido , Contenido2);
								//Nueva apertura para lectura ya que con los metodo .LeerFichero filiza cerrandolo hay que volverlo abrir
								FileReader	Rereader = new FileReader(fichero);
								GestioPlus.LeerFichero(Rereader);
								System.out.println("\n");
								Rereader.close();
								
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} break;
			  	case 6:
			  		System.out.println("Estando en la CarpetaTrabajo--> "+adrecaTreball);
			  		System.out.println("Primera Carpeta:");
			  		nomFile=adrecaTreball+"\\"+teclado.next();
			  		System.out.println("Segona Carpeta:");
			  		nomFile2=adrecaTreball+"\\"+teclado.next();
			  		
			  		try {
						folder1=new File(nomFile);
						folder2=new File(nomFile2);
						difffolder.setFolders(folder1, folder2);
						result=difffolder.compare();
						while(result.hasNext()){
							result.next().imprimir();
						
						 	}
						
					}catch(GestioFitxersException ex ){
						ex.printStackTrace();
					}
			  	break;
			  	case 7:
			  		exit="no";
					System.out.println(exit);
				while (exit.equals("no")){
					
					System.out.println("Eliga carpeta por defecto :");
					
					desti="C:\\Users\\ManuelJose\\Documents\\Unidad01\\"+teclado.next();
					GestioPlus.adrecaDetalls(desti);
					//File DirectoriContingut = new File (desti);			
					while (exit.equals("no")){
						System.out.println("Eliga Nombre de Fichero :");
						nomFile=teclado.next();
						//File Ficher = new File (desti);
					utilitats.creaciofitxers(desti, nomFile);
					System.out.println("Finalizar Creacion (si)/(no): ");
					exit=teclado.next();
					}
					exit="keep";
					System.out.println("Finalizar SALIR  (si)/(no): ");
					exit=teclado.next();
					}break;
			  	case 8:
			  		String FicheroElegido="C:\\Users\\ManuelJose\\Documents\\Unidad01\\carpeta2\\";
					GestioPlus.mostrarContingut(FicheroElegido);
					System.out.println("Elija carpeta o Fihcero:");
					nomFile=FicheroElegido+teclado.next();
					
					GestioPlus.setDetallets(nomFile);
					break;
			  	case 9:
			  		do{
			  		opcion2=menu2();
					  switch(opcion2){
					  case 1:
				  GestioPlus.mostrarContingutOcult(true,TipusOrdre.DATA_MODIFICACIO);
					  case 2:
						  GestioPlus.mostrarContingutOcult(true,TipusOrdre.NOM);
						  break;
					  case 3:
						  GestioPlus.mostrarContingutOcult(true,TipusOrdre.MIDA);
						  break;
					  case 4:
						  System.out.println("Exit................");
						  //break;
						 }
			  	
			  }while (opcion2!=4);
			  break;
			  	case 10:
			  		System.out.println("   Selecciona la carpeta que quieres ir: "+adrecaTreball);
					adreca=/**adrecaTreball+**/teclado.next();
					GestioPlus.entraA(adreca);
					System.out.println(adrecaTreball);
					GestioPlus.mostrarContingut(adrecaTreball+adreca);
						System.out.println("Seleccione el FICHERO que desea copiar :");
						String file=teclado.next();
							nomFile=adrecaTreball+adreca+"\\"+file;
							
							File sourceFile = new File(nomFile);
							System.out.println("Seleccione la carpeta Destino : ");
							desti=adrecaTreball+"\\"+teclado.next()+"\\";
							
						
							File destFile = new File(desti+"\\"+file);
							
				try {
					GestioPlus.copyFile(sourceFile, destFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}break;
			  	case 11:
			  		do{
			  		System.out.println("Seleccione el FICHERO: ");
			  		nomFile=teclado.next();
					//omFile=adrecaTreball+teclado.next();
					//File file = new File(nomFile);
					
						opcion=menu3();
						  switch(opcion){
						  case 1:
							GestioPlus.elimina(nomFile);
							break;
						  case 2:
							if ( GestioPlus.esPotEscriure(nomFile) == true){
								System.out.println(nomFile+" esPotEscriure");
							}
							if ( GestioPlus.esPotExecutar(nomFile) == true){
								System.out.println(nomFile+" esPotExecutar");
							}
							if ( GestioPlus.esPotLlegir(nomFile) == true){
								System.out.println(nomFile+" esPotLlegir");
							}
							Date d = new Date(GestioPlus.getUltimaModificacio(nomFile));
							System.out.println(nomFile+" UltimaModificacio: "+d);					
							
							break;
						  case 3:
								GestioPlus.setEsPotLlegir(nomFile,false);
								
								
								break;
						  case 4:
							  System.out.println("Nou Nom: ");
							  nomFile2=teclado.next();
							GestioPlus.reanomena(nomFile, nomFile2);
								
								break;
						  }
					}while (opcion!=5);
					
			  }
			 }while (opcion!=20);
			 
}
		private static int menu(){
			Scanner teclado = new Scanner(System.in);
			System.out.println("\n");
			for (OpcionesNumerados Metodos : OpcionesNumerados.values()) {System.out.println(Metodos.getMetode()+Metodos.getClasss());}
			System.out.print("Selecione la Opcion a Realizar:" );
			return teclado.nextInt();
			
			
		}
		private static int menu2(){
			Scanner teclado = new Scanner(System.in);
			System.out.println("\n");
			System.out.println("1.Ordenar per Ultima Modificacio");
  			System.out.println("2. Ordenar per Nom:");
  			System.out.println("3. Ordenar per tamany:");
			System.out.print("Selecione la Opcion a Realizar:" );
			return teclado.nextInt();
			
			
		}
		private static int menu3(){
			Scanner teclado = new Scanner(System.in);
			System.out.println("\n");
			System.out.println("1.Eliminar");
  			System.out.println("2. Detalles");
  			System.out.println("3.Cambiar fitxer seleccionar per a llevar permis de Lectura");
  			System.out.println("4.Reanomena");
			System.out.print("Selecione la Opcion a Realizar (5=exit) : " );
			return teclado.nextInt();
			
			
		}
}