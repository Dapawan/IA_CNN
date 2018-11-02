package Chess;

import java.util.ArrayList;

public interface Valeurs {

	int longueurFenetre = 900;
	int hauteurFenetre = 900;
	
	/*
	 * Contrôle user
	 */
	int nbrUser = 0;//Si == 1 contrôle equipe bleu
	
	/*
	 * Contrôle IA
	 */
	int nbrIA = 2;
	
	
	/*
	 * Caractéristiques grille
	 */
	int nbrColonne = 4;
	int nbrLigne = 4;
	
	int debutPosColonne = 30;//x
	int debutPosLigne = 30;//y
	
	
	/*
	 * Neurone
	 */
	double biasMax = 0.05d;
	double weightMax = 0.05d;
	/*
	 * Reseau neuronale
	 */
	char nbrEntree = 17;
	char[] nbrNeuroneParCouche = {15,15,17};
	
	/*
	 * Types de pieces
	 */
	enum TypeDePiece{PION,TOUR,FOU,CAVALIER};
	enum Equipe{BLEU,ROUGE};	
	
	/*
	 * Play
	 * J1 : Bleu
	 * J2 : Rouge
	 */
	
	TypeDePiece PieceJ1[] = {TypeDePiece.PION,TypeDePiece.PION,TypeDePiece.PION};
	TypeDePiece PieceJ2[] = {TypeDePiece.PION,TypeDePiece.PION,TypeDePiece.PION};
	
	
	ArrayList<Piece> pieceJ1 = new ArrayList<>();
	ArrayList<Piece> pieceJ2 = new ArrayList<>();
	
}
