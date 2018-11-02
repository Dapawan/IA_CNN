package Chess;

import java.awt.Graphics;

public class Grille implements Valeurs{

	/*
	 * Permet de dessiner la grille
	 * Fait le lien entre position et dessin
	 */
	
	private Fenetre fenetre;
	
	public int espaceColonne;
	public int espaceLigne;
	
	
	public Grille(Fenetre fenetre) {
		this.fenetre = fenetre;
	}
	
	public void dessin(Graphics g)
	{
		/*
		 * 1. Calculer les espaces colonnes et lignes suivant la taille de la fenetre
		 * 2. Dessiner les traits verticaux --> colonne
		 * 3. Dessiner les traits horizontaux --> ligne
		 */
		
		calculEspaceLigneColonne();
		
		//Colonne
		for(int x = debutPosColonne; x <= ( (nbrColonne) * espaceColonne); x += espaceColonne)
		{
			g.drawLine(x, debutPosLigne, x, fenetre.getHeight());
		}
		//Traçage de la dernière colonne qui se trouve au brod droit de la fenetre
		g.drawLine(fenetre.getWidth() - 20, debutPosLigne, fenetre.getWidth() - 20, fenetre.getHeight());
		
		
		//Ligne
		for(int y = debutPosLigne; y <= ( (nbrLigne) * espaceLigne); y += espaceLigne)
		{
			g.drawLine(debutPosColonne, y, fenetre.getWidth(), y);
		}
		//Traçage de la dernière colonne qui se trouve au brod droit de la fenetre
		g.drawLine(debutPosColonne,fenetre.getHeight() - 45,fenetre.getWidth() , fenetre.getHeight() - 45);
		
	}
	
	public void calculEspaceLigneColonne()
	{
		int longueurDispo = (fenetre.getWidth() - debutPosColonne);
		int hauteurDispo = (fenetre.getHeight() - debutPosLigne);
		
		this.espaceColonne = (longueurDispo / (nbrColonne) );
		this.espaceLigne = (hauteurDispo / nbrLigne);
		
	}

}
