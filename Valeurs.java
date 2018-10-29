package Chess;

public interface Valeurs {

	int longueurFenetre = 900;
	int hauteurFenetre = 900;
	
	/*
	 * Neurone
	 */
	double biasMax = 0.05d;
	double weightMax = 0.05d;
	/*
	 * Reseau neuronale
	 */
	char nbrEntree = 2;
	char[] nbrNeuroneParCouche = {2,1};
	
	
	
	/*
	 * Caractéristiques grille
	 */
	int nbrColonne = 4;
	int nbrLigne = 4;
	
	int debutPosColonne = 30;//x
	int debutPosLigne = 30;//y
	
	
}
