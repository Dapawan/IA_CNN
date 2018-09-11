/**
 * 
 */
package neurone;

import java.time.format.TextStyle;

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
		
		
		Couche_neuronale couche_neuronale = new Couche_neuronale(3, 2);
		
		int entrees[] = {0,1};
		
		couche_neuronale.calcul_sortie_neurones(entrees);
	}
	
	
	

}

