package Chess;

public class DeplacementHistorique implements Valeurs{

	public Piece piece;
	/*
	 * Enreg de 2 deplacements
	 */
	public int listeColonne[] = new int[nbrSameMove];
	public int listeLigne[] = new int[nbrSameMove];
	public int index = 0;
	public boolean enregReady = false;
	
	public DeplacementHistorique() 
	{
		
	}
	
	/*
	 * On ajoute le depl de la piece si c'est la même que la précedente
	 */
	public void add(Piece piece)
	{
		if(this.piece == null)
		{
			this.piece = piece;
		}
		else
		{
			/*
			 * On a déjà une piece
			 */
			if(this.piece.typeDePiece.compareTo(piece.typeDePiece) == 0)
			{
				/*
				 * Même piece
				 * On add le move
				 */
				listeColonne[index] = piece.colonneX;
				listeLigne[index] = piece.ligneY;
				if(index++ >= (nbrSameMove - 1) )
				{
					index = 0;
					enregReady = true;
				}
				
				
			}
		}
	}
	
	public boolean isSameMove()
	{
		/*
		 *On compare tous les move enreg
		 */
		int compteurSame = 0;
		if(enregReady == true)
		{
			for(int i = 0; i < (nbrSameMove - 1); i++)
			{
				if(listeColonne[i] == listeColonne[i + 1])
				{
					if(listeLigne[i] == listeLigne[i + 1])
					{
						compteurSame++;
					}
					else
					{
						break;
					}
				}
			}
			if(compteurSame == (nbrSameMove - 1) )
			{
				return true;
			}
		}
		return false;
	}

}
