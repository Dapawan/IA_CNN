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
	
	//Gravit�
	float g = (float) 9.81;
	
	//D�placement perso
	int vitesseX = 2;
	int vitesseY = 2;
	//D�placement cam�ra
	int espaceX = 20;
	
	//Palier cr�ation level up
	int palierUp = 10;
	int limitePalierUp = 500;
	int limitePalierDown = hauteurFenetre;
	
}
