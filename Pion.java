package Chess;

import java.awt.Graphics;

public class Pion extends Piece implements Valeurs{

	public Pion(Equipe equipe,int colonneX, int ligneY) 
	{
		super(TypeDePiece.PION,equipe, colonneX, ligneY);
	}

	
	@Override
	public boolean regleMove(int newColonneX, int newLigneY)
	{		
		int diffAbsColonneX = Math.abs(newColonneX - this.colonneX);
		
		int diffAbsLigneY = Math.abs(newLigneY - this.ligneY);
		
		/*
		 * On ne peut que se déplacer d'1 case dans une direction à la fois
		 */
		if( ((diffAbsColonneX == 1) && (diffAbsLigneY == 0)) ^ ((diffAbsLigneY == 1) && (diffAbsColonneX == 0)) )
		{
			if( ((newColonneX >= 1) && (newColonneX <= nbrColonne))
					&& ((newLigneY >= 1) && (newLigneY <= nbrLigne)) )
			{
				if(isAutrePieceHere(newColonneX, newLigneY) == true)
				{
					return false;
				}
				return true;
			}
		}
			
		return false;
	}
	
	@Override
	public void dessin(Graphics g, int espaceColonne, int espaceLigne) {
		super.dessin(g, espaceColonne, espaceLigne);
		
		g.fillOval(posX, posY, espaceColonne, espaceLigne);
	}

}
