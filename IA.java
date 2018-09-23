package neurone;

import java.util.ArrayList;
import java.util.Comparator;

public class IA implements Valeurs{
	
	static Fenetre fenetre;
	static ArrayList<Couche_neuronale> couche_neuronale_liste;
	final static int NBR_POISSONS = nombrePoissons;
	
	public IA() {
		// TODO Auto-generated constructor stub
		lancementIa();
	}
	
	public void lancementIa()
	{
		fenetre = new Fenetre(NBR_POISSONS);
		
		couche_neuronale_liste = new ArrayList<>();
		for(int i = 0; i < NBR_POISSONS; i++)
		{
			couche_neuronale_liste.add(new Couche_neuronale(3, 2));
		}
		Double[] sortie;
		double entrees[] = {0,0};
		int index = 0;
		double new_posX;
		double new_posY;
		
		int min_x = 10000;
		int min_y = 10000;
		
		/*int entrees[] = {0,1};
		Double[] sortie;
		
		sortie = couche_neuronale.calcul_sortie_neurones(entrees);
		
		for(int i = 0; i < sortie.length; i++)
		{
			System.out.println("Sortie n° " + i + " = " + sortie[i]);
		}
		*/
		
		/*
		 * Vue
		 */
		
		
		while(true)
		{
			for(int a = 0; a < 70; a++)
			{
				fenetre.repaint();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for(Couche_neuronale couche_neuronale : couche_neuronale_liste)
				{
					//entrees[0] = //(fenetre.liste_poisson.get(index).posX + fenetre.liste_poisson.get(index).posY)/2000;
					
					for(int i = 0; i < NBR_POISSONS; i++)
					{
						if((min_x + min_y) > (fenetre.liste_poisson.get(i).posX + fenetre.liste_poisson.get(i).posY) && (fenetre.liste_poisson.get(i).vie == true))
						{
							min_x = fenetre.liste_poisson.get(i).posX;
							min_y = fenetre.liste_poisson.get(i).posY;
						}
					}
					
					//entrees[1] = (min_x + min_y)/2000;
					entrees[0] = (min_x + min_y) / 2000.0;
					entrees[1] = (fenetre.liste_poisson.get(index).posX + fenetre.liste_poisson.get(index).posY + fenetre.liste_poisson.get(index).grandeur) / 20.0;

					sortie = couche_neuronale.calcul_sortie_neurones(entrees);
					
					new_posX = (sortie[0]-0.5);
					new_posY = (sortie[1]-0.5);
					
					
					fenetre.liste_poisson.get(index).move(new_posX, new_posY);
					
					
					/*
					 * Gestion collision
					 */
					for(int i = 0; i < NBR_POISSONS; i++)
					{
						if( ((fenetre.liste_poisson.get(index).posX <= fenetre.liste_poisson.get(i).posX + fenetre.liste_poisson.get(i).grandeur) && (fenetre.liste_poisson.get(index).posX + fenetre.liste_poisson.get(index).grandeur) >= (fenetre.liste_poisson.get(i).posX) ) &&
								((fenetre.liste_poisson.get(index).posY <= fenetre.liste_poisson.get(i).posY + fenetre.liste_poisson.get(i).grandeur) && (fenetre.liste_poisson.get(index).posY + fenetre.liste_poisson.get(index).grandeur) >= (fenetre.liste_poisson.get(i).posY) )	&& (i != index) && (fenetre.liste_poisson.get(i).vie == true) && (fenetre.liste_poisson.get(index).vie == true)  )
						{
							//Collision
							fenetre.liste_poisson.get(i).vie = false;
							fenetre.liste_poisson.get(index).grandeur += fenetre.liste_poisson.get(i).grandeur;
							fenetre.liste_poisson.get(index).score += 1;
							//On regarde s'il y a à nouveau une collision
							i = 0;
						}
					}
					
					
					index++;
					
				}
				index = 0;
				min_x = 10000;
				min_y = 10000;
				fenetre.temps_sec += 0.1;
			}
			/*
			 * 1 série effectuée
			 */		
			//On crée une liaison entre poisson et mémoire
			for(int i = 0; i < NBR_POISSONS; i++)
			{
				fenetre.liste_poisson.get(i).couche_neuronale = couche_neuronale_liste.get(i);
			}
			fenetre.liste_poisson.sort(new Comparator<Poisson>() {

				@Override
				public int compare(Poisson o1, Poisson o2) {
					// TODO Auto-generated method stub
					int test = -100;
					if(o2.vie)
					{
						test = 100;
					}
					return (o2.score - o1.score) + (100*test);
				}
			});
			
			/*for(int i = 10; i < NBR_POISSONS; i++)
			{
				fenetre.liste_poisson.remove(10);
			}*/
			int test_index = 0;
			Poisson test = fenetre.liste_poisson.get(test_index);
			while(test.vie == true)
			{
				test_index++;
				test = fenetre.liste_poisson.get(test_index);
			}
			//On arrive au niveau des poissons morts
			for(int i = test_index; i < NBR_POISSONS; i++)
			{
				fenetre.liste_poisson.remove(test_index);
			}
			
			/*
			 * On recopie les mémoires
			 */
			couche_neuronale_liste = new ArrayList<>();
			for(int i = 0; i < test_index; i++)
			{
				couche_neuronale_liste.add(fenetre.liste_poisson.get(i).couche_neuronale);
			}
			
			/*
			//On tri pour ne garder que les meilleurs
			for(int i = 0; i < fenetre.liste_poisson.size(); i++)
			{
				//On enlève les poissons morts
				if( (fenetre.liste_poisson.get(i).vie == false) || (fenetre.liste_poisson.get(i).score == 0))
				{
					couche_neuronale_liste.remove(i);
					fenetre.liste_poisson.remove(i);
				}
			}
			*/
			
			
			//On garde les 10 meilleurs poissons
			//Collections.sort(fenetre.liste_poisson);
			
			
			System.out.println("***Result :");
			float moyenne = 0;
			for(Poisson poisson : fenetre.liste_poisson)
			{
				System.out.println(" " + poisson.score);
				
				moyenne += poisson.score;
			}
			moyenne = moyenne / 10;
			fenetre.moyenne = moyenne;
			//On suppr les poissons
			fenetre.liste_poisson = new ArrayList<Poisson>();
			for(int i = 0; i < NBR_POISSONS; i++)
			{
				fenetre.liste_poisson.add(new Poisson());
			}
			
			//On mémorise le début de l'indice à créer
			index = couche_neuronale_liste.size() + 1;
			Couche_neuronale couche_neuronale_ = new Couche_neuronale(3, 2);
			//On remplit de nouveau
			while(couche_neuronale_liste.size() < NBR_POISSONS)
			{
				couche_neuronale_liste.add(couche_neuronale_.mutation(couche_neuronale_liste));
			}
			
			
			fenetre.temps_sec = 0;
			index = 0;
			
		}
	}


}
