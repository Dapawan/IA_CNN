package mario;

public interface Valeurs {

	int hauteurFenetre = 1080;
	int longueurFenetre = 1920;
	
	
	int longueurLevel = 2000;
	//Bloc
	int hauteurBloc = 50;
	int longueurBloc = 100;
	
	int hauteurPerso = 50;
	int longueurPerso = 50;
	
	//Gravité
	float g = (float) 6;
	//Tick lié à la gravité de saut
	int compteurMax = 80;
	
	//Déplacement perso
	int vitesseX = 1;
	int vitesseY = 1;
	//Temps entre chaque déplacement perso
	int tpsDeplacement = 1;
	//Jump
	int jumpY = 200;
	
	//Palier création level up
	int palierUp = 10;
	int limitePalierUp = 500;
	int limitePalierDown = hauteurFenetre;
	
	
	//Affichage min gauche
	int stopMvGauche = (longueurFenetre / 4);
	
	//Direction
	enum Direction
	{
		INIT,
		RIGHT,
		LEFT,
		UP,
		DOWN
	}
	
	//String img
	String imgStr[] = { "depl_gauche_", "depl_droite_"};
	
}
