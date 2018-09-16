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
	
		float compteur_couche = 0;
		Neurone neurone;
		
		for(int index = 0; index < neuroneListe.size(); index += 1)
		//On calcul les valeurs de chaques neurones
		//for(Neurone neurone : neuroneListe)
		{
			System.out.println("Neurone n° " + index + " result");
			
			neurone = neuroneListe.get(index);
			
			//Reset de la valeur
			neurone.result = (double) 0;
			//Dans le cas de la première couche
			if(index < this.nbr_neurone_par_couche)
			{
				//On parcourt toutes les entrées
				for(int i = 0; i < entrees.length; i++)
				{
					System.out.print("Calcul : " + entrees[i] + " * " + neurone.w[i] + " - " + neurone.bias[i] + " + " + neurone.result);
					neurone.result += (entrees[i] * neurone.w[i]) - neurone.bias[i];
					System.out.println( " = " + neurone.result);
				}
			}
			else
			{
				System.out.println("Compteur couche : " + compteur_couche);
				for(int i = 0; i < this.nbr_neurone_par_couche; i++)
				{
					if(index >= 4)
					{
						System.out.print("Calcul : " + neuroneListe.get(i+(int)compteur_couche+1).result + " * " +  neurone.w[i] + " - " + neurone.bias[i] + " + " + neurone.result);
						neurone.result += (neuroneListe.get(i+(int)compteur_couche+1).result * neurone.w[i]) - neurone.bias[i];
						System.out.println( " = " + neurone.result);
					}
					else
					{
						System.out.print("Calcul : " + neuroneListe.get(i+(int)compteur_couche).result + " * " +  neurone.w[i] + " - " + neurone.bias[i] + " + " + neurone.result);
						neurone.result += (neuroneListe.get(i+(int)compteur_couche).result * neurone.w[i]) - neurone.bias[i];
						System.out.println( " = " + neurone.result);
					}
				}
				compteur_couche += ((float)index % this.nbr_neurone_par_couche);
			}
			
			neurone.result = neurone.sigmoide(neurone.result);
			System.out.println( "Result = " + neurone.result);
			
			
			
		}
		
		
	}
}
