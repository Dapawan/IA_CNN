package mario;

import java.util.ArrayList;

public class main implements Valeurs{

	private static Fenetre_ fenetre;
	
	private static GestionIA gestionIA;
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		boolean continuer = true;
		
		fenetre = new Fenetre_();
		
		//Relance la game pour amélioration d'efficacite
		while(continuer) {
			
			
		if(optimisationIaBoucleInf == false)
		{
			continuer = false;
		}
		
		
		if( (isIa == true) || (isPlusieurIa == true))
		{
			gestionIA = new GestionIA(fenetre);
			
			if( (isPlusieurIa == true) && (ancienResult == true))
			{
				for(int i = 0; i < nbrIA; i++)
				{
					fenetre.map.persoListe.remove(0);
				}
				for(int i = 0; i < nbrIA; i++)
				{
					fenetre.map.persoListe.add(new Personnage(fenetre.map.listeBloc.get(0),fenetre.map));
				}
				fenetre.map.stopGame = false;
			}
			
			/*
			fenetre.map.persoListe.get(1).posX = fenetre.map.listeBloc.get(2).posX;
			fenetre.map.persoListe.get(1).posY = fenetre.map.listeBloc.get(2).posY - (fenetre.map.persoListe.get(1).hauteurPerso + 10);
			
			
			fenetre.map.persoListe.get(0).posX = fenetre.map.listeBloc.get(4).posX;
			fenetre.map.persoListe.get(0).posY = fenetre.map.listeBloc.get(4).posY - (fenetre.map.persoListe.get(0).hauteurPerso + 10);
			*/
			/*fenetre.map.persoListe.get(1).direction = Direction.RIGHT;
			fenetre.map.persoListe.get(0).direction = Direction.RIGHT;
			
			fenetre.map.persoListe.get(1).directionOld = Direction.INIT;
			fenetre.map.persoListe.get(0).directionOld = Direction.INIT;*/
			//On place un perso immobile au bloc 2
			//fenetre.map.persoListe.add(new Personnage(fenetre.map.listeBloc.get(2),fenetre.map));
		}
		
			while(fenetre.map.stopGame == false)
			{
				/*
				 * Met en pause l'exécution du code avec touche espace
				 */
				if(fenetre.gestionPause()==false)
				{
					if(isIa == true)
					{
						gestionIA.startUneIA();
					}
					else if((isIa == false) && (isPlusieurIa == false))
					{
						fenetre.gestionDeplacementClavier(fenetre.map.perso);
					}
					else if(isPlusieurIa == true)
					{
						if(isFocusPlayer == false) {
						//Plusieur IA
						gestionIA.startPlusieursIA();
						}
						else
						{
							gestionIA.startPlusieursIA();
							fenetre.gestionDeplacementClavier(fenetre.map.persoListe.get(0));
						}
						
						
						
						
						
						//On gère le perso n°1
						
						//fenetre.gestionDeplacementClavier(fenetre.map.persoListe.get(0));
						
					}
					
					if(isPlusieurIa == false)
					{
						
						if( (fenetre.map.perso.isJumping == true) && (fenetre.map.perso.compteur <= compteurMax))
						{
							fenetre.map.perso.compteurSprint -= decrSpeedJump;
							if(fenetre.map.perso.compteurSprint < (vitesseX*multiplicateur))
							{
								fenetre.map.perso.compteurSprint = vitesseX*multiplicateur;
							}
							
							fenetre.map.perso.compteur++;
							fenetre.map.move( fenetre.map.perso.posX, (int)(fenetre.map.perso.posY - 2) );
						}
						else if(fenetre.map.perso.isJumping == false)
						{
							fenetre.map.perso.compteur = 0;
						}
						
						if(fenetre.map.perso.vie == false)
						{
							fenetre.map.perso.resetPosInit(fenetre.map);
						}
						
						fenetre.map.move( fenetre.map.perso.posX, (int)(fenetre.map.perso.posY + 1) ,fenetre.map.perso,Direction.DOWN);
					}
					else if(fenetre.map.stopGame == false && isPlusieurIa == true)
					{
						int posX = 0;
						int posY = 0;
		
		
						for(Personnage perso : fenetre.map.persoListe)
							
							if(perso.vie == true)
							{
								posX = perso.posX;
								posY = perso.posY;
								
								
								if( (perso.isJumping == true) && (perso.compteur <= compteurMax))
								{
									perso.compteurSprint -= decrSpeedJump;
									if(perso.compteurSprint < (vitesseX*multiplicateur))
									{
										perso.compteurSprint = vitesseX*multiplicateur;
									}
									perso.compteur++;
									fenetre.map.move( posX, (posY - 2) ,perso);
								}
								else if(perso.isJumping == false)
								{
									perso.compteur = 0;
								}
								
								posX = perso.posX;
								posY = perso.posY;
								
								fenetre.map.move(posX, (posY + 1),perso, Direction.DOWN);
							}
												
						
					}
				
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//fenetre.gestionDeplacementClavier();
				fenetre.repaint();
				
			}
		}
		
		
	}

}
