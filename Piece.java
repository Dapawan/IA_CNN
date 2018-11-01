package Chess;

import java.awt.Color;
import java.awt.Graphics;

public class Piece implements Valeurs{
	
	public TypeDePiece typeDePiece;
	public Equipe equipe;
	
	public int colonneX;
	public int ligneY;
	
	public int posX;
	public int posY;
	

	public Piece(TypeDePiece typeDePiece,Equipe equipe,int colonneX,int ligneY) 
	{
		this.equipe = equipe;
		this.typeDePiece = typeDePiece;
		this.colonneX = colonneX;
		this.ligneY = ligneY;
	}

	/*
	 * Fonction à override
	 */
	public boolean regleMove(int newColonneX, int newLigneY)
	{
		return false;
	}
	/*
	 * Dessin de la piece
	 */
	public void dessin(Graphics g, int espaceColonne, int espaceLigne) 
	{
		/*
		 * Transposition de la pos en colonne ligne à un nbr de pixel en x et y
		 */
		this.posX = ( (this.colonneX - 1) * espaceColonne) + debutPosColonne;
		this.posY = ( (this.ligneY - 1) * espaceLigne) + debutPosLigne;
		
		/*
		 * Set la couleur de l'équipe
		 */
		g.setColor(getColorEquipe());
		
		
		
	}
	
	/*
	 * Renvoie la couleur de l'équipe
	 */
	public Color getColorEquipe()
	{
		if(this.equipe == Equipe.BLEU)
		{
			return Color.BLUE;
		}
		else
		{
			return Color.RED;
		}
	}
	
	public boolean move(int newColonneX, int newLigneY)
	{
		/*
		 * On vérifie que le déplacement respecte les règles
		 */
		if(regleMove(newColonneX,newLigneY) == true)
		{
			this.colonneX = newColonneX;
			this.ligneY = newLigneY;
			return true;
		}
		
		return false;
	}

	
	
	
	
	
}
