package enumerados;

public enum OpcionesNumerados {
	MOURE ("1.Metode MOURE ", "-De GestionImpPlus"), //Separamos con comas.
	COPIAR ("2.Metodes COPIAR ", "-De Utilitats"),
	RECODIFICAR ("3.Metodes RECODIFICAR", "-De Utilitats"),
	LECTURA ("4.Metodes LECTURA ", "-De Utilitatas"),
	ESCRITURA ("5.Metodes ESCRITURA ", "-De Utilitatas"),
	COMPARADOR ("6.Metode COMPARADOR CARPETES", "-De DiffFolder-ResultatComparacio"),
	CREADORFITXER ("7.Metode CREADORFITXERS ", "-De Utilitats"),
	CARACTERISTiQES ("8.Metode CARACTERISTIQUES ", "-De GestionImpPlus"),
	OCULTS ("9.Metode LLISTAR_OCULTS ", "-De GestionImpPlus"),
	COPYOTHERFOLDER ("10.Copiar Fitxer a altre directori ", "-De GestionImpPlus"),
	JUGARFICHER ("11.Jugar amb un Ficher ", "-De GestionImp"),
	SORTIDA ("20-->", "EXIT"),
	;	
	
	//Campos tipo constantes.
	private final String Metode;
	private final String Class;
	
	/**
	 * Constructor. Al asigna<rle uno de los valores posibles, el constructor recoge
	 * automáticamenter valores de los campos.
	 * @param color
	 * @param peso
	 */
	OpcionesNumerados(String Metode, String Class){
		this.Metode=Metode;
		this.Class=Class;
	}
	
	//Métodos de la clase tipo Enum.
	public String getMetode(){return this.Metode;}
	public String getClasss(){return this.Class;}

	
}
