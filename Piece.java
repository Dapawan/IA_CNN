package Chess;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Piece implements Valeurs{
	
	public TypeDePiece typeDePiece;
	public Equipe equipe;
	
	ArrayList<Piece> pieceJ1 = new ArrayList<>();
	ArrayList<Piece> pieceJ2 = new ArrayList<>();
	
	public int colonneX;
	public int ligneY;
	
	public int posX;
	public int posY;
	
	public int indicePieceAdverseToDel = -1;
	

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
			if( (equipe == Equipe.BLEU) &&
					(isAutrePieceHereJ2(newColonneX,newLigneY,true) == true) )
			{
				pieceJ2.remove(indicePieceAdverseToDel);
			}
			else if( (equipe == Equipe.ROUGE) &&
					(isAutrePieceHereJ1(newColonneX,newLigneY,true) == true) )
			{
				pieceJ1.remove(indicePieceAdverseToDel);
			}
			this.colonneX = newColonneX;
			this.ligneY = newLigneY;
			return true;
		}
		
		return false;
	}
	
	public boolean isAutrePieceHereJ1(int colonneX, int ligneY,boolean isMove)
	{
		int index = 0;
		if(pieceJ1.size() >= 1)
		{
			for(Piece piece : pieceJ1)
			{
				if( (piece.colonneX == colonneX) && (piece.ligneY == ligneY) )
				{
					indicePieceAdverseToDel = index;
					return true;
				}
				index++;
			}
		}
		/*
		 * Default value
		 */
		indicePieceAdverseToDel = -1;
		return false;
	}
	public boolean isAutrePieceHereJ2(int colonneX, int ligneY,boolean isMove)
	{
		int index = 0;
		if(pieceJ2.size() >= 1)
		{
			for(Piece piece : pieceJ2)
			{
				if( (piece.colonneX == colonneX) && (piece.ligneY == ligneY) )
				{
					indicePieceAdverseToDel = index;
					return true;
				}
				index++;
			}
		}
		/*
		 * Default value
		 */
		indicePieceAdverseToDel = -1;
		return false;
	}

	
	
	
	
	
}
