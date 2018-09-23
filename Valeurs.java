package neurone;

public interface Valeurs {
	
	int hauteurFenetre = 1080;
	int longueurFenetre = 1920;
	
	//Largeur poisson
	int grandeurPoisson = 10;
	//Espace poissons
	int hauteurFenetrePoisson = 800;
	int longueurFenetrePoisson = 800;
	
	//Limite pos x
	int posXminPoisson = (longueurFenetre / 2) - (longueurFenetrePoisson / 2);
	int posXmaxPoisson = (longueurFenetre / 2) + (longueurFenetrePoisson / 2) - grandeurPoisson;
	//Limite pos y 
	int posYminPoisson = (hauteurFenetre / 2) - (hauteurFenetrePoisson / 2);
	int posYmaxPoisson = (hauteurFenetre / 2) + (hauteurFenetrePoisson / 2) - grandeurPoisson;
	
	
	
	
	int nombrePoissons = 50;
	
	boolean isIA = false;
	

}
