package Chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import Chess.Valeurs.Equipe;

public class Partie implements Valeurs,MouseListener{
	
	public Fenetre fenetre = null;
	
	Equipe tourDeJeu = null;
	boolean isFinished = false;
	
	public Partie() 
	{
		creationPiece();
	}
	
	
	public void start()
	{
		if( (tourDeJeu == null) && (isFinished == false) )
		{
			tourDeJeu = Equipe.BLEU;		
		}
		else if(isFinished == false)
		{
			if(pieceJ1.size() == 0)
			{
				/*
				 * J1 a perdu
				 */
				System.out.println("J1 defeated");
				isFinished = true;
			}
			else if(pieceJ2.size() == 0)
			{
				/*
				 * J1 a perdu
				 */
				System.out.println("J2 defeated");
				isFinished = true;
			}
		}
	}
	
	
	public void finish()
	{
		
	}
		
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		/*
		 * On récup le clic souris pour soit sélection piece ou deplacement
		 * Suivant le tour equipe
		 */	
		if(fenetre == null)
		{
			return;
		}
		
		int posPieceX = 0;
		int posPieceY = 0;
		boolean isNewPieceSelected = false;
		
		if( (tourDeJeu == Equipe.BLEU) && (pieceJ1.size() >= 1) && (nbrUser >= 1) )
		{
			for(int index = 0; index < pieceJ1.size(); index++)
			{
				posPieceX = pieceJ1.get(index).posX;
				posPieceY = pieceJ1.get(index).posY;
				
				if(isPieceHere(e.getX(), e.getY(), posPieceX, posPieceY) == true)
				{
					/*
					 * Une piece est sélectionnée
					 */
					fenetre.indicePieceSelectJ1 = index;
					isNewPieceSelected = true;
					break;
				}
			}
			if(isNewPieceSelected == false)
			{						
				for(int newColonneX = 1; newColonneX <= nbrColonne + 1; newColonneX++)
				{
					for(int newLigneY = 1; newLigneY <= nbrLigne + 1; newLigneY++)
					{
						if(pieceJ1.get(fenetre.indicePieceSelectJ1).regleMove(newColonneX, newLigneY) == true)
						{
							if(isCollisionHere(e.getX(),e.getY(),newColonneX,newLigneY) == true)
							{
								/*
								 * On sélectionne un déplacement
								 */
								pieceJ1.get(fenetre.indicePieceSelectJ1).move(newColonneX, newLigneY);
								/*
								 * On termine le tour
								 */
								tourDeJeu = Equipe.ROUGE;
							}
						}
					}
				}
			}
		}
		else if( (tourDeJeu == Equipe.ROUGE) && (pieceJ2.size() >= 1)  && (nbrUser == 2) )
		{
			for(int index = 0; index < pieceJ2.size(); index++)
			{
				posPieceX = pieceJ2.get(index).posX;
				posPieceY = pieceJ2.get(index).posY;
				
				if(isPieceHere(e.getX(), e.getY(), posPieceX, posPieceY) == true)
				{
					/*
					 * Une piece est sélectionnée
					 */
					fenetre.indicePieceSelectJ2 = index;
					isNewPieceSelected = true;
					break;
				}
			}
			if(isNewPieceSelected == false)
			{
				for(int newColonneX = 0; newColonneX <= nbrColonne; newColonneX++)
				{
					for(int newLigneY = 0; newLigneY <= nbrLigne; newLigneY++)
					{
						if(pieceJ2.get(fenetre.indicePieceSelectJ2).regleMove(newColonneX, newLigneY) == true)
						{
							if(isCollisionHere(e.getX(),e.getY(),newColonneX,newLigneY) == true)
							{
								/*
								 * On sélectionne un déplacement
								 */
								pieceJ2.get(fenetre.indicePieceSelectJ2).move(newColonneX, newLigneY);
								/*
								 * On termine le tour
								 */
								tourDeJeu = Equipe.BLEU;
							}
						}
					}
				}
			}
		}
		
	}

	public boolean isPieceHere(int x, int y, int xPiece, int yPiece)
	{
		if( (x >= xPiece) && (x <= xPiece + fenetre.grille.espaceColonne) )
		{
			if( (y >= yPiece) && (y <= yPiece + fenetre.grille.espaceLigne) )
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isCollisionHere(int x, int y, int colonnePieceX, int lignePieceY)
	{
		
		int debutX = (( (colonnePieceX - 1) * fenetre.grille.espaceColonne) + debutPosColonne);
		int finX = debutX + fenetre.grille.espaceColonne;
		
		int debutY = (( (lignePieceY - 1) * fenetre.grille.espaceLigne) + debutPosLigne);
		int finY = debutY + fenetre.grille.espaceLigne;
		/*
		System.out.println("X : " + x + " debutX :" + debutX + "  finX :" + finX);
		System.out.println("Y : " + y + " debutY :" + debutY + "  finY :" + finY);
		*/
		if( (x >= debutX) && (x <= finX) )
		{
			if( (y >= debutY) && (y <= finY) )
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	
	/*
	 * Création des pieces
	 * On parcourt la liste des pieces demandées
	 */
	public void creationPiece()
	{
		Equipe equipe = Equipe.BLEU;
		int colonneX = nbrColonne;
		int ligneY = nbrLigne;
		
		
		for(int i = 0; i < PieceJ1.length; i++)
		{
			switch(PieceJ1[i])
			{
			case CAVALIER:
				break;
			case FOU:
				break;
			case PION:
				pieceJ1.add(new Pion(equipe, colonneX, ligneY));
				break;
			case TOUR:
				break;
			default:
				break;
			
			}
			
			/*
			 * Décalage vers le bas (de haut en bas)
			 */
			if(colonneX-- < 1)
			{
				colonneX = nbrColonne;
				ligneY -= 1;
			}
		}
		
		
		equipe = Equipe.ROUGE;
		colonneX = nbrColonne;
		ligneY = 1;
		
		for(int i = 0; i < PieceJ2.length; i++)
		{
			switch(PieceJ2[i])
			{
			case CAVALIER:
				break;
			case FOU:
				break;
			case PION:
				pieceJ2.add(new Pion(equipe, colonneX, ligneY));
				break;
			case TOUR:
				break;
			default:
				break;
			
			}
			
			/*
			 * Décalage vers le haut (de bas en haut)
			 */
			if(colonneX-- < 1)
			{
				colonneX = nbrColonne;
				ligneY += 1;
			}
		}
	}

}
