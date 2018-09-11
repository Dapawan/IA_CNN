package neurone;

import java.util.ArrayList;

public class Couche_neuronale {

	public ArrayList<Neurone> neuroneListe = new ArrayList<>();
	public int nbr_neurone_par_couche;
	
	public Couche_neuronale(int nbr_couche,int nbr_neurone_par_couche) {
	// TODO Auto-generated constructor stub
		
		//On mémorise
		this.nbr_neurone_par_couche = nbr_neurone_par_couche;
		
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
	
	public void calcul_sortie_neurones(int entrees[])
	{
		System.out.println("---Calcul des sorties de chaque neurone");
	
		int compteur = 0;
		
		//On calcul les valeurs de chaques neurones
		for(Neurone neurone : neuroneListe)
		{
			//Reset de la valeur
			neurone.result = (double) 0;
			//Dans le cas de la première couche
			if(compteur < this.nbr_neurone_par_couche)
			{
				for(int i = 0; i < entrees.length; i++)
				{
					neurone.result += (entrees[i] * neurone.w[i]) - neurone.bias[i];
				}
			}
			else
			{
				for(int i = 0; i < this.nbr_neurone_par_couche; i++)
				{
					neurone.result += (neuroneListe.get(compteur+i-this.nbr_neurone_par_couche).result * neurone.w[i]) - neurone.bias[i];
				}
			}
			
			//neurone.result = neurone.sigmoide(neurone.result);
			
			System.out.println("Neurone n° " + compteur + " result");
			System.out.println(neurone.result);
			
			//On parcourt toutes les neurones
			compteur++;
		}
		
		
	}
}
