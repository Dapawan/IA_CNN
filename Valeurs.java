package Chess;

import java.util.ArrayList;

public interface Valeurs {

	int longueurFenetre = 900;
	int hauteurFenetre = 900;
	
	/*
	 * Contr�le user
	 */
	int nbrUser = 0;//Si == 1 contr�le equipe bleu
	
	/*
	 * Contr�le IA
	 */
	int nbrIA = 2;
	int poidsPonderationProprePieceIA = 2;
	
	/*
	 * Caract�ristiques grille
	 * Garder une grille paire !
	 */
	int nbrColonne = 2;
	int nbrLigne = 2;
	
	int debutPosColonne = 30;//x
	int debutPosLigne = 30;//y
	
	
	/*
	 * Neurone
	 */
	double biasMax = 5d;
	double weightMax = 5d;
	/*
	 * Reseau neuronale
	 */
	char nbrEntree = 4;
	char[] nbrNeuroneParCouche = {4,2,4};
	
	/*
	 * Types de pieces
	 */
	enum TypeDePiece{PION,TOUR,FOU,CAVALIER};
	enum Equipe{BLEU,ROUGE};	
	
	/*
	 * Nbr de m�me deplacement autoris�s avant arret game
	 */
	int nbrSameMove = 3; //1 pour le save puis 2 move
	
	
	/*
	 * Play
	 * J1 : Bleu
	 * J2 : Rouge
	 */
	
	TypeDePiece PieceJ1[] = {TypeDePiece.PION};
	TypeDePiece PieceJ2[] = {TypeDePiece.PION};
	
	
	ArrayList<Piece> pieceJ1 = new ArrayList<>();
	ArrayList<Piece> pieceJ2 = new ArrayList<>();
	
}
