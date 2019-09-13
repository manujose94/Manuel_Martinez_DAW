package ioc.dam.m6.exemples.gestiofitxers;



import java.io.File;


public class main {

	private static final FormatVistes Formatvistes = null;

	public static void main(String[] args) throws GestioFitxersException  {
		// TODO Auto-generated method stub
		String rutaInicial = File.listRoots()[0].toString();
		try {
			GestioFitxerImpl GestioFitxerImpl = new GestioFitxerImpl();
			GestioFitxerImpl.setAdrecaCarpeta(rutaInicial);
			GestioFitxerImpl.setOrdenat(TipusOrdre.NOM);
			GestioFitxerImpl.setMostrarOcults(false);
			GestioFitxerImpl.setFormatContingut(Formatvistes.CARPETES_I_FITXERS);
			new MainGUI(GestioFitxerImpl).setVisible(true);
		} catch (GestioFitxersException ex ){
			ex.printStackTrace();
		}
	}

}




