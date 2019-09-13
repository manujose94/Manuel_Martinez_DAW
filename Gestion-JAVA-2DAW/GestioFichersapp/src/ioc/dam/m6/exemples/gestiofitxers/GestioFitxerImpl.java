/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ioc.dam.m6.exemples.gestiofitxers;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;


public class GestioFitxerImpl implements GestioFitxers{
    protected final FiltreFitxersOcults filtreFitxersOcults=new FiltreFitxersOcults();
    protected final ComparadorDeFile comparadorDeFile = new ComparadorDeFile();
    private int files=0;
    private int columnesBase = 2;
    protected int columnes = columnesBase;
    private Object[][] contingut;
    private String[] titolColumnesDetall = {"Nom", "Mida", "Data modif."};
    private FormatVistes formatVistes=FormatVistes.NOMS;
    protected TipusOrdre ordenat=TipusOrdre.DESORDENAT;
    private boolean mostrarOcults=false;
    
    protected File carpetaDeTreball=null;   /*CANVI PROTECTEC*/
    
    
    /**
     * Constructor de la classe GestioFitxersImpl. Inicialitza la carpeta de treball
     * amb el primer dels ssitemes de fitxers disponibles i actualitza el contingut
     */
    public GestioFitxerImpl(){
        carpetaDeTreball = File.listRoots()[0];
        actualitza();
    }
    
    
    /**
     * Assigna l'adreça passada per paràmetre com a carpeta actual.
     * @param adreca String amb l'adreça que s'asignarà com a carpeta actual
     * @throws GestioFitxersException En cas que la ruta no sigui una carpeta 
     * es llançarà un GestióFitxersException
     */
    @Override
    public void setAdrecaCarpeta(String adreca) throws GestioFitxersException{
        File file = new File(adreca);
        //es controla que l'adreça passada existeixi i sigui un directori
        if(!file.isDirectory()){
            throw new GestioFitxersException("Error. S'esperava un directori, però " 
                    + file.getAbsolutePath() + " no és un directori");
        }
        //es controla que es tinguin permisos per llegir la carpeta
        if(!file.canRead()){
            throw new GestioFitxersException("Alerta. No es pot  accedir a " 
                                        + file.getAbsolutePath() 
                                        + ". No teniu prou permisos");
        }
        //nova assignció de la carpeta de treball
        carpetaDeTreball=file;
        //es requereix actualitzar el contingut
        actualitza();
    }
    
    /**
     * Obté l'adeça de la carpeta actual en forma de cadena
     * @return String que representa la carpeta actual
     */
    @Override
    public String getAdrecaCarpeta(){
        return carpetaDeTreball.getAbsolutePath();
    }
    
    /**
     * Obté el nom de la carpeta actual. No és el nom de la ruta sencera sinó 
     * només el nom de la carpeta acual en questió.
     * @return cadena amb el nom de la carpeta
     */
    @Override
    public String getNomCarpeta(){
        return carpetaDeTreball.getName();
    }

    /**
     * Obté una llista amb tosts els fitxers i carpetes que conté
     * l'adreça actual en el primer nivell.
     * @return Array de cadenes amb els fitxers de fitxers i carpetes
     */
    @Override
    public Object[][] getContingut() {
        return contingut;
    }    
    
    
    /**
     * Obté el nombre de columnes en que es visualitzarà la llista
     * de fitxers.
     * @return columnes
     */
    @Override
    public int getColumnes() {
        return columnes;
    }

    /**
     * Assigna el nombre de columnes en que es desitja visualitzar la llista
     * de fitxers.
     * @param columnes columnes a assignar
     */
    @Override
    public void setColumnes(int columnes) {
        this.columnes = columnes;
        this.columnesBase=columnes;
    }
    
    /**
     * Obté el nombre de files en que es mostrarà el contingut de la carpeta 
     * actual
     * @return files
     */
    @Override
    public int getFiles() {
        return files;
    }
    
    @Override
    public int numArrels(){
        return File.listRoots().length;
    }
    
    @Override
    public String nomArrel(int id){
        return File.listRoots()[id].toString();
    }
    
     /**
     * Fa que la carpeta de treball passi a ser la carpeta pare de la actual
     */
    @Override
    public void amunt(){
        if(carpetaDeTreball.getParentFile()!=null){
            carpetaDeTreball = carpetaDeTreball.getParentFile();
            actualitza();
        }
    }
    
    /**
     * Fa que la ruta relativa a la carpeta de traball i representada pel 
     * paràmetre nomCarpeta es converteixi en la nova carpeta de treball.
     * En cas que la ruta passada no es correspongui amb cap carpeta existent
     * es llançarà un error.
     * @param nomCarpeta Ruta relativa a la carpeta de treball que es destja
     * convertir en la nova carpeta de treball
     * @throws GestioFitxersException Exepció en cas de que la ruta no representi 
     * cap carpeta existent
     */
    @Override
    public void entraA(String nomCarpeta) throws GestioFitxersException{
        File file = new File(carpetaDeTreball, nomCarpeta);
        //es controla que el nom correspongui a una carpeta existent
        if(!file.isDirectory()){
            throw new GestioFitxersException("Error. S'ha trobat " 
                                        + file.getAbsolutePath() 
                                        + " quan s'eperava un directori");
        }
        //es controla que es tinguin permisos per llegir la carpeta
        if(!file.canRead()){
            throw new GestioFitxersException("Alerta. No es pot  accedir a " 
                                        + file.getAbsolutePath() 
                                        + ". No teniu prou permisos");
        }
        //nova assignció de la carpeta de treball
        carpetaDeTreball=file;
        //es requereix actualitzar el contingut
        actualitza();
    }
    
    @Override
    public void creaCarpeta(String nomCarpeta) throws GestioFitxersException{
        File file = new File(carpetaDeTreball, nomCarpeta);
        if(!carpetaDeTreball.canWrite()){
            throw new GestioFitxersException("Error. No s'ha pogut crear "  
                                        + nomCarpeta + ". No teniu suficients "
                                        + "permisos");
        }
        if(file.exists()){
            throw new GestioFitxersException("Error. No s'ha pogut crear. Ja "
                                            + "exsiteix un fitxer o caprpeta "
                                            + "amb el nom " + nomCarpeta);
        }
        //file.mkdir();
        if(!file.mkdir()){
            throw new GestioFitxersException("Error. No s'ha pogut crear "  
                                        + nomCarpeta + ".");
        }
        actualitza();
    }
    
    @Override
    public void creaFitxer(String nomFitxer) throws GestioFitxersException {
        File file = new File(carpetaDeTreball, nomFitxer);
        if(!carpetaDeTreball.canWrite()){
            throw new GestioFitxersException("Error. No s'ha pogut crear "  
                                        + nomFitxer + ". No teniu suficients "
                                        + "permisos");
        }
        if(file.exists()){
            throw new GestioFitxersException("Error. No s'ha pogut crear. Ja "
                                            + "exsiteix un fitxer o caprpeta "
                                            + "amb el nom " + nomFitxer);
        }
        try {
            if(!file.createNewFile()){
                throw new GestioFitxersException("Error. No s'ha pogut crear "  
                                            + nomFitxer + ".");
            }
        } catch (IOException ex) {
            throw new GestioFitxersException("S'ha produit un error d'entrada"
                    + " o sortida: '" + ex.getMessage() + "'", ex);
        }
        actualitza();
    }
    

    @Override
    public void reanomena(String nom, String nomNou) throws GestioFitxersException{
        File file = new File(carpetaDeTreball, nom);
        File fileNou = new File(carpetaDeTreball, nomNou);
        if(!carpetaDeTreball.canWrite()){
            throw new GestioFitxersException("Error. No s'ha pogut eliminar "  
                                        + nom + ". No teniu suficients "
                                        + "permisos");
        }
        if(!file.exists()){
            throw new GestioFitxersException("Error. No es pot fer el canvi de nom, "
                                            + nom +  " no existeix.");
        }
        if(!file.renameTo(fileNou)){
            throw new GestioFitxersException("Error. No s'ha pogut canviar de nom "  
                                        + nom + ".");
        }
        actualitza();        
    }
    
    @Override
    public void elimina(String nom) throws GestioFitxersException{
        File file = new File(carpetaDeTreball, nom);
        if(!carpetaDeTreball.canWrite()){
            throw new GestioFitxersException("Error. No s'ha pogut eliminar "  
                                        + nom + ". No teniu suficients "
                                        + "permisos");
        }
        if(!file.exists()){
            throw new GestioFitxersException("Error. S'intenta eliminar "
                                            + nom +  " però no existeix.");
        }
        //file.delete();
        if(!file.delete()){
            if(file.isDirectory() && file.list().length>0){
                throw new GestioFitxersException("Error. No s'ha pogut eliminar."
                        + " la carpeta " + nom + "no està buida.");
            }else{
                throw new GestioFitxersException("Error. No s'ha pogut eliminar "  
                                        + nom + ".");
            }
        }
        actualitza();

    }

    @Override
    public void setFormatContingut(FormatVistes format) {
        formatVistes = format;
        
        //assegurem que només hi hagi una columne en aquest format
        if(formatVistes==FormatVistes.DETALLS){
            columnes=titolColumnesDetall.length;
        }else{
            columnes = columnesBase;        
        }
        actualitza();
    }

    @Override
    public FormatVistes getFormatContingut() {
        return formatVistes;
    }    
    
    @Override
    public String[] getTitolColumnes() {
        String[] ret = null;
        if(formatVistes==FormatVistes.DETALLS){
            ret = titolColumnesDetall;
        }
        return ret;
    }

    @Override
    public TipusOrdre getOrdenat() {
        return ordenat;
    }

    @Override
    public void setOrdenat(TipusOrdre ordenat) {
        if(ordenat==TipusOrdre.DESORDENAT
                || ordenat==TipusOrdre.NOM && formatVistes==FormatVistes.NOMS
                || formatVistes!=FormatVistes.NOMS){
            this.ordenat=ordenat;
            actualitza();
        }
    }

    @Override
    public boolean getMostrarOcults() {
       return mostrarOcults;
    }

    @Override
    public void setMostrarOcults(boolean ocults) {
        this.mostrarOcults=ocults;
        actualitza();
    }

    @Override
    public String getInformacio(String nom) throws GestioFitxersException {
        ByteFormat byteFormat= new ByteFormat("#,###.0", ByteFormat.BYTE);
        StringBuilder strBuilder = new StringBuilder();
        File file = new File(carpetaDeTreball, nom);
        //Es controla que existeixi l'element a analitzar
        if(!file.exists()){
            throw new GestioFitxersException("Error. No es pot obtenir "
                                        + " informació " + "de " + nom 
                                        +  ", no existeix.");
        }
        //es controla que es tinguin permisos per llegir la carpeta
        if(!file.canRead()){
            throw new GestioFitxersException("Alerta. No es pot  accedir a " 
                                        + nom + ". No teniu prou permisos");
        }
        //S'escriu el títol
        strBuilder.append("INFORMACIÓ DEL SISTEMA");
        strBuilder.append("\n\n");
        //S'afegeix el nom
        strBuilder.append("Nom: ");
        strBuilder.append(nom);
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
        //Afegim la ubicació
        strBuilder.append("Ubicació: ");
        /* Cal posar el try per exigències del llenguatge, però no controlarem
         * aquest error doncs sabem que mai es produirà. Si hem arribat fins aquí és
         * que l'adreça és bona*/
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
        }
        

        return strBuilder.toString();        
    }

    @Override
    public long getUltimaModificacio(String nom) throws GestioFitxersException {
        File file = new File(carpetaDeTreball, nom);
        
        if(!file.exists()){
            throw new GestioFitxersException("Error. No es pot obtenir informació de "
                                            + nom +  ", no existeix.");
        }
        return file.lastModified();
    }
    
    @Override
    public void setUltimaModificacio(String nom, long dataIHora) throws GestioFitxersException {
        File file = new File(carpetaDeTreball, nom);

        if(!file.exists()){
            throw new GestioFitxersException("Error. No es pot modificar "
                                            + nom +  ", no existeix.");
        }
        file.setLastModified(dataIHora);
    }
    
    @Override
    public boolean esPotLlegir(String nom) throws GestioFitxersException {
        File file = new File(carpetaDeTreball, nom);
        
        if(!file.exists()){
            throw new GestioFitxersException("Error. No es pot obtenir informació de "
                                            + nom +  ", no existeix.");
        }
        return file.canRead();
    }

    @Override
    public boolean esPotEscriure(String nom) throws GestioFitxersException {
        File file = new File(carpetaDeTreball, nom);

        if(!file.exists()){
            throw new GestioFitxersException("Error. No es pot obtenir informació de "
                                            + nom +  ", no existeix.");
        }
        return file.canWrite();
    }

    @Override
    public boolean esPotExecutar(String nom) throws GestioFitxersException {
        File file = new File(carpetaDeTreball, nom);

        if(!file.exists()){
            throw new GestioFitxersException("Error. No es pot obtenir informació de "
                                            + nom +  ", no existeix.");
        }
        return file.canExecute();
    }

    @Override
    public void setEsPotLlegir(String nom, boolean permis) throws GestioFitxersException {
        File file = new File(carpetaDeTreball, nom);

        if(!file.exists()){
            throw new GestioFitxersException("Error. No es pot modificar "
                                            + nom +  ", no existeix.");
        }
        file.setReadable(permis);
    }

    @Override
    public void setEsPotEscriure(String nom, boolean permis) throws GestioFitxersException {
        File file = new File(carpetaDeTreball, nom);

        if(!file.exists()){
            throw new GestioFitxersException("Error. No es pot modificar "
                                            + nom +  ", no existeix.");
        }
        file.setWritable(permis);
    }

    @Override
    public void setEsPotExecutar(String nom, boolean permis) throws GestioFitxersException {
        File file = new File(carpetaDeTreball, nom);

        if(!file.exists()){
            throw new GestioFitxersException("Error. No es pot modificar "
                                            + nom +  ", no existeix.");
        }
        file.setExecutable(permis);
    }

    @Override
    public String getEspaiDisponibleCarpetaTreball() {
        ByteFormat format = new ByteFormat("#,##0.00");
		return format.format(carpetaDeTreball.getUsableSpace());
    }
    
    @Override
    public String getEspaiTotalCarpetaTreball() {
        ByteFormat format = new ByteFormat("#,##0.00");
        return format.format(carpetaDeTreball.getTotalSpace());
    }

    /**
     * Actualitza el contingut de la carpeta i el distribueix uniformement en
     * cada columna
     */
    protected void actualitza(){     /*CANVI PROTECTEC*/   
        if(formatVistes == FormatVistes.NOMS){
            actualitzaNoms();
        }else if(formatVistes==FormatVistes.CARPETES_I_FITXERS){
            actualitzaNomsICategories();
        }else if(formatVistes==FormatVistes.DETALLS){
            actualitzaDetalls();
        }        
    }
    
    /**
     * Actualitza el contingut de la carpeta i el distribueix uniformement en
     * cada columna. Distingeix entre carpetes i fitxers, marcant les carpetes 
     * amb un signe al final del nom.
     */
    private void actualitzaDetalls(){
        File[] fitxers;
        if(mostrarOcults){//obtenir els fitxers
            fitxers = carpetaDeTreball.listFiles();
        }else{
            fitxers = carpetaDeTreball.listFiles((FileFilter)filtreFitxersOcults);
        }
        //calcular el nombre de files necessari
        files = fitxers.length;
        //ordenarció del contingut
        if(ordenat!=TipusOrdre.DESORDENAT){
            Arrays.sort(fitxers, comparadorDeFile);
        }
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
            contingut[i][0]= new EntradaTaula(te, fitxers[i].getName()+sufix);
            contingut[i][1]= new EntradaTaula(ted, String.valueOf(mida));
            contingut[i][2]= new EntradaTaula(te, date.toString());
        }        
    }
    
    /**
     * Actualitza el contingut de la carpeta i el distribueix uniformement en
     * cada columna. Distingeix entre carpetes i fitxers, marcant les carpetes 
     * amb un signe al final del nom.
     */
    private void actualitzaNomsICategories(){
        File[] fitxers;
        if(mostrarOcults){//obtenir els fitxers
            fitxers = carpetaDeTreball.listFiles();
        }else{
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
    }

    /**
     * Actualitza el contingut de la carpeta i el distribueix uniformement en
     * cada columna
     */
    /* canvi de nom del mètode public actualitza al mètode privat actualitzaNoms */
    private void actualitzaNoms(){
        String[] fitxers ; //obtenir els noms
        if(mostrarOcults){
            fitxers = carpetaDeTreball.list();
        }else{
            fitxers = carpetaDeTreball.list(filtreFitxersOcults);
        }
        //calcular el nombre de files necessari
        files = fitxers.length / columnes;         
        if(files*columnes < fitxers.length){
            files++; //si hi ha residu necessitem un fila més
        }        
        //ordenarció del contingut
        if(ordenat!=TipusOrdre.DESORDENAT){
            Arrays.sort(fitxers, String.CASE_INSENSITIVE_ORDER);
        }
        //dimensionar la matriu contingut d'acord als resultats 
        contingut = new String[files][columnes];
        //Omplir el contingut amb els noms dels elements de la carpeta de treball
        for(int i=0; i<columnes; i++){
            for(int j=0; j<files; j++){
                int ind = j*columnes+i;
                if(ind<fitxers.length){
                    contingut[j][i]=fitxers[ind];
                }else{
                    contingut[j][i]="";
                }
            }
        }        
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
    
    private class ComparadorDeFile implements Comparator<File>{
        @Override
        public int compare(File t, File t1) {
            int ret;
            if(t.isDirectory() && t1.isDirectory() || t.isFile() && t1.isFile()){
                if(ordenat==TipusOrdre.NOM){
                    ret = t.getName().compareToIgnoreCase(t1.getName());
                }else if(ordenat==TipusOrdre.MIDA){
                    if(t.isDirectory()){
                        ret = t.getName().compareToIgnoreCase(t1.getName());
                    }else{
                        ret = new Long(t.length()).compareTo(t1.length());
                    }
                }else if(ordenat==TipusOrdre.DATA_MODIFICACIO){
                    ret = new Long(t.lastModified()).compareTo(t1.lastModified());
                }else{
                    ret=-1;
                }                
            }else if(t.isDirectory()){
                ret=-1;
            }else{
                ret=1;
            }
            return ret;
        }

    }
}