/**
 * 
 */
package neurone;

/**
 * @author Dapawan
 *
 */
public class main {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//On crée notre structure de neurone
		//Ici 1
		
		Neurone neurone = new Neurone();
		
		System.out.println("w = " + neurone.w);
		
		
		//ENTRAINEMENT
		//int entree;
		int result;
		int attendu;
		
		int val_min = -50;
		int val_max = 40;
		int plage = Math.abs(val_min-val_max);
		
		int compteur_juste = 0;
		//Entrainement pour des valeurs
		//entree = 1;
		for(int entree = val_min; entree <= val_max; entree++)
		{
			double net = neurone.w * entree;
			
			//Délimitation
			System.out.println("***");
			
			System.out.println("Entree : " + entree + " Net : " + net);
			
			//Fonction heaviside
			if(net >= 0)
			{
				result = 1;
			}
			else
			{
				result = 0;
			}
			
			System.out.print("Resultat : " + result + " Juste ?");
			
			//Vérification de la justesse
			if( entree >= 2 )
			{
				attendu = 1;
			}
			else
			{
				attendu = 0;
			}
			
			if(result == attendu)
			{
				System.out.println(" VRAI");
				compteur_juste ++;
			}
			else
			{
				System.out.println(" faux");
			}
			
			//Correction
			
			//Si attendu = 0 et resultat = 1 : wi = wi - xi
			//Si attendu = 1 et resultat = 0 : wi = wi + xi
			
			System.out.print("Neurone old : " + neurone.w);
			
			if( (attendu == 0) && (result == 1) )
			{
				neurone.w = neurone.w - entree;
			}
			else if( (attendu == 1) && (result == 0) )
			{
				neurone.w = neurone.w + entree;
			}
			
			System.out.println(" Neurone new : " + neurone.w);
			
			//Délimitation
			System.out.println("***");
		}
		
		//Pourcentage de réussite
		System.out.println("Compteur reussite = " + compteur_juste + "/" + plage);
		System.out.println("% de réussite : " + ((double)compteur_juste/(double)plage)*100 );
	}

}
