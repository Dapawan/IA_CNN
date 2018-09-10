package neurone;

import java.util.ArrayList;

public class Couche_neuronale {

	public ArrayList<Neurone> neuroneListe = new ArrayList<>();
	
	public Couche_neuronale(int nbr_couche,int nbr_neurone_par_couche) {
	// TODO Auto-generated constructor stub
		
		System.out.println("***Création de la structure neuronale***");
		
		for(int a = 0; a < nbr_couche; a++)
		{
			for(int i = 0; i < nbr_neurone_par_couche; i++)
			{
				System.out.println("Neurone n° " + (neuroneListe.size()));
				
				this.neuroneListe.add(new Neurone(nbr_neurone_par_couche));
			}
		}
		
		System.out.println("***Fin de création");
	}
}
