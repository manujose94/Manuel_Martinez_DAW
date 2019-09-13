package UtilitatCompararDirectoris;


	public class ResultatComparacio {
		private String nomFitxer;
		private ValorComparacio valorComparacio;
		/**ResultatComparacio és una classe que per­met 
		 * emma­gat­ze­mar el nom d’un fit­xer i 
		 * un valor indi­cant si exis­teix el mateix  
		 * fit­xer en amb­dues car­pe­tes o si només exis­teix en una d’elles **/
	 
	public ResultatComparacio(String nomFitxer,ValorComparacio valorComparacio) {
			this.nomFitxer = nomFitxer;
			this.valorComparacio = valorComparacio;
			}
	 
			
			public ResultatComparacio() {
			
			}


			/**String getNomFitxer(). Aquest mètode retorna el nom relatiu de l’element que s’intenta 
			 * determinar si existeix en ambdós costats i si són idèntics. **/
			public String getNomFitxer() {
				return nomFitxer;
			}
	 
			public ValorComparacio getValorComparacio() {
				return valorComparacio;
	}
			public void imprimir(){
				System.out.println(this.nomFitxer+" -> "+this.valorComparacio+"\n");
			}
			
			public enum ValorComparacio {
				IGUALS, FALTA_EN_1, FALTA_EN_2,
				MES_NOU_EN_1, MES_NOU_EN_2;
			}
}

