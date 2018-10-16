package mario;

public class GestionIA implements Valeurs{
	
	private CoucheNeuronale coucheNeuronale;
	private CoucheNeuronale[] meilleursCouche = new CoucheNeuronale[1];
	public Resultat resultat;
	
	private Fenetre_ fenetre;
	
	double entree[] = new double[4];
	double sortie[] = new double[4];
	
	int scoreOld;
	int compteurScoreOld;
	long timeOutSec;
	
	public Ecriture ecriture;
	public Lecture lecture;
	
	
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
	
	@SuppressWarnings("unused")
	public void startPlusieursIA() throws CloneNotSupportedException
	{
		int posX = 0;
		int posY = 0;
		Personnage perso;
		CoucheNeuronale coucheNeuronale;
		
		
		
		if(meilleursCouche[0] != null)
		{
			scoreOld = meilleursCouche[0].score;
		}
		
		if(fenetre.map.nbrPersoAlive() > 0)
		{
			for(int i = 0; i < nbrIA; i++)
			{
				perso = fenetre.map.persoListe.get(i);
				if(perso.vie == true)
				{
					posX = perso.posX;
					posY = perso.posY;
					
					if(posX >= (longueurLevel - (3*longueurBloc)) && (isFocusPlayer == false) )
					{
						//Niveau finit
						System.out.println("**FINISH");
						System.out.println(perso.coucheNeuronale.toString());
						
						for(Personnage perso_ : fenetre.map.persoListe)
						{
							if(perso_.vie == true)
							{
								perso = perso_;
							}
						}
						ecriture = new Ecriture(perso.coucheNeuronale, "resultatfinal.txt");
						ecriture.start();
						
						
						fenetre.map.stopGame = true;
					}
					
					
					/*
					 * Recherche du bloc le plus proche
					 */
					int index = 0;
					Bloc bloc_tempo = new Bloc(-1, -1);
					for(Bloc bloc : fenetre.map.listeBloc)
					{
						if( (posX + perso.longueurPerso) < bloc.posX)
						{
							bloc_tempo = bloc;
							break;
						}
						index++;
					}
					
					
					//Vecteur d'entrée
					plusieurIAentree[i][0] = (double) (bloc_tempo.posX - (posX) );
					plusieurIAentree[i][1] = (double) (bloc_tempo.posY - (posY + perso.hauteurPerso) );
					plusieurIAentree[i][2] = (double) plusieurIAentree[i][0] + bloc_tempo.longueur;
					plusieurIAentree[i][3] = (double) plusieurIAentree[i][1] + bloc_tempo.hauteur;
					
					/*plusieurIAentree[i][4] = (double) (fenetre.map.listeBloc.get(index).posX - (posX) );
					plusieurIAentree[i][5] = (double) (fenetre.map.listeBloc.get(index).posY - (posY + perso.hauteurPerso) );
					plusieurIAentree[i][6] = (double) plusieurIAentree[i][0] + fenetre.map.listeBloc.get(index).longueur;
					plusieurIAentree[i][7] = (double) plusieurIAentree[i][1] + fenetre.map.listeBloc.get(index).hauteur;
					
					plusieurIAentree[i][8] = (double) (fenetre.map.listeBloc.get(index+1).posX - (posX) );
					plusieurIAentree[i][9] = (double) (fenetre.map.listeBloc.get(index+1).posY - (posY + perso.hauteurPerso) );
					plusieurIAentree[i][10] = (double) plusieurIAentree[i][0] + fenetre.map.listeBloc.get(index).longueur;
					plusieurIAentree[i][11] = (double) plusieurIAentree[i][1] + fenetre.map.listeBloc.get(index).hauteur;
					*/
					
					//Gestion des sorties
					coucheNeuronale = (CoucheNeuronale) perso.coucheNeuronale.clone();
					//coucheNeuronale = new CoucheNeuronale();
					
					plusieurIAsortie[i] = coucheNeuronale.calculSortie(plusieurIAentree[i]);
				
					
					if( (isFocusPlayer == true) && (i == 0) )
					{
						/*
						 * Déplacement USER
						 */
					}
					else
					{
						//Affecte les déplacements sur les perso
						fenetre.gestionDeplacementIA(plusieurIAsortie[i],perso);
					}
					
					
					/*
					 * Gestion de la progression pour éliminer les perso non fonctionnant
					 */
					if( (isFocusPlayer == true) && (i == 0) )
					{
						
					}
					else
					{
						isInProgress(perso);
					}
					
				}
				
			}
			int index = 0;
			perso = fenetre.map.persoListe.get(index++);
			while( (perso.vie != true) && (index < nbrIA))
			{
				perso = fenetre.map.persoListe.get(index++);
			}
			if(index < nbrIA)
			{
				fenetre.coucheNeuronale = (CoucheNeuronale) fenetre.map.persoListe.get(index).coucheNeuronale;
			}
		}
		else
		{
			Personnage perso_;
			
			/*
			System.out.println("AVANT mutation");
			for(int i = 0; i < (nbrIA-1); i++)
			{
				//On regarde s'ils sont différents
				perso = fenetre.map.persoListe.get(i);
				perso_ = fenetre.map.persoListe.get(i+1);
				
				if(perso.coucheNeuronale.neuroneArray.equals(perso_.coucheNeuronale.neuroneArray)==true)
				{
					System.out.println("Perso " + i + " et Perso " + (i+1) + " IDENTIQUES");
				}
				else
				{
					System.out.println("Perso " + i + " et Perso " + (i+1) + " differents");
				}
			}
			*/
			
			//On stocke toutes les IA
			for(int i = 0; i < nbrIA; i++)
			{
				perso = fenetre.map.persoListe.get(i);
				
				
				//On stocke le score dans la classe coucheNeuronale
				perso.coucheNeuronale.score = perso.score;
				//On ajoute ce réseau à la liste des résultats
				resultat.ajout((CoucheNeuronale) perso.coucheNeuronale.clone());
				
				
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
				for(int a = 0; a <= nbrResultatStocke; a++)
				{
					System.out.println("Score " + resultat.CoucheNeuronaleListe.get(a).score);
				}
				meilleursCouche[0] = (CoucheNeuronale) resultat.CoucheNeuronaleListe.get(0).clone();
				
				
				
				//meilleursCouche[1] = resultat.CoucheNeuronaleListe.get(1);
				
			}
			while(resultat.CoucheNeuronaleListe.size() >= nbrResultatStocke)
			{
				resultat.CoucheNeuronaleListe.remove(nbrResultatStocke-1);
			}
			
			
			fenetre.map.compteurGeneration++;
			fenetre.graph.addScore(meilleursCouche[0].score);
			
			/*
			 * Gestion du coeff de mutation
			 */
			if(meilleursCouche[0] != null) 
			{
				if(scoreOld >= meilleursCouche[0].score)
				{
					//Score stagne
					compteurScoreOld++;
					if(compteurScoreOld >= 8)
					{
						System.out.println("RESET DES IA");
					
						for(int i = 0; i < nbrIA; i++)
						{
							perso = fenetre.map.persoListe.get(i);
							perso.coucheNeuronale = new CoucheNeuronale();
						}
						scoreOld = 1000000;//eviter la réentrance
						compteurScoreOld = 0;
						
						/*
						 * Copie des résultats
						 */
						
						ecriture = new Ecriture(meilleursCouche[0], "resultat" + fenetre.map.compteurGeneration + ".txt");
						ecriture.start();
						
						
						while(resultat.CoucheNeuronaleListe.size() > 0)
						{
							resultat.CoucheNeuronaleListe.remove(0);
						}
						
					}
					else
					{
						/*
						 * Stagnation inf au seuil de reset
						 */
						for(int i = 0; i < nbrIA; i++)
						{
							//int diffScore = (meilleursCouche[0].score - perso.coucheNeuronale.score);
							perso = fenetre.map.persoListe.get(i);
							perso.coucheNeuronale = new CoucheNeuronale();
							//coucheNeuronale = new CoucheNeuronale();
							//coucheNeuronale.neuroneArray = resultat.CoucheNeuronaleListe.get(0).neuroneArray.clone();
							//perso.coucheNeuronale = new CoucheNeuronale();
							//coucheNeuronale.mutation(meilleursCouche.clone(),perso.coucheNeuronale);
							perso.coucheNeuronale.mutationBETA(meilleursCouche[0].neuroneArray,fenetre.map.compteurGeneration,((compteurScoreOld+1)*1));
							
						}
					}
				}
				else
				{
					compteurScoreOld = 0;
					System.out.println("Augmentation !");
					
					for(int i = 0; i < nbrIA; i++)
					{
						//int diffScore = (meilleursCouche[0].score - perso.coucheNeuronale.score);
						perso = fenetre.map.persoListe.get(i);
						perso.coucheNeuronale = new CoucheNeuronale();
						//coucheNeuronale = new CoucheNeuronale();
						//coucheNeuronale.neuroneArray = resultat.CoucheNeuronaleListe.get(0).neuroneArray.clone();
						//perso.coucheNeuronale = new CoucheNeuronale();
						//coucheNeuronale.mutation(meilleursCouche.clone(),perso.coucheNeuronale);
						perso.coucheNeuronale.mutationBETA(meilleursCouche[0].neuroneArray,fenetre.map.compteurGeneration,((compteurScoreOld+1)*1));
						
					}
				}
			}
			
			//fenetre.coucheNeuronale = (CoucheNeuronale) resultat.CoucheNeuronaleListe.get(0);
			/*
			System.out.println("APRES mutation");
			for(int i = 0; i < (nbrIA-1); i++)
			{
				//On regarde s'ils sont différents
				perso = fenetre.map.persoListe.get(i);
				perso_ = fenetre.map.persoListe.get(i+1);
				
				if(perso.coucheNeuronale.neuroneArray.equals(perso_.coucheNeuronale.neuroneArray)==true)
				{
					System.out.println("Perso " + i + " et Perso " + (i+1) + " IDENTIQUES");
				}
				else
				{
					System.out.println("Perso " + i + " et Perso " + (i+1) + " differents");
				}
			}*/
			
			
		}
		
		if(resultat != null)
		{
			//fenetre.coucheNeuronale = (CoucheNeuronale) resultat.CoucheNeuronaleListe.get(0);
		}
		
		
		
		
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	boolean isTest = false;
	
	@SuppressWarnings("unused")
	public GestionIA(Fenetre_ fenetre) {
		this.fenetre = fenetre;
		
		if(isPlusieurIa == false)
		{
			coucheNeuronale = new CoucheNeuronale();
			if(ancienResult == false)
			{
				isTest = true;
			}
		}
		
		resultat = new Resultat();
	}
	
	public void startUneIA() throws CloneNotSupportedException
	{
		if(isTest == false)
		{
			isTest = true;
			lecture = new Lecture("resultatfinal");
			coucheNeuronale = lecture.traitement();
		}
		
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
		int index = 0;
		Bloc bloc_tempo = new Bloc(-1, -1);
		for(Bloc bloc : fenetre.map.listeBloc)
		{
			if( (fenetre.map.perso.posX + fenetre.map.perso.longueurPerso) < bloc.posX)
			{
				bloc_tempo = bloc;
				break;
			}
			index++;
		}
		
		//System.out.println("PosIA : " + (fenetre.map.perso.posX + fenetre.map.perso.longueurPerso) + " PosBloc : " + bloc_tempo.posX);
		entree[0] = (double) (bloc_tempo.posX - (fenetre.map.perso.posX + fenetre.map.perso.longueurPerso) );
		entree[1] = (double) (bloc_tempo.posY - (fenetre.map.perso.posY + fenetre.map.perso.hauteurPerso) );
		
		entree[2] = entree[0] + bloc_tempo.longueur;
		entree[3] = entree[1] + bloc_tempo.hauteur;
		
		/*
		 * NEW
		 */
		
		int posX = fenetre.map.perso.posX;
		int posY = fenetre.map.perso.posY;
		Personnage perso = fenetre.map.perso;
		
		//Vecteur d'entrée
		entree[0] = (double) (bloc_tempo.posX - (posX) );
		entree[1] = (double) (bloc_tempo.posY - (posY + perso.hauteurPerso) );
		entree[2] = (double) entree[0] + bloc_tempo.longueur;
		entree[3] = (double) entree[1] + bloc_tempo.hauteur;
		/*
		entree[4] = (double) (fenetre.map.listeBloc.get(index).posX - (posX) );
		entree[5] = (double) (fenetre.map.listeBloc.get(index).posY - (posY + perso.hauteurPerso) );
		entree[6] = (double) entree[0] + fenetre.map.listeBloc.get(index).longueur;
		entree[7] = (double) entree[1] + fenetre.map.listeBloc.get(index).hauteur;
		
		entree[8] = (double) (fenetre.map.listeBloc.get(index+1).posX - (posX) );
		entree[9] = (double) (fenetre.map.listeBloc.get(index+1).posY - (posY + perso.hauteurPerso) );
		entree[10] = (double) entree[0] + fenetre.map.listeBloc.get(index).longueur;
		entree[11] = (double) entree[1] + fenetre.map.listeBloc.get(index).hauteur;
		*/
		
		
		
		
		
		
		//entree[2] = (double) bloc_tempo.posX;
		//entree[3] = (double) bloc_tempo.posY;
		
		sortie = coucheNeuronale.calculSortie(entree);
		
		fenetre.coucheNeuronale = coucheNeuronale;
		
		fenetre.gestionDeplacementIA(sortie,perso);
	}

}
