package Chess;

import java.util.ArrayList;

import Chess.Valeurs.Equipe;

public class IA {

	public Partie partie;
	
	public ReseauNeuronale reseauNeuronal;
	public ArrayList<Piece> listePiece;
	public int score;
	public Equipe equipe;
	
	public IA(Equipe equipe) 
	{
		this.equipe = equipe;
		this.partie = new Partie();
		this.reseauNeuronal = new ReseauNeuronale();
		this.listePiece = partie.creationPieceIA(equipe);
	}

}
