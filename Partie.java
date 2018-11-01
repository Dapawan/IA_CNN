package Chess;

import java.util.ArrayList;

public class Partie implements Valeurs{
	
	public Partie() 
	{
		creationPiece();
	}
	
	
	/*
	 * Cr�ation des pieces
	 * On parcourt la liste des pieces demand�es
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
			 * D�calage vers le bas (de haut en bas)
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
			 * D�calage vers le haut (de bas en haut)
			 */
			if(colonneX-- < 1)
			{
				colonneX = nbrColonne;
				ligneY += 1;
			}
		}
	}

}
