package Chess;

import java.util.ArrayList;

public class GestionIA implements Valeurs{
	
	private ArrayList<IA> listeIA = new ArrayList<>();
	
	private double[] vecteurEntree = new double[nbrEntree];
	private double[] vecteurSortie = new double[nbrEntree];
	

	public GestionIA()
	{
		
		for(int i = 0; i < nbrIA; i++)
		{
			if( (i%2) == 1 )
			{
				listeIA.add(new IA(Equipe.ROUGE));
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
				valueTemp = vecteurEntree[(nbrColonne * nbrLigne)-i];
				//Suppr de l'ancienne valeur
				vecteurEntree[(nbrColonne * nbrLigne)-i] = vecteurEntree[i];
				vecteurEntree[i] = valueTemp;
			}
			
			
		}
		
		return vecteurEntree;
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
