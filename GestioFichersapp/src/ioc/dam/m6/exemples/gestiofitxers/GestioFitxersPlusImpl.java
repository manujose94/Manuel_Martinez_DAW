/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ioc.dam.m6.exemples.gestiofitxers;

import ioc.dam.m6.exemples.fluxos.Utilitats;
import ioc.dam.m6.exemples.gestiofitxers.contingut.ContingutFitxerCaracters;
import ioc.dam.m6.exemples.fluxos.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;


public class GestioFitxersPlusImpl extends GestioFitxerImpl implements GestioFitxersPlus {
    private Utilitats utilitats=new Utilitats();
    int columnesBase = 0;
    int files2=0;
    private int files=0;
    int columnesBase2 = 2;
    int columnes2 = columnesBase2;
    private Object[][] contingut;
    @Override
    
    /**----------------------------------------------------------------------------------------------**/
    //1. MOURE EL FITXER 
       	//A. Se Comproba que no hi haja un fitxer igual 
       	//B. Se Comproba que siga accesible el Dic Desti
      
       //Aneu a moure el fitxer a altre lloc:
       					//Pasem : c://origen.txt ---> desti c//desti//
       public void moure(File origen, File desti) throws GestioFitxersException {
       	//Posem per defecte false  perqe en Exitencia si no es True , se valorara el error estioFitxersException
       			//--> Fitxer Existent 
           TestejarExistencia(desti, false);
           
           TestejarAcces(origen);
           try {
           	//Primer deben de copiar el fitxer 
           			//Pasan el metodo copiaFitxers de Utilitatas
               utilitats.copiaFitxersDeBytesNio(origen, desti);
               //Mes tart eliminem el ficher
               origen.delete();
           } catch (IOException ex) {
                throw new GestioFitxersException("S'ha produit un error d'entrada"
                           + " o sortida: '" + ex.getMessage() + "'", ex);
           }
           //Actuliza es de la clase GestioImpl
           actualitza();                
       }

    
    /**El mètode  rep dos
    paràmetres: el ﬁtxer a testejar i el test a realitzar. Un test amb valor cert signiﬁcarà
    que és necessari que el ﬁtxer existeixi per poder continuar, mentre que un test amb
    valor fals el que indicarà és que no hi hauria d’haver cap ﬁtxer amb aquell nom.**/
        private void TestejarExistencia(File file, boolean test) throws GestioFitxersException{
        	//Si es diferne de false 
        			//Si el fitxer existix -->moure --->   TestejarExistencia(desti, false);
            if(file.exists()!=test){
                if(test){
                    throw new GestioFitxersException("Error. el fitxer " 
                        + file.getAbsolutePath()+ " no existeix");
                }else{
                    throw new GestioFitxersException("Error. el fitxer " 
                        + file.getAbsolutePath()+ " ja existeix");
                }           
            }
        }
        
        
        
        /**El mètode és, de forma semblant, comprova que el ﬁtxer passat per
    paràmetre existeix i es troba accessible per ser llegit.**/
        
        private void TestejarAcces(File file) throws GestioFitxersException{
        	//Indiquem per defcete "TestejarExistencia" --> boolean test = true
        		//Per si no existeix
        		      TestejarExistencia(file, true);
        		        
            //es controla que es tinguin permisos per llegir la carpeta
            if(!file.canRead()){
                throw new GestioFitxersException("Alerta. No es pot llegir " 
                                            + file.getAbsolutePath()
                                            + ". No teniu prou permisos");
            }        
        }
    
    
    /**-----------------------------------------------------------------*/
        //2.COPIAR el contenido de un ichero a otro
        public void copiar(File origen, File desti) throws GestioFitxersException {
        	//Primer hi ha que mirar que tingam acces a al ficher Origen
            TestejarAcces(origen);
            crearSiCal(desti);
            try {
                utilitats.copiaFitxersDeBytesNio(origen, desti);
            } catch (IOException ex) {
                 throw new GestioFitxersException("S'ha produit un error d'entrada"
                            + " o sortida: '" + ex.getMessage() + "'", ex);
            }
            actualitza();
        }
    /** Asegura la EXISTENCIA DEL FITXER  o xqe esta o xqe se crea en el moment de lejecucio
     */
        private void crearSiCal(File file) throws GestioFitxersException{
            if(!file.exists()){//Si no existeix el fitxer
            	//Se obte  la ruta ->C:/Users//Manueljose/
                File pare = file.getParentFile();
                //Si la ruta obtenida tampoc existix se CREA
                if(!pare.exists()){
                	//Se crea la ruta del fitxer 
                	//--->C:/Users//Manueljose/ per exemple
                    pare.mkdirs();
                }
                try {
                	//Inclus aixna no sa lograt crear saltaran algunes de este dues EXCEPCIONS 
                    if(!file.createNewFile()){
                        throw new GestioFitxersException("Error. No s'ha pogut crear "  
                                                    + file.getAbsolutePath() + ".");
                    }
                } catch (IOException ex) {
                    throw new GestioFitxersException("S'ha produit un error d'entrada"
                            + " o sortida: '" + ex.getMessage() + "'", ex);
                }
            } 
        
        }
 
        public  void copyFile(File sourceFile, File destFile) throws IOException, GestioFitxersException {
            if(!destFile.exists()) {
                destFile.createNewFile();
            }
            TestejarExistencia(destFile, true);
            TestejarAcces(sourceFile);
            
            FileChannel origen = null;
            FileChannel destino = null;
            try {
                origen = new FileInputStream(sourceFile).getChannel();
                destino = new FileOutputStream(destFile).getChannel();
         
                long count = 0;
                long size = origen.size();              
                while((count += destino.transferFrom(origen, count, size-count))<size);
            }
            finally {
                if(origen != null) {
                    origen.close();
                }
                if(destino != null) {
                    destino.close();
                }
            }
        }    
        
        
        
    /**La primera permet obtenir un objecte File corresponent al
nom passat per paràmetre (el nom serà relatiu a la carpeta de treball del gestor)**/
        public File getFile(String relativeName) {
            File ret = new File(carpetaDeTreball, relativeName);        
            return ret;
        }

        /**La segona utilitat permetrà obtenir un nom de ﬁtxer, assegurant que es tracta d’un
nom inexistent encara en el sistema. El nou nom tindrà com a preﬁx la cadena
passada per paràmetre concatenada a un suﬁx numèric abans de l’extensió.**/
    public String getNouNom(String relativeName) {
        int cont=1;
        File file = new File(carpetaDeTreball, relativeName);
        String[] nom = new String[2];
        int indPunt = relativeName.lastIndexOf('.');
        nom[0] = relativeName.substring(0, indPunt);
        nom[1] = relativeName.substring(indPunt+1);
        while(file.exists()){
            if(nom[1].length()>0){
                file = new File(carpetaDeTreball, nom[0] + "("+ cont +")"+"."+nom[1]);
            }else{
                file = new File(carpetaDeTreball, nom[0] + "("+ cont +")");
            }
            cont++;
        }

        return file.getName();
    }
    
    
    
    public <ordenat> void mostrarContingutOcult(boolean mostrarOcults, ordenat Tipus){
    	//actualitza();
    	TipusOrdre ordenat=(TipusOrdre) Tipus;
    	 //System.out.print (ordenat);
        File[] fitxers;
       if(mostrarOcults==true){//obtenir els fitxers
            fitxers = carpetaDeTreball.listFiles();
        }else{
        	//Si queremos que filre los fitxeros ocultos para que no los muestre 
            fitxers = carpetaDeTreball.listFiles((FileFilter) filtreFitxersOcults);
        }
        
        //calcular el nombre de files necessari
        files = fitxers.length / columnes;
        
        if(files*columnes < fitxers.length){
            files++;//si hi ha residu necessitem un fila més
        }
        //ordenarció del contingut
        if(ordenat!=TipusOrdre.DESORDENAT){
            Arrays.sort(fitxers, comparadorDeFile);
        }
        //dimensionar la matriu contingut d'acord als resultats 
        contingut = new String[files][columnes];
        //Omplir el contingut amb els noms dels elements de la carpeta de treball
        for(int i=0; i<columnes; i++){
            for(int j=0; j<files; j++){
                int ind = j*columnes+i;
                if(ind<fitxers.length){
                    if(fitxers[ind].isDirectory()){
                        contingut[j][i]=fitxers[ind].getName()+File.separator;
                    }else{
                        contingut[j][i]=fitxers[ind].getName();
                    }
                }else{
                    contingut[j][i]="";
                }
            }
        }  
        for (int x=0; x < contingut.length; x++) {
        	 System.out.print("|");
         	  for (int y=0; y < contingut[x].length; y++) {
         		  System.out.print (contingut[x][y]);
         		    if (y!=contingut[x].length-1) System.out.print("\t");
         		  }
         	 System.out.println("|");
         	  }
    	
    	
    	 
        	 //calcular el nombre de files necessari
             files = fitxers.length;
             
           //dimensionar la matriu contingut d'acord als resultats 
             contingut = new EntradaTaula[files][columnes];
             //Omplir el contingut amb les dades dels elements de la carpeta de treball
             for(int i=0; i<files; i++){
                 String mida;
                 TipusEntrades te; //Format justificat a l'esquerra
                 TipusEntrades ted; //format justificat a la dreta
                 String sufix;
                 if(fitxers[i].isDirectory()){
                     //dades si és directori
                     te= TipusEntrades.RESALTAT;
                     ted = TipusEntrades.RESALTAT_DRETA;
                     sufix = File.separator;
                     if(!fitxers[i].canRead()){
                         mida="" + 0 + " entrades.";
                     }else{
                         mida = "" + fitxers[i].list().length + " entrades.";
                     }                
                 }else{
                     //dades si és fitxer
                     te= TipusEntrades.NORMAL;
                     ted = TipusEntrades.NORMAL_DRETA;
                     sufix="";
                     mida = "" + fitxers[i].length() + " bytes.";
                 }      
                 //data de modificació
                 Date date = new Date(fitxers[i].lastModified());

                 //Assignar la informació de les tres columnes
                 contingut[i][0]= new EntradaTaula(te, date.toString());
                 contingut[i][1]= new EntradaTaula(ted, String.valueOf(mida));
                 //contingut[i][2]= new EntradaTaula(te, date.toString());
                
             }
             for (int x=0; x < contingut.length; x++) {
            	 System.out.print("|");
             	  for (int y=0; y < contingut[x].length; y++) {
             		  System.out.print (contingut[x][y]);
             		    if (y!=contingut[x].length-1) System.out.print("\t");
             		  }
             	 System.out.println("|");
             	  }
            
            	
             
         }
    
    
    public void mostrarContingut(String carpeta){
    	File dir = new File(carpeta);
    	String[] ficheros = dir.list();
    	
    	if (ficheros == null)
    		  System.out.println("No hay ficheros en el directorio especificado");
    		else { 
    		  for (int x=0;x<ficheros.length;x++)
    			
    		    System.out.println(x+". "+ficheros[x]);
    		}
    }
    public  String mostrarllIST(String carpeta){
    	File dir = new File(carpeta);
    	String[] ficheros = dir.list();
    	StringBuilder strBuilder = new StringBuilder();
    	if (ficheros == null){
    		  System.out.println("No hay ficheros en el directorio especificado");
    	}else { 
    		  for (int x=0;x<ficheros.length;x++)
    			 // String llist = System.out.println(x+". "+ficheros[x]);
    			  strBuilder.append(ficheros[x]+"\n");
    		  strBuilder.append("\n");
    }
		return  strBuilder.toString();

    }

    @Override
    public void comparar(File origen, File desti) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  
  /**  public ContingutFitxerCaracters 
              instanciarContingutFitxerCaracters(File fitxer) 
                                 throws GestioFitxersException{
                 return new ContingutFitxerCaractersImpl(fitxer);
    }
    **/
    							
   

	@Override
	public ContingutFitxerCaracters instanciarContingutFitxerCaracters(File arg0)
			throws GestioFitxersException {
		// TODO Auto-generated method stub
		return null;
	}
   
 
 
	public void adrecaDetalls(String adreca) throws GestioFitxersException{
		setAdrecaCarpeta(adreca);
		System.out.println(getAdrecaCarpeta());
		mostrarContingut(adreca);
	}
	/** BY MANU **/
	
	public void escriure(File Ficher) throws IOException{
		int MIDA_BYTES=1024;
		
		
		//InputStreamReader fr = new InputStreamReader ((inFILE),"UTF-8");
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(MIDA_BYTES);
		@SuppressWarnings("resource")
		FileChannel InChannel = new FileInputStream(Ficher).getChannel();

		utilitats.escriu( byteBuffer, InChannel);
	}
	
	

	public void escriureSinChannel(File Ficher) throws IOException{
		int MIDA_BYTES=1024;
		
		FileInputStream inFILE = new FileInputStream(Ficher);
		//InputStreamReader fr = new InputStreamReader ((inFILE),"UTF-8");
		ShortBuffer shBuffer = ShortBuffer .allocate(MIDA_BYTES);
		int i=0;
			
		for(short value=32;value<128; value++){ 
			shBuffer.put(value); //Assignem cada valor usant put 
			 /* flip, marca la posició actual com el limit de dades entrades 13 * i 
			  * endarrereix la posició actual del buffer a la posició 14 * inicial. 
			  * En el nostre cas marcarà la posició 94 com a límit 15 * 
			  * de les dades i es situarà a la posició zero*/
		}
			 shBuffer.flip(); 
			
			 
		 while(i<shBuffer.limit()){ 
			 System.out.printf("%4d", shBuffer.get()); 
			 i++;
		}
		
	}
	public  void getEspaiTotalCarpetaTreball(File adreca) {
        ByteFormat format = new ByteFormat("#,##0.00");
        System.out.print(format.format(carpetaDeTreball.getUsableSpace()));    
    }
	public  void LeerFichero(Reader reader ) throws FileNotFoundException {
		 BufferedReader bf  = null;
           
            bf = new BufferedReader(reader);
            //Lectura del Fichero 
            	String Linea ;
            	try {
					while ((Linea=bf.readLine())!=null)
						System.out.println(Linea);
					//System.out.println(bf.read());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					//Cerraremos los ficheros para asegurarnos
					// que se cierra tanto si todo va bien como si salta excepcion
					if(null != reader){
						try {
							reader.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
    }
	public void EscribirFicheroAñadirFicheroExistente( String file , String Contenido ){
		PrintWriter pw =null;
		FileWriter fichero=null;
		try {
			fichero = new FileWriter(file,true);
			pw = new PrintWriter(fichero);
			fichero.write(Contenido);
			//Guardamos los cambios del contenido 
			fichero.flush();
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//Cerraremos los ficheros para asegurarnos
			// que se cierra tanto si todo va bien como si salta excepcion
			if(null != fichero){
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void EscribirFichero(FileWriter writer,  String Contenido ){
		
		try {
			
			writer.write(Contenido);
			//Guardamos los cambios del contenido 
			writer.flush();
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//Cerraremos los ficheros para asegurarnos
			// que se cierra tanto si todo va bien como si salta excepcion
			if(null != writer){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void EcribirTtx(OutputStreamWriter out  , String Contenido , String Contenido2 ) throws IOException{
		int bytesLlegits=0;
	
		//OutputStreamWriter OutputStreamWriter = new OutputStreamWriter(out);
		out.write(Contenido2);
		out.write(Contenido);
		out.close();
		
	}
	public void ReadTtx(InputStreamReader inputReader   ) throws IOException{
		int bytesLlegits=0;
	int charsLlegits=0;
	char[] buffer = new char[1000];
	 while(charsLlegits!=-1){
		 charsLlegits=inputReader.read(buffer);
		
	 }
	 inputReader.close();
	}
	
	//**************** DETALLS FICHERS **//
    public String setDetallets(String fitxer) throws GestioFitxersException{
        File file = new File(fitxer);
        //es controla que l'adreça passada existeixi i sigui un directori
        ByteFormat byteFormat= new ByteFormat("#,###.0", ByteFormat.BYTE);
        StringBuilder strBuilder = new StringBuilder();
        if(!file.exists()){
            throw new GestioFitxersException("Error. No es pot obtenir "
                                        + " informació " + "de " + fitxer 
                                        +  ", no existeix.");
        }
        //es controla que es tinguin permisos per llegir la carpeta
        if(!file.canRead()){
            throw new GestioFitxersException("Alerta. No es pot  accedir a " 
                                        + fitxer + ". No teniu prou permisos");
        }
        strBuilder.append("INFORMACIÓ DEL FITXER DONAT ");
        strBuilder.append("\n\n");
        //S'afegeix el nom
        strBuilder.append("Nom: ");
        strBuilder.append(fitxer);
        strBuilder.append("\n");
        //El tipus (carpeta o fitxer)
        strBuilder.append("Tipus: ");
        if(file.isFile()){
            //es fitxer
            strBuilder.append("fitxer");
            strBuilder.append("\n");
            //s'escriu La mida
            strBuilder.append("Mida: ");
            strBuilder.append(byteFormat.format(file.length()));
            strBuilder.append("\n");
        }else{
            //es carpeta
            strBuilder.append("carpeta");
            strBuilder.append("\n");            
            //S'indica el nombre d'elements continguts
            strBuilder.append("Contingut: ");
            strBuilder.append(file.list().length);
            strBuilder.append(" entrades\n");
        }        
        try {
            strBuilder.append(file.getCanonicalPath());
        } catch (IOException ex) {/*Mai es produirà aquest error*/}
        strBuilder.append("\n");        
        //Afegim la data de la última modificació
        strBuilder.append("Última modificació: ");
        Date date = new Date(file.lastModified());
        strBuilder.append(date.toString());
        strBuilder.append("\n");        
        //Indiquem si és o  no un fitxer ocult
        strBuilder.append("Ocult: ");
        strBuilder.append((file.isHidden())?"Si":"No");
        strBuilder.append("\n");
        if(file.isDirectory()){
            //Mostrem l'espai lliure
            strBuilder.append("Espai lliure: ");
            strBuilder.append(byteFormat.format(file.getFreeSpace()));
            strBuilder.append("\n");
            //Mostrem l'espai disponible
            strBuilder.append("Espai disponible: ");
            strBuilder.append(byteFormat.format(file.getUsableSpace()));
            strBuilder.append("\n");
            //Mostrem l'espai total
            strBuilder.append("Espai total: ");
            strBuilder.append(byteFormat.format(file.getTotalSpace()));
            strBuilder.append("\n");
            strBuilder.append("Llist Contingut: ");
            strBuilder.append("\n");
            strBuilder.append(mostrarllIST(fitxer)); 
        }
        
        System.out.println( strBuilder.toString() );
        return strBuilder.toString();        
    }
    private boolean mostrarOcults=false;
    
    
    public boolean getMostrarOcults() {
        return mostrarOcults;
     }

     @Override
     public void setMostrarOcults(boolean ocults) {
         this.mostrarOcults=ocults;
         actualitza();
     }
     
     
    
   private class FiltreFitxersOcults implements FileFilter, FilenameFilter{
        @Override
        public boolean accept(File file) {
            return !file.isHidden();
        }
        
        @Override
        public boolean accept(File pfile, String string) {
            File file = new File(pfile, string);
            return !file.isHidden();            
        }
        
    }
   
   
 
}
