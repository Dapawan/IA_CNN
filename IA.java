package Chess;

import java.util.ArrayList;

import Chess.Valeurs.Equipe;

public class IA {

	public Partie partie;
	
	public ReseauNeuronale reseauNeuronal;
	public ArrayList<Piece> listePiece;
	public int score;
	public Equipe equipe;
	
	public DeplacementHistorique deplacementHistorique = new DeplacementHistorique();
	
	
	
	
	
	
	public IA(Equipe equipe) 
	{
		this.equipe = equipe;
		this.partie = new Partie();
		this.reseauNeuronal = new ReseauNeuronale();
		this.listePiece = partie.creationPieceIA(equipe);
	}
	
	
	public void genAleatoire()
	{
		this.listePiece = partie.creationPieceIA(equipe);
		this.reseauNeuronal = new ReseauNeuronale();
		this.score = 0;	
	}
		
	public void mutation()
	{
		
	}

}
