package mario;

public interface Valeurs {

	int hauteurFenetre = 1080;
	int longueurFenetre = 1920;
	
	
	int longueurLevel = 1000;
	//Bloc
	int hauteurBloc = 100;
	int longueurBloc = 100;
	
	int hauteurPerso = 50;
	int longueurPerso = 50;
	
	//Gravité
	float g = (float) 9.81;
	
	//Déplacement perso
	int vitesseX = 2;
	int vitesseY = 10;
	//Déplacement caméra
	int espaceX = 20;
	
}
