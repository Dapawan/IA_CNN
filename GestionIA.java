package mario;

public class GestionIA implements Valeurs{
	
	private CoucheNeuronale coucheNeuronale;
	private CoucheNeuronale[] meilleursCouche = new CoucheNeuronale[1];
	public Resultat resultat;
	
	private Fenetre_ fenetre;
	
	double entree[] = new double[4];
	double sortie[] = new double[4];
	
	int scoreOld;
	long timeOutSec;
	
	/*
	 * Test 2 IA
	 */
	
	double plusieurIAentree[][] = new double[nbrIA][4];
	double plusieurIAsortie[][] = new double[nbrIA][4];
	
	
	
	/*
	public GestionIA(boolean isPlusieurIA, Fenetre_ fenetre) {
		if(isPlusieurIA == true)
		{
			resultat = new Resultat();
			
			this.fenetre = fenetre;
			
			
		}
	}*/
	
	public void startPlusieursIA() throws CloneNotSupportedException
	{
		int posX = 0;
		int posY = 0;
		Personnage perso;
		CoucheNeuronale coucheNeuronale;
		
		if(fenetre.map.nbrPersoAlive() > 0)
		{
			for(int i = 0; i < nbrIA; i++)
			{
				perso = fenetre.map.persoListe.get(i);
				if(perso.vie == true)
				{
					posX = perso.posX;
					posY = perso.posY;
					
					/*
					 * Recherche du bloc le plus proche
					 */
					Bloc bloc_tempo = new Bloc(-1, -1);
					for(Bloc bloc : fenetre.map.listeBloc)
					{
						if( (posX + perso.longueurPerso) < bloc.posX)
						{
							bloc_tempo = bloc;
							break;
						}
					}
					//Vecteur d'entrée
					plusieurIAentree[i][0] = (double) (bloc_tempo.posX - (posX + perso.longueurPerso) );
					plusieurIAentree[i][1] = (double) (bloc_tempo.posY - (posY + perso.hauteurPerso) );
					plusieurIAentree[i][2] = (double) 10.0;
					plusieurIAentree[i][3] = (double) 10.0;
					
					//Gestion des sorties
					coucheNeuronale = (CoucheNeuronale) perso.coucheNeuronale.clone();
					
					plusieurIAsortie[i] = coucheNeuronale.calculSortie(plusieurIAentree[i]);
					
					
					
					//Affecte les déplacements sur les perso
					fenetre.gestionDeplacementIA(plusieurIAsortie[i],perso);
					
					
					/*
					 * Gestion de la progression pour éliminer les perso non fonctionnant
					 */
					
					isInProgress(perso);
				}
				
			}
		}
		else
		{
			//On stocke toutes les IA
			for(int i = 0; i < nbrIA; i++)
			{
				perso = fenetre.map.persoListe.get(i);
				
				
				//On stocke le score dans la classe coucheNeuronale
				perso.coucheNeuronale.score = perso.score;
				//On ajoute ce réseau à la liste des résultats
				resultat.ajout((CoucheNeuronale) perso.coucheNeuronale.clone());
				
				
				if(resultat.CoucheNeuronaleListe.size() >= nbrResultatStocke)
				{
					resultat.CoucheNeuronaleListe.remove(nbrResultatStocke-1);
				}
				
				//On recommence le niveau 
				perso.resetPosInit(fenetre.map);
				
				
				//Met à 0 les valeurs
				perso.chrono.stop();
				perso.chrono.start();
				perso.timeOutSec = 0;
				perso.scoreOld = 0;
			}
			
			if(resultat.CoucheNeuronaleListe.size() > 1)
			{
				System.out.println("Résultat");
				resultat.CoucheNeuronaleListe.sort(resultat);
				for(int a = 0; a < resultat.CoucheNeuronaleListe.size(); a++)
				{
					System.out.println("Score " + resultat.CoucheNeuronaleListe.get(a).score);
				}
				meilleursCouche[0] = (CoucheNeuronale) resultat.CoucheNeuronaleListe.get(0).clone();
				//meilleursCouche[1] = resultat.CoucheNeuronaleListe.get(1);
				
			}
			
			for(int i = 0; i < nbrIA; i++)
			{
				//int diffScore = (meilleursCouche[0].score - perso.coucheNeuronale.score);
				perso = fenetre.map.persoListe.get(i);
				coucheNeuronale = new CoucheNeuronale();
				perso.coucheNeuronale = new CoucheNeuronale();
				coucheNeuronale.mutation(meilleursCouche.clone(),perso.coucheNeuronale);
				
			}
		}
		
		fenetre.coucheNeuronale = (CoucheNeuronale) fenetre.map.persoListe.get(0).coucheNeuronale.clone();
		
		
		
		
		
	}
	
	public void isInProgress(Personnage perso) throws CloneNotSupportedException
	{
		if(perso.chrono != null)
		{
			//On regarde si le score augmente
			if(perso.scoreOld < perso.score)
			{
				perso.scoreOld = perso.score;
				//On rafrachit le temps du timeout
				perso.timeOutSec = perso.chrono.getTime();
			}
			else
			{
				//Le score stagne ou diminue
				if( ((perso.chrono.getTime() - perso.timeOutSec) >= 2000))
				{
					perso.vie = false;
				}
			}
		}
	}
	
	/*
	 * 
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unused")
	public GestionIA(Fenetre_ fenetre) {
		this.fenetre = fenetre;
		
		if(isPlusieurIa == false)
		{
			coucheNeuronale = new CoucheNeuronale();
		}
		
		resultat = new Resultat();
	}
	
	public void startUneIA() throws CloneNotSupportedException
	{
		if(fenetre.chrono != null)
		{
			//On regarde si le score augmente
			if( (scoreOld < fenetre.map.perso.score) && (fenetre.map.perso.vie != false))
			{
				scoreOld = fenetre.map.perso.score;
				//On rafrachit le temps du timeout
				timeOutSec = fenetre.chrono.getTime();
			}
			else
			{
				//Le score stagne ou diminue
				if( ((fenetre.chrono.getTime() - timeOutSec) >= 2000) || (fenetre.map.perso.vie == false))
				{
					//On stocke le score dans la classe coucheNeuronale
					coucheNeuronale.score = fenetre.map.perso.score;
					//On ajoute ce réseau à la liste des résultats
					resultat.ajout((CoucheNeuronale) coucheNeuronale.clone());
					
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
						int diffScore = (meilleursCouche[0].score - coucheNeuronale.score);
						coucheNeuronale = new CoucheNeuronale();
						coucheNeuronale = (CoucheNeuronale) coucheNeuronale.mutation(meilleursCouche.clone(), diffScore ).clone();
					}
					else
					{
						//On crée un nouveau réseau de neurone
						coucheNeuronale = new CoucheNeuronale();
					}
					if(resultat.CoucheNeuronaleListe.size() > 6)
					{
						resultat.CoucheNeuronaleListe.remove(5);
					}
					
					//On recommence le niveau 
					fenetre.map.perso.vie = true;
					fenetre.map.perso.posX = 0;
					fenetre.map.perso.posY = (fenetre.map.listeBloc.get(0).posY - fenetre.map.perso.hauteurPerso - 2);
					//Évite de pouvoir sauter au début
					fenetre.map.perso.isJumping = true;
					
					
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
		
		fenetre.coucheNeuronale = coucheNeuronale;
		
		fenetre.gestionDeplacementIA(sortie);
	}

}
