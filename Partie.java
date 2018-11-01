package Chess;

import java.util.ArrayList;

public class Partie implements Valeurs{
	
	public Partie() 
	{
		creationPiece();
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
