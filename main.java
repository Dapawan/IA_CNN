package mario;

public class main implements Valeurs{

	private static Fenetre_ fenetre;
	private static CoucheNeuronale coucheNeuronale;
	
	private static Resultat resultat;
	private static CoucheNeuronale[] meilleursCouche = new CoucheNeuronale[1];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Double entree[] = new Double[4];
		Double sortie[];
		
		int scoreOld = 0;
		long timeOutSec = 0;
		
		coucheNeuronale = new CoucheNeuronale();
		
		resultat = new Resultat();
		
		fenetre = new Fenetre_();
		while(fenetre.map.stopGame == false)
		{
			if(fenetre.chrono != null)
			{
				//On regarde si le score augmente
				if(scoreOld < fenetre.map.perso.score)
				{
					scoreOld = fenetre.map.perso.score;
					//On rafrachit le temps du timeout
					timeOutSec = fenetre.chrono.getTime();
				}
				else
				{
					//Le score stagne ou diminue
					if( (fenetre.chrono.getTime() - timeOutSec) >= 2000)
					{
						//On stocke le score dans la classe coucheNeuronale
						coucheNeuronale.score = fenetre.map.perso.score;
						//On ajoute ce réseau à la liste des résultats
						resultat.ajout(coucheNeuronale);
						
						if(resultat.CoucheNeuronaleListe.size() > 1)
						{
							System.out.println("Résultat");
							resultat.CoucheNeuronaleListe.sort(resultat);
							for(int i = 0; i < resultat.CoucheNeuronaleListe.size(); i++)
							{
								System.out.println("Score " + resultat.CoucheNeuronaleListe.get(i).score);
							}
							meilleursCouche[0] = resultat.CoucheNeuronaleListe.get(0);
							//meilleursCouche[1] = resultat.CoucheNeuronaleListe.get(1);
							
							coucheNeuronale.mutation(meilleursCouche, (meilleursCouche[0].score - coucheNeuronale.score) );
						}
						if(resultat.CoucheNeuronaleListe.size() > 6)
						{
							resultat.CoucheNeuronaleListe.remove(5);
						}
						
						//On recommence le niveau 
						fenetre.map.perso.posX = 0;
						fenetre.map.perso.posY = (fenetre.map.listeBloc.get(0).posY - jumpY);
						//Évite de pouvoir sauter au début
						fenetre.map.perso.isJumping = true;
						
						
						//On crée un nouveau réseau de neurone
						coucheNeuronale = new CoucheNeuronale();
						
						//Met à 0 les valeurs
						fenetre.chrono.stop();
						fenetre.chrono.start();
						timeOutSec = 0;
						scoreOld = 0;
					}
				}
			}
			
			
			/*
			 * Bloc le plus proche
			 */
			Bloc bloc_tempo = new Bloc(-1, -1);
			for(Bloc bloc : fenetre.map.listeBloc)
			{
				if( (fenetre.map.perso.posX + fenetre.map.perso.longueurPerso) < bloc.posX)
				{
					bloc_tempo = bloc;
					break;
				}
			}
			
			//System.out.println("PosIA : " + (fenetre.map.perso.posX + fenetre.map.perso.longueurPerso) + " PosBloc : " + bloc_tempo.posX);
			entree[0] = (double) (bloc_tempo.posX - (fenetre.map.perso.posX + fenetre.map.perso.longueurPerso) );
			entree[1] = (double) (bloc_tempo.posY - (fenetre.map.perso.posY + fenetre.map.perso.hauteurPerso) );
			
			entree[2] = 0.0;
			entree[3] = 0.0;
			
			//entree[2] = (double) bloc_tempo.posX;
			//entree[3] = (double) bloc_tempo.posY;
			
			sortie = coucheNeuronale.calculSortie(entree);
			
			fenetre.gestionDeplacementIA(sortie);
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//fenetre.gestionDeplacementClavier();
			fenetre.repaint();
		}
		
		//GAME FINISHED
		System.out.println("****Résultats*****");
		System.out.println("");
		System.out.println(resultat.CoucheNeuronaleListe.get(0).toString());
		
		
	}

}
