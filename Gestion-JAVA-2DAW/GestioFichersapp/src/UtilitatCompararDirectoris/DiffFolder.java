package UtilitatCompararDirectoris;

import ioc.dam.m6.exemples.gestiofitxers.GestioFitxersException;










import java.applet.Applet;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;

import UtilitatCompararDirectoris.ResultatComparacio.ValorComparacio;





public class DiffFolder {
	
	
	private final FiltreFitxers filtefitxers =new FiltreFitxers();
	private final ComparadorDeFilePerNom comp= new ComparadorDeFilePerNom();
	//private final ResultatComparacio  ResultatComparacio = new ResultatComparacio();
	//Podem les dos carpetes FILES 
	File folder1 ;
	File folder2 ;
	/**
	 * Iterator<ResultatComparacio> compare(). Realitza la comparació entre les 
	 * dues carpetes assignades per mitjà del mètode setFolders 
	 * i retorna un Iterador d’instàncies de ResultatComparacio.
	 * @return
	 */
	public   Iterator<ResultatComparacio> compare(){
	
		ResultatComparacio resultComparacio;
		//Obtenim els fitxers de cada carpeta.
		File[] fitxers1 = folder1.listFiles(filtefitxers);
		File[] fitxers2 = folder2.listFiles(filtefitxers);
		//Creem una lista amb array lista de  la class--->ResultatComparacio
		ArrayList<ResultatComparacio> LlistaComparacio =new ArrayList<ResultatComparacio>();
		
			int pos1=0;
			int pos2=0;
			
			//Ordenem els fitxers de les carpetes 
			Arrays.sort(fitxers1,comp);
			Arrays.sort(fitxers2,comp);
			
			//comparem els fitxers de cada directori
			while(pos1<fitxers1.length && pos2<fitxers2.length){
				
				//Comparem primer el nom dels fitxers
				int compara=fitxers1[pos1].getName().compareTo(fitxers2[pos2].getName());
					//Si es diuen igual ->
				if(compara==0){
						//Y  tambe tene el mateix tamany y data de modificacio
						if(fitxers1[pos1].length()==fitxers2[pos2].length()&& 
								fitxers1[pos1].lastModified()== fitxers2[pos2].lastModified()){
							
							//Guardem el valor del fitxer posanli la "etiqueta" -> IGUALS
    /**Save in  of Obj.Class->**/resultComparacio=new ResultatComparacio(
    							fitxers1[pos1].getName(),ValorComparacio.IGUALS); 
						
							//Si uno esta creat avans que lialtre
						}else if(fitxers1[pos1].lastModified() <  
								fitxers2[pos2].lastModified()){
							//Fitxer 1 es mes NOU 
	/**Save in of Obj.Class->**/resultComparacio=new ResultatComparacio(
							fitxers1[pos1].getName(),ValorComparacio.MES_NOU_EN_2);
						}else{
							//Fitxer 2 es mes NOU 
	/**Save in  of Obj.Class->**/resultComparacio=new ResultatComparacio(
							fitxers1[pos1].getName(),ValorComparacio.MES_NOU_EN_1);
							
						}
					//Posicions
					pos1++;
					pos2++;
				}else if(compara<0){
						//Agerrem el Priemr si no son iguals  en NOm
						
	/**Save in  of Obj.Class->**/resultComparacio=new ResultatComparacio(
					fitxers1[pos1].getName(),ValorComparacio.FALTA_EN_2);
					//Etiquem en la informacio Resultat que falta el 2 
					//Pasem de Posicio ++
					pos1++;
					}else{
	/**Save in  of Obj.Class->**/resultComparacio=new ResultatComparacio(
						fitxers2[pos2].getName(),ValorComparacio.FALTA_EN_1);
						pos2++;
						}
				
				/**END: Una vez comparado y etiquitado (FALTA_EN_2,MES_NOU_EN_1 etc )
				 * Añadimos cada Obj(la informacion de los fitxeros de resultComparacio)  al Arraylist
				 */
					//Añadim el Resultat en el ITERADOr de ResultComparacio
					LlistaComparacio.add(resultComparacio);
					}
					
					//Per a poder saber  qui conte mes elements
					while(pos1<fitxers1.length){
							//--> 1r.->while(pos1<fitxers1.length && pos2<fitxers2.length)
					//Si pos1 Encara entra asi fitxers1.length , es la mes llarga 
						resultComparacio=new ResultatComparacio(
								fitxers1[pos1].getName(),ValorComparacio.FALTA_EN_2);
								LlistaComparacio.add(resultComparacio);
					pos1++;
					}
					
					while(pos2<fitxers2.length){
						//Si pos2  Encara entra asi fitxers1.length , es la mes llarga 
						resultComparacio=new ResultatComparacio(
									fitxers2[pos2].getName(),ValorComparacio.FALTA_EN_1);
									LlistaComparacio.add(resultComparacio);
						pos2++;
						}
						
						
					return  LlistaComparacio.iterator();
	}
//PARA EXAMEN FIJO :
//Creem la class Filtre Fitxer per a poder filtrar soles el archius que siguen fitxers de la carpeta
private class FiltreFitxers implements FileFilter{
public boolean accept(File file) {
return file.isFile();
}
 
}


//Listem el fitxer de  la carpeta pasada 
public void listarficheros(File folder){ 
	File[] ficheros = folder1.listFiles();
	for (int x=0;x<ficheros.length;x++){
	  System.out.println(ficheros[x].getName());
	}
}	


public void setFolders(File folder1, File folder2)throws GestioFitxersException{
	if(!folder1.exists()|| !folder2.exists()){
		throw new GestioFitxersException("Cal que "+ "els elements a comparar "+ "EXISTIXQUEN");
	}else if(!folder1.isDirectory()|| !folder2.isDirectory()){
		throw new GestioFitxersException("Cal que "+ "els elements a comparar "+ "siguin directoris");
	}
	this.folder1 = folder1;
	this.folder2 = folder2;
}
	
	 

/**Retorna el fitxer (com a instància de File) 
 * assignat en primer lloc en el mètode setFolders.
 * @return
 */
		
	public File getFiles1() {
        return folder1;
    }	
	public File getFiles2() {
        return folder2;
    }	
	
	//Para pdoer realizar la cordenacion del loistado array ->> Arrays.sort(fitxers1,comp);

	 
	private class ComparadorDeFilePerNom implements Comparator<File>{
		public int compare(File t, File t1) {
			int ret;
	 
				if(t==null && t1==null){
						ret=0;
				}else if (t==null){
						ret=1;
				}else if(t1==null){
						ret=-1;
				}else{
					ret = t.getName().compareTo(t1.getName());
				}
				return ret;
		}
	 
		}
}
	 

	 
		
	

