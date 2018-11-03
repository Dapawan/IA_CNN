package Chess;

import java.util.ArrayList;

public class GestionIA implements Valeurs{
	
	public ArrayList<IA> listeIA = new ArrayList<>();
	
	private double[] vecteurEntree = new double[nbrEntree];
	private double[] vecteurSortie = new double[nbrEntree];
	
	
	public int timeToWait = 10;//En ms
	

	public GestionIA()
	{
		Partie partie = new Partie();
		partie.creationPiece();
		
		for(int i = 0; i < nbrIA; i++)
		{
			if( (i%2) == 1 )
			{
				listeIA.add(new IA(Equipe.ROUGE));
				for(Piece piece : listeIA.get(i).listePiece)
				{
					piece.pieceJ1 = listeIA.get(i-1).listePiece;
					piece.pieceJ2 = listeIA.get(i).listePiece;
					
				}
				for(Piece piece : listeIA.get(i-1).listePiece)
				{
					piece.pieceJ1 = listeIA.get(i-1).listePiece;
					piece.pieceJ2 = listeIA.get(i).listePiece;
					
				}
			}
			else
			{
				listeIA.add(new IA(Equipe.BLEU));
			}
		}
	}
	
	public void calcul()
	{
			
		int indiceVecteurEntree = 0;
		/*
		 * On calcul les sorties et affecte les déplacements
		 */
		for(int i = 0; i < nbrIA; i++)
		{
			/*
			 * On regarde si l'IA a encore des pieces
			 */
			if(listeIA.get(i).listePiece.size() >= 1)
			{
				if(listeIA.get(i).score >= 1)
				{
					this.timeToWait = 1000;
					System.out.println("REGARDE");
				}
				/*
				 * On parcourt toutes les IA 
				 */
				vecteurEntree = vecteurEntree(listeIA.get(i).equipe, i);
				indiceVecteurEntree = (nbrColonne*nbrLigne) - 1;
				//vecteurEntree[indiceVecteurEntree++] = ;
				
				
				vecteurSortie = listeIA.get(i).reseauNeuronal.result(vecteurEntree);
				/*
				 * 1 action seulement possible
				 * --> on regarde la plus grande valeur
				 */
				double valueMAx = 0;
				int indexValueMax = 0;
				for(int index = 0; index < vecteurSortie.length; index++)
				{
					if(valueMAx < vecteurSortie[index])
					{
						valueMAx = vecteurSortie[index];
						indexValueMax = index;
					}
				}
				/*
				 * On regarde si un déplacement est possible
				 * On extrait de l'indice la colonne et la ligne
				 */
				int colonne_ = 0;
				int ligne_ = 0;
				for(int colonne = 0; colonne < nbrColonne; colonne++)
				{
					for(int ligne = 0; ligne < nbrLigne; ligne++)
					{
						if( (colonne + ligne) == indexValueMax)
						{
							colonne_ = colonne;
							ligne_ = ligne;
							break;
						}
					}
				}
				
				/*
				 * Faire le déplacement
				 */
				boolean movePossible = false;
				Piece piece_ = null;
				for(Piece piece : listeIA.get(i).listePiece)
				{
					if(piece.move(colonne_, ligne_) == true)
					{
						movePossible = true;
						piece_ = piece;
						break;
					}
				}
				/*
				 * Compteur de déplacement impossible
				 */
				if(movePossible == false)
				{
					/*
					 * On arrête la game --> non fonctionnel
					 */
					
					//System.out.println("MOVE IMPOSSIBLE");
					
					//Re - génère un réseau de neurone aléatoire
					listeIA.get(i).genAleatoire();
				}
				else
				{
					/*
					 * On arrête si les déplacements sont identiques
					 */
					listeIA.get(i).deplacementHistorique.add(piece_);
					if(listeIA.get(i).deplacementHistorique.isSameMove() == true)
					{
						/*
						 * Same move
						 * --> on quitte la game
						 */
						System.out.println("SAME MOVE");
						
						//Re - génère un réseau de neurone aléatoire
						listeIA.get(i).genAleatoire();
					}					
					
				}
				
				/*
				 * Dans le cas où on a 2 IA
				 */
				
				
			}
		}
		
		
		
	}
	
	public double[] vecteurEntree(Equipe equipe,int index)
	{
		double[] vecteurEntree = new double[nbrEntree];
		
		double valuePosition = 0.0d;
		Piece piece_ = null;
		
		if(equipe == Equipe.BLEU)
		{
			for(int colonne = 0; colonne < nbrColonne; colonne++)
			{
				for(int ligne = 0; ligne < nbrLigne; ligne++)
				{
					/*
					 * Il ne peut y avoir qu'1 piece à la fois
					 */
					//Adv
					for(Piece piece : listeIA.get(index+1).listePiece)
					{
						if( (piece.colonneX == colonne) && (piece.ligneY == ligne) )
						{
							piece_ = piece;
							break;
						}
					}
					for(Piece piece : listeIA.get(index).listePiece)
					{
						if( (piece.colonneX == colonne) && (piece.ligneY == ligne) )
						{
							piece_ = piece;
							break;
						}
					}
					
					if(piece_ != null)
					{
						/*
						 * Il y a une piece
						 */
						if(piece_.equipe == Equipe.BLEU)
						{
							/*
							 * Plus grosse pondération pour ses propres pièces
							 */
							valuePosition = sigmoideFunction((double)piece_.typeDePiece.ordinal() * poidsPonderationProprePieceIA);
						}
						else
						{
							valuePosition = sigmoideFunction((double)piece_.typeDePiece.ordinal());
						}
					}
					else
					{
						/*
						 * Il n'y a rien 
						 */
						
						valuePosition = 0.0d;
					}
					vecteurEntree[(colonne + ligne)] = valuePosition; 
					
				}
			}
			
			/*
			 * On calcule le score
			 */
			listeIA.get(index).score = refreshScore(listeIA.get(index+1).listePiece);
			
		}
		else
		{
			for(int colonne = 0; colonne < nbrColonne; colonne++)
			{
				for(int ligne = 0; ligne < nbrLigne; ligne++)
				{
					/*
					 * Il ne peut y avoir qu'1 piece à la fois
					 */
					//Adv
					for(Piece piece : listeIA.get(index-1).listePiece)
					{
						if( (piece.colonneX == colonne) && (piece.ligneY == ligne) )
						{
							piece_ = piece;
							break;
						}
					}
					for(Piece piece : listeIA.get(index).listePiece)
					{
						if( (piece.colonneX == colonne) && (piece.ligneY == ligne) )
						{
							piece_ = piece;
							break;
						}
					}
					
					if(piece_ != null)
					{
						/*
						 * Il y a une piece
						 */
						if(piece_.equipe == Equipe.ROUGE)
						{
							/*
							 * Plus grosse pondération pour ses propres pièces
							 */
							valuePosition = sigmoideFunction((double)piece_.typeDePiece.ordinal() * poidsPonderationProprePieceIA);
						}
						else
						{
							valuePosition = sigmoideFunction((double)piece_.typeDePiece.ordinal());
						}
					}
					else
					{
						/*
						 * Il n'y a rien 
						 */
						
						valuePosition = 0.0d;
					}
					vecteurEntree[(colonne + ligne)] = valuePosition; 
					
				}
			}
			
			/*
			 * On reverse le vecteur pour passer à la vue identique au J1
			 * L'indice 15 --> 0 et ainsi de suite 
			 */
			double valueTemp = 0.0d;
			
			for(int i = 0; i < (nbrColonne * nbrLigne) / 2; i++)
			{
				//Mem de la valeur
				valueTemp = vecteurEntree[((nbrColonne * nbrLigne) - 1) - i];
				//Suppr de l'ancienne valeur
				vecteurEntree[((nbrColonne * nbrLigne) - 1) - i] = vecteurEntree[i];
				vecteurEntree[i] = valueTemp;
			}
			
			/*
			 * On calcule le score
			 */
			listeIA.get(index).score = refreshScore(listeIA.get(index-1).listePiece);
			
		}
		
		return vecteurEntree;
	}
	
	public int refreshScore(ArrayList<Piece> pieceAdversaire)
	{
		int resultPoint = 0;
		int pointTot = 0;
		for(Piece piece : pieceJ1)
		{
			switch(piece.typeDePiece)
			{
			case CAVALIER:
				pointTot += 3;
				break;
			case FOU:
				pointTot += 2;
				break;
			case PION:
				pointTot += 1;
				break;
			case TOUR:
				pointTot += 4;
				break;
			default:
				break;
			}
		}
		
		for(Piece piece : pieceAdversaire)
		{
			switch(piece.typeDePiece)
			{
			case CAVALIER:
				resultPoint += 3;
				break;
			case FOU:
				resultPoint += 2;
				break;
			case PION:
				resultPoint += 1;
				break;
			case TOUR:
				resultPoint += 4;
				break;
			default:
				break;
			
			}
		}
		
		return (pointTot - resultPoint);
		
	}
	
	
	public double sigmoideFunction(double value)
	{
		double result = 0.0;
		
		/*
		 * 1/(1+exp(-k*x))
		 */
		result = (1/ (1 + Math.exp(value*-1)) ); //On prend k = 1 pour la fonction
		
		return result;
		
	}

}
