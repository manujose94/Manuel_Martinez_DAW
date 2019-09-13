/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ioc.dam.m6.exemples.fluxos;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.NonWritableChannelException;

/**
 *
 * @author josep
 */
public class Utilitats {

    private int capacitatMaximaBufferEnBytes = 524288;

    /**
     * Constructor d'instàncies d'Utilitat.
     */
    public Utilitats() {
    }

    /**
     * Constructor que rep per paràmetre la mida màxima del buffer d'intercanvi 
     * mesurada en bytes.
     * @param midaBuffer: mida del buffer d0intercanvi mesurada en bytes
     */
    
    public Utilitats(int midaBuffer) {
        capacitatMaximaBufferEnBytes=midaBuffer;
    }
    public void creaciofitxers(String  DirectoriContingut , String Ficher ){
    	System.out.println("Seleccione Carpeta:");
    	File fichero = new File (DirectoriContingut,Ficher);
    	try {
    		  // A partir del objeto File creamos el fichero físicamente
    		  if (fichero.createNewFile())
    		    System.out.println("El fichero se ha creado correctamente");
    		  else
    		    System.out.println("No ha podido ser creado el fichero");
    		} catch (IOException ioe) {
    		  ioe.printStackTrace();
    		}
    }
    
    
    
    
    
    //Copia de Fluuxos tant de Bytes com de chars 
    /**
     * Copia a nivell de bytes el contingut de l'Stream <code>input</code> a l'Stream 
     * <code>output</code>. 
     * @param input. Stream d'origen de dades.
     * @param output. Stream destí
     * @throws IOException 
     */
    public void copiaDeBytes(InputStream input, OutputStream output) throws IOException{
        int bytesLlegits=0;
        byte[] buffer = new byte[capacitatMaximaBufferEnBytes];
        while(bytesLlegits!=-1){
            output.write(buffer, 0, bytesLlegits);
            bytesLlegits=input.read(buffer);
        }
    }
    
    /**
     * Copia a nivell de caràcters el contingut del Reader <code>reader</code> al 
     * Writer <code>writer</code>. 
     * @param reader. Reader d'origen de dades.
     * @param writer. Writer destí
     * @throws IOException 
     */
    public void copiaDeChars(Reader reader, Writer writer) throws IOException {
        int charsLlegits=0;
        char[] buffer = new char[midaBytesACaracters(capacitatMaximaBufferEnBytes)];
        while(charsLlegits!=-1){
            writer.write(buffer, 0, charsLlegits);
            charsLlegits=reader.read(buffer);
        }
    }    

    /**
     * Copia a nivell de bytes el contingut del fitxer <code>origen</code> al fitxer 
     * <code>desti</code>. Internament aquest mètode usa instancies d'Streams per 
     * realitzar al còpia.
     * @param origen. Fitxer origen.
     * @param desti. Fitxer destí.
     * @throws IOException 
     */
    public void copiaFitxersDeBytes(File origen, File desti) throws IOException {
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            input = new FileInputStream(origen);
            output = new FileOutputStream(desti);
            copiaDeBytes(input, output);
        } finally {
            intentarTancar(input);
            intentarTancar(output);
        }
    }
    
    /**
     * Copia a nivell de bytes el contingut del fitxer <code>origen</code> al fitxer 
     * <code>desti</code>. Internament aquest mètode usa instancies d'Streams per 
     * realitzar al còpia.
     * @param origen. Fitxer origen.
     * @param desti. Fitxer destí.
     * @throws IOException 
     */
    public void copiaFitxersDeCaracters(File origen, File desti) throws IOException {
        FileReader reader = null;
        FileWriter writer = null;
        try {
            reader = new FileReader(origen);
            writer = new FileWriter(desti);
            copiaDeChars(reader, writer);
        } finally {
            intentarTancar(reader);
            intentarTancar(writer);
        }
    }

    /**
     * Copia a nivell de bytes el contingut del fitxer <code>origen</code> al fitxer 
     * <code>desti</code>. Internament aquest mètode usa instancies de Channel per 
     * realitzar al còpia. Es tracta doncs d'un mètode molt optimitzat.
     * @param origen. Fitxer origen.
     * @param desti. Fitxer destí.
     * @throws IOException 
     */
    public void copiaFitxersDeBytesNio(File origen, File desti) throws IOException {
        long size;
        long count = 0;
        FileChannel input = null;
        FileChannel output = null;
        try {
        	//Obrim el canal origen
            input = new FileInputStream(origen).getChannel();
            //Obrim el Canal Desti
            output = new FileOutputStream(desti).getChannel();
            //Calculem la mida 
            size = input.size();
            //Mnetres no s'hagi copiar tot el fitcxer
            while (count < size) {
                count += output.transferFrom(input, 0, size - count);
            }
        } finally {
        	//Tancar els Canals 
            intentarTancar(input);
            intentarTancar(output);
        }
    }
    
    /**
     * Calcula el nombre de caràcters que hi ha en el nombre de bytes indicat per 
     * paràmetre.
     * @param bytes nombre de bytes a calcular.
     * @return nombre de caràcters que representaran els bytes indicats.
     */
    public long midaBytesACaracters(long bytes){
        return (bytes>>1);
    }

    /**
     * Calcula el nombre de bytes que ocupen el nombre de caracters indicat per 
     * paràmetre.
     * @param caracters nombre de caracters a calcular.
     * @return nombre de caràcters que representaran els caracters indicats.
     */
    public long midaCaractersABytes(long caracters){
        return (caracters<<1);
    }

    /**
     * Calcula el nombre de caràcters que hi ha en el nombre de caracters indicat per 
     * paràmetre.
     * @param bytes
     * @return 
     */
    public int midaBytesACaracters(int bytes){
        return (bytes>>1);
    }

    /**
     * Calcula el nombre de bytes que ocupen el nombre de caracters indicat per 
     * paràmetre.
     * @param caracters nombre de caracters a calcular.
     * @return nombre de caràcters que representaran els caracters indicats.
     */
    public int midaCaractersABytes(int caracters){
        return (caracters<<1);
    }
    
    /**
     * Obté la capacitat màxima del buffer d'intercanvi en bytes.
     * @return capacitatMaximaBufferEnBytes
     */
    public int getMidaBuffer() {
        return capacitatMaximaBufferEnBytes;
    }

    /**
     * Assigna la capacitat màxima del buffer d'intercanvi.
     * @param capacitatMaximaBufferEnBytes. La capacitat màxima a assignar
     */
    public void setMidaBuffer(int midaBuffer) {
        this.capacitatMaximaBufferEnBytes = midaBuffer;
    }
    
//    /**
//     * Obté la capacitat màxima del buffer d'intercanvi mesurarda en cacracters.
//     * Cal tenir en compte que un caracter ocupa 2 bytes.
//     * @return capacitatMaximaBufferEnChars
//     */
//    public int getCapacitatMaximaBufferEnChars() {
//        return midaBytesACaracters(capacitatMaximaBufferEnBytes);
//    }

    /**
     * Intenta tancar un objecte de tipus Closeable (flux, reader, writer, etc.). En 
     * cas que es produixi una excepcció d'entrada/sortida es mostrarà per la 
     * sortida d'error estandar.
     * es cert.
     * @param aTancar
     * @param excepcioSiError
     * @throws IOException 
     */
    //Es tracta de capturar les excecpcion degudes als tancamente i deixar-les sense reportar
    	//Se realitza este metodo idenpendent per a capturar la excepcio
    			// Se ahorrem posar en el metots que operem el varios fulxes 
		/**	} finally {
				try{ 
				f1.close();
			}finally{ 
				try{  
				f2.close(); 
			}finally{ 
				f3.close();  
					}  
				}  
			}**/

    public void intentarTancar(Closeable aTancar){
        try {
            if (aTancar != null) {
                aTancar.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    //Amb aço sols sera necesari  ---> } finally { intentarTancar(f1)  intentarTancar(f2)
    
    
    /**
     * Recodifica un fitxer (<code>origen</code>) codificat originàriament d'acord 
     * amb la <code>codificacioOri</code> reescrivint-lo amb la codificació <code>
     * codificacioDest</code>. El fitxer resultant substituirà l'original amb el 
     * mateix nom i la mateixa ubicació.
     * @param origen Fitxer a recodificar
     * @param codificacioOri Codificació del fitxer original
     * @param codificacioDest Codificació en que es desitja reescriure el fitxer.
     * @throws IOException 
     */
    public void recodificaFitxer(File origen,  
                                    String codificacioOri, 
                                    String codificacioDest) throws IOException{
        File tmp = getFitxerNou(new File(".tmp.dat"));
        InputStreamReader reader = new InputStreamReader(
                                new FileInputStream(origen), codificacioOri);
        OutputStreamWriter writer = new OutputStreamWriter(
                                new FileOutputStream(tmp), codificacioDest);
        try {
            copiaDeChars(reader, writer);
        } finally {
            intentarTancar(reader);
            intentarTancar(writer);
        }        
        copiaFitxersDeBytesNio(tmp, origen);
        tmp.delete();
    }

    /**
     * Recodifica un fitxer (<code>origen</code>) codificat originàriament d'acord 
     * amb la <code>codificacioOri</code> en un fitxer <code>destí</code> codificat 
     * amb la codificació <code>codificacioDest</code>. El fitxer resultant no 
     * substituirà l'original.
     * @param origen Fitxer a recodificar
     * @param desti Fitxer destí
     * @param codificacioDest Codificació en que es desitja reescriure el fitxer.
     * @throws IOException 
     */
    public void recodificaFitxer(File origen, File desti,  
                                    String codificacioDest) throws IOException{
        FileReader reader = new FileReader(origen);
        OutputStreamWriter writer = new OutputStreamWriter(
                                new FileOutputStream(desti), codificacioDest);
        try {
            copiaDeChars(reader, writer);
        } finally {
            intentarTancar(reader);
            intentarTancar(writer);
        }        
    }

    /**
     * Recodifica un fitxer (<code>origen</code>) codificat originàriament d'acord 
     * amb la <code>codificacioOri</code> en un fitxer <code>destí</code> codificat 
     * amb la codificació <code>codificacioDest</code>. El fitxer resultant no 
     * substituirà l'original.
     * @param origen Fitxer a recodificar
     * @param codificacioOri Codificació del fitxer original
     * @param desti Fitxer destí
     * @throws IOException 
     */
    public void recodificaFitxer(File origen,
                                    String codificacioOri, 
                                    File desti) throws IOException{
        InputStreamReader reader = new InputStreamReader(
                                new FileInputStream(origen), codificacioOri);
        FileWriter writer = new FileWriter(desti);
        try {
            copiaDeChars(reader, writer);
        } finally {
            intentarTancar(reader);
            intentarTancar(writer);
        }        
    }

    /**
     * Recodifica un fitxer (<code>origen</code>) codificat originàriament d'acord 
     * amb la <code>codificacioOri</code> en un fitxer <code>destí</code> codificat 
     * amb la codificació <code>codificacioDest</code>. El fitxer resultant no 
     * substituirà l'original.
     * @param origen Fitxer a recodificar
     * @param codificacioOri Codificació del fitxer original
     * @param desti Fitxer destí
     * @param codificacioDest Codificació en que es desitja reescriure el fitxer.
     * @throws IOException 
     */
    public void recodificaFitxer(File origen,  
                                    String codificacioOri, 
                                    File desti,  
                                    String codificacioDest) throws IOException{
        InputStreamReader reader = new InputStreamReader(
                                new FileInputStream(origen), codificacioOri);
        OutputStreamWriter writer = new OutputStreamWriter(
                                new FileOutputStream(desti), codificacioDest);
        try {
            copiaDeChars(reader, writer);
        } finally {
            intentarTancar(reader);
            intentarTancar(writer);
        }        
    }
    
    /**
     * Escriu la cadena de text passada per paràmetre en un writer.
     * @param text Text a escriure
     * @param writer Writer destí
     * @throws IOException 
     */
    public void copiarStringAWriter(String text, Writer writer) throws IOException{
        int length = text.length();
        int offset = 0;
        while(offset<length){
            int charsAEscriure = Math.min(
                                midaBytesACaracters(capacitatMaximaBufferEnBytes), 
                                length - offset);
            writer.write(text, offset, charsAEscriure);
            offset+=charsAEscriure;
        }
    }

    /**
     * LLegeix el contingut del reader i el retorna en forma de cadena de caracter String.
     * @param reader Font de dades
     * @return El contingut del fitxer en forma d'String.
     * @throws IOException 
     */
    
    public String copiarReaderAString(Reader reader) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        int charsLlegits=0;
        char[] buffer = new char[midaBytesACaracters(capacitatMaximaBufferEnBytes)];
        while(charsLlegits!=-1){
            stringBuilder.append(buffer, 0, charsLlegits);
            charsLlegits=reader.read(buffer);
        }
        return stringBuilder.toString();
    }
    
    /**
     * El mètode  getFitxerNou retorna el nom d'un fitxer inexistent basant-se 
     * en la nom del paràmetre fitxer. Si el fitxer passat no existeix, 
     * el nom d'aquest es retorna integre i sense modificar. En  cas que existexi, 
     * el mètode afegeix un numero com a sufix del nom. Es cerca el número que 
     * combinat amb el nom i l'extensió del fitxer doni com a resultat un fitxer 
     * nou i per tant inexistent en el sistema.
     * @param file Fitxer base
     * @return 
     */
    public File getFitxerNou(File file) {
        int cont=1;
        String[] nom = new String[2];
        while(file.exists()){
            int indPunt = file.getAbsolutePath().lastIndexOf('.');
            nom[0] = file.getAbsolutePath().substring(0, indPunt);
            nom[1] = file.getAbsolutePath().substring(indPunt+1);
            if(nom[1].length()>0){
                file = new File(nom[0] + "("+ cont +")"+"."+nom[1]);
            }else{
                file = new File(nom[0] + "("+ cont +")");
            }
            cont++;
        }
        return file;
    }

    public void escriu(long offsetFitxer, ByteBuffer buffer, FileChannel channel) 
                                                              throws IOException{
        int escrit = 0;
        //Mentre no s'hagi escrit tot el contingut del buffer...
        while(escrit<buffer.limit()){
             escrit += channel.write(buffer, offsetFitxer+escrit);            
        }
    }

    public void escriu(ByteBuffer buffer, FileChannel channel) throws
                                                                     IOException{
    	
        //Mentre no s'hagi escrit tot el contingut del buffer...
        while(buffer.position()<buffer.limit()){
             channel.write(buffer);   
             throw new NullPointerException();
         	}
    }
        
    
    
    
  public void llegeix(long offsetFitxer, int mida, ByteBuffer buffer,
                                       FileChannel channel) throws IOException{
        int llegit = 0;
        int totalLLegit=0;
        //Mentre no s'hagi llegit la quantitat desitjada...
        while(llegit!=-1 && totalLLegit<mida){
             llegit = channel.read(buffer, offsetFitxer+totalLLegit);
             totalLLegit+=llegit;
        }
    }

    public void llegeix(int mida, ByteBuffer buffer, FileChannel channel) 
                                                            throws IOException{
        int llegit = 0;
        int totalLLegit=0;
        //Mentre no s'hagi llegit la quantitat desitjada...
        while(llegit!=-1 && totalLLegit<mida){
             llegit = channel.read(buffer);
             totalLLegit+=llegit;
        }
    }
}