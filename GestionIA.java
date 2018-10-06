package mario;

public class GestionIA {
	
	private CoucheNeuronale coucheNeuronale;
	private CoucheNeuronale[] meilleursCouche = new CoucheNeuronale[1];
	public Resultat resultat;
	
	private Fenetre_ fenetre;
	
	double entree[] = new double[4];
	double sortie[] = new double[4];
	
	int scoreOld = 0;
	long timeOutSec = 0;
	
	
	public GestionIA(Fenetre_ fenetre) {
		this.fenetre = fenetre;
		
		coucheNeuronale = new CoucheNeuronale();
		
		resultat = new Resultat();
	}
	
	public void start() throws CloneNotSupportedException
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
