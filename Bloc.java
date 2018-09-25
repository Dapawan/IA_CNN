package mario;

public class Bloc implements Valeurs{
	
	public boolean isBloquant;
	public int posX;
	public int posY;
	public int hauteur;
	public int longueur;
	
	public Bloc(int posX,int posY) {
		this.isBloquant = true;
		this.posX = posX;
		this.posY = posY;
		
		this.hauteur = hauteurBloc;
		this.longueur = longueurBloc;
	}

}
