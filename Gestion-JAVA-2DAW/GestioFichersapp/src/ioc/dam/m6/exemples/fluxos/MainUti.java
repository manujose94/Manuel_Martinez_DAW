package ioc.dam.m6.exemples.fluxos;

import java.io.File;
import java.io.IOException;

public class MainUti {
	static String fichero ="C:\\Users\\ManuelJose\\Videos\\halo.txt";
	static String[] bufferfichers={"C:\\Users\\ManuelJose\\Videos\\halo1.txt","C:\\Users\\ManuelJose\\Videos\\halo2.txt","C:\\Users\\ManuelJose\\Videos\\halo3.txt"};
	static Utilitats uti = new Utilitats();
	public static void main(String[] args) throws IOException {
		 int cont = 0;
		while( cont <bufferfichers.length){
		File file = new File(bufferfichers[cont]);
		 try{
			  // A partir del objeto File creamos el fichero físicamente
			 if (file.createNewFile())
			    System.out.println("El fichero se ha creado correctamente");
			  else
				 // System.out.println("No ha podido ser creado el fichero");
				  if (file.exists())
			    System.out.println("Fichero ya esistente ");
			}catch (IOException ioe) {
			  ioe.printStackTrace();
			}cont++;
		}
		File file = new File(bufferfichers[0]);
		File file2 = new File(bufferfichers[1]);
		uti.copiaFitxersDeBytes(file, file2);
		 System.out.println(uti.getFitxerNou(file));
		//uti.copiaFitxersDeBytesNio(file, file2);
		
	}



}