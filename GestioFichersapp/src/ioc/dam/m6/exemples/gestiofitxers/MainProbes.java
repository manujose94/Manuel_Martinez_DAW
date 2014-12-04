package ioc.dam.m6.exemples.gestiofitxers;

import ioc.dam.m6.exemples.fluxos.Utilitats;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;
import java.util.Scanner;

public class MainProbes {
	 Scanner teclado = new Scanner(System.in);
	public static void main(String[] args) throws IOException {
		String fichero = "C:\\Users\\ManuelJose\\Documents\\Unidad01\\carpeta2\\forza.txt";
		GestioFitxersPlusImpl GestioPlus = new GestioFitxersPlusImpl();
		Utilitats utilitats = new Utilitats();
		File Ficher = new File ("C:\\Users\\ManuelJose\\Documents\\Unidad01\\carpeta1\\halo");
		
		File Adreca=null ;
		Adreca = File.listRoots()[0];
		GestioPlus.getEspaiTotalCarpetaTreball(Adreca);
		
	FileOutputStream out= new  FileOutputStream(fichero);
	OutputStreamWriter OutputStreamWriter = new OutputStreamWriter(out);
	String Contenido= "piter la asila ";
	String Contenido2= "MUUUUUUSSTANG";
	///Hay que realizar que añada continido , al contenido ya existente .
		GestioPlus.EcribirTtx(OutputStreamWriter, Contenido , Contenido2);
		FileInputStream input= new  FileInputStream(fichero);
		InputStreamReader inputreader = new InputStreamReader(input);
		//No funciona !!!
		GestioPlus.ReadTtx(inputreader);
		/**try {
			//GestioPlus.escriureSinChannel(Ficher);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		//DataOutputStream out= new DataOutputStream(new FileOutputStream(fichero));
		 FileWriter writer = null;
		try {
			writer = new FileWriter(fichero);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String text= "BienVenidos al Mundo de Forza Horioz";
		//utilitats.copiarStringAWriter(text, writer);
		//Creates a FileReader Object**/
		  FileReader fr = new FileReader(fichero); 
	      //CharBuffer Bfc =  null;
		  // GestioPlus.EscribirFicheroAñadirFicheroExistente( fichero, "EMPEZAMOS CON FORZA");
	    
		 GestioPlus.LeerFichero(fr);
		  /** 
		   FileReader frr = new FileReader(fichero);
		   BufferedReader bf = new BufferedReader(fr);
		   
		   while ((fichero = bf.readLine())!=null) {
			   System.out.println(fichero);
			   }**/
		 //  GestioPlus.EscribirFicheroAñadirFicheroExistente(fichero, "fichero");
		  // GestioPlus.LeerFichero(fr);
		   
	   /**   char [] a = new char[50];
	      fr.read(a); // reads the content to the array
	      for(char c : a)
	          System.out.print(c); //prints the characters one by one
	      
	      **/
	     // System.out.print( utilitats.copiarReaderAString(fr));
	    

	      
	      // Writes the content to the file
	     // writer.write("This\n is\n an\n example\n"); 
	    //  writer.flush();
	     // writer.close();

	      //Creates a FileReader Object
	     /**
	      char [] a = new char[50];
	      fr.read(a); // reads the content to the array
	      for(char c : a)
	         System.out.print(c); //prints the characters one by one
	      fr.close();**/
	    
		
	      
	}

}
