/**
 * 
 */
package neurone;

/**
 * @author Dapawan
 *
 */
public class main implements Valeurs{

	/**
	 * @param args
	 */
	
	public static IA ia;
	
	public static Fenetre fenetre;
	

	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//On crée notre structure de neurone
		
		
		//Couche_neuronale couche_neuronale = new Couche_neuronale(3, 2);
		
		if(isIA == true)
		{
			ia = new IA();
		}
		else
		{
			//Test du jeux
			fenetre = new Fenetre(nombrePoissons);
			while(true)
			{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				//Gestion des déplacements du poisson user
				fenetre.gestionDeplacementClavier();
				fenetre.gestionDeplacementaleatoirePoisson();
				
				
				fenetre.repaint();
				
			}
		}
		
			
		
		
	}

}

