package neurone;

import java.util.ArrayList;

public class Couche_neuronale {

	public ArrayList<Neurone> neuroneListe = new ArrayList<>();
	public int nbr_neurone_par_couche;
	public int nbr_couche;
	public Poisson poisson;
	
	public Double poids[][];
	
	public Couche_neuronale(int nbr_couche,int nbr_neurone_par_couche) {
	// TODO Auto-generated constructor stub
		
		//On mémorise
		this.nbr_neurone_par_couche = nbr_neurone_par_couche;
		this.nbr_couche = nbr_couche;
		
		//System.out.println("***Création de la structure neuronale***");
		
		for(int a = 0; a < nbr_couche; a++)
		{
			for(int i = 0; i < nbr_neurone_par_couche; i++)
			{
				//System.out.println("Neurone n° " + (neuroneListe.size()));
				
				this.neuroneListe.add(new Neurone(nbr_neurone_par_couche));
			}
		}
		
		//System.out.println("***Fin de création");
		
		
		/*
		 * Création du tableau de poids des neurones
		 */
		
		this.poids = new Double[this.nbr_couche][2*this.nbr_neurone_par_couche];
		
		/*
		 * Remplissage (init) des poids
		 */
		refresh_tableau_poids();
	}
	
	/*
	 * 
	 * A finir !!! 
	 */
	public void refresh_tableau_poids()
	{
		float index_couche = 0;
		int compteur = 0;
		
		for(Neurone neurone : this.neuroneListe)
		{
			for(int index_neurone_par_couche = 0; index_neurone_par_couche < neurone.w.length; index_neurone_par_couche ++)
			{
				this.poids[(int)index_couche][index_neurone_par_couche] = neurone.w[index_neurone_par_couche];
			}
			
			index_couche = compteur/this.nbr_neurone_par_couche;
			compteur++;
		}
		
		for(int x = 0; x < this.nbr_couche; x++)
		{
			for(int y = 0; y < 2*this.nbr_neurone_par_couche; y++)
			{
				//System.out.println(" W " + (x+y) + " = " + this.poids[x][y] );
			}
		}
	}
	
	public Couche_neuronale mutation(ArrayList<Couche_neuronale> couche_neuronale_list)
	{
		this.neuroneListe = new ArrayList<>();
		Couche_neuronale couche_neuronale = new Couche_neuronale(nbr_couche, nbr_neurone_par_couche);
		/*
		 * On choisit aléatoirement 2 réseaux de neurones sur les 10
		 * chiffre de 0 à 9
		 */
		int index_reseau_1 = (int) (Math.random() * couche_neuronale_list.size());
		int index_reseau_2 = (int) (Math.random() * couche_neuronale_list.size());
		
		//Neurone aléatoire
		int index_neurone = (int) (Math.random() * (nbr_couche + nbr_neurone_par_couche));
		int bias_ou_poids = 0;//(int) (Math.random() * 2);
		
		/*
		 * On moyenne leurs poids et bias
		 */
		
		for(int a = 0; a < nbr_couche; a++)
		{
			for(int i = 0; i < nbr_neurone_par_couche; i++)
			{
				//On crée la neurone
				this.neuroneListe.add(new Neurone(nbr_neurone_par_couche));
				//On copie la valeur
				this.neuroneListe.get(a+i).w[i] = (couche_neuronale_list.get(index_reseau_1).neuroneListe.get(a+i).w[i] + couche_neuronale_list.get(index_reseau_2).neuroneListe.get(a+i).w[i]) / 2;
				this.neuroneListe.get(a+i).bias[i] = (couche_neuronale_list.get(index_reseau_1).neuroneListe.get(a+i).bias[i] + couche_neuronale_list.get(index_reseau_2).neuroneListe.get(a+i).bias[i]) / 2;				
			
				//Facteur aléatoire
				
				if(index_neurone == (a+i))
				{
					if(bias_ou_poids == 1)
					{
						//modification du bias
						this.neuroneListe.get(a+i).bias[i] += (Math.random());
					}
					else
					{
						//modification du poids
						this.neuroneListe.get(a+i).w[i] += (Math.random());
					}
				}
			}
		}
		couche_neuronale.neuroneListe = this.neuroneListe;
		return couche_neuronale;
	}
	
	public Double[] calcul_sortie_neurones(double entrees[])
	{
		/*
		 * Vecteur de sortie
		 */
		
		Double[] sortie = new Double[2];
		
		//System.out.println("---Calcul des sorties de chaque neurone");
	
		float compteur_couche = 0;
		Neurone neurone;
		
		for(int index = 0; index < neuroneListe.size(); index += 1)
		//On calcul les valeurs de chaques neurones
		//for(Neurone neurone : neuroneListe)
		{
			//System.out.println("Neurone n° " + index + " result");
			
			neurone = neuroneListe.get(index);
			
			//Reset de la valeur
			neurone.result = (double) 0;
			//Dans le cas de la première couche
			if(index < this.nbr_neurone_par_couche)
			{
				//On parcourt toutes les entrées
				for(int i = 0; i < entrees.length; i++)
				{
					//System.out.print("Calcul : " + entrees[i] + " * " + neurone.w[i] + " - " + neurone.bias[i] + " + " + neurone.result);
					neurone.result += (entrees[i] * neurone.w[i]) - neurone.bias[i];
					//System.out.println( " = " + neurone.result);
				}
			}
			else
			{
				//System.out.println("Compteur couche : " + compteur_couche);
				for(int i = 0; i < this.nbr_neurone_par_couche; i++)
				{
					if(index >= 4)
					{
						//System.out.print("Calcul : " + neuroneListe.get(i+(int)compteur_couche+1).result + " * " +  neurone.w[i] + " - " + neurone.bias[i] + " + " + neurone.result);
						neurone.result += (neuroneListe.get(i+(int)compteur_couche+1).result * neurone.w[i]) - neurone.bias[i];
						//System.out.println( " = " + neurone.result);
					}
					else
					{
						//System.out.print("Calcul : " + neuroneListe.get(i+(int)compteur_couche).result + " * " +  neurone.w[i] + " - " + neurone.bias[i] + " + " + neurone.result);
						neurone.result += (neuroneListe.get(i+(int)compteur_couche).result * neurone.w[i]) - neurone.bias[i];
						//System.out.println( " = " + neurone.result);
					}
				}
				compteur_couche += ((float)index % this.nbr_neurone_par_couche);
			}
			
			neurone.result = neurone.sigmoide(neurone.result);
			//System.out.println( "Result = " + neurone.result);
			
			if(index >= 4)
			{
				sortie[index%4] = neurone.result;
			}
			
		}
		
		return sortie;
	}
}
