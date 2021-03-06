package mario;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public interface Valeurs {

	int hauteurFenetre = 1080;
	int longueurFenetre = 1000;
	
	//Neurone
	int biasMax = 50;//Entre - biasMax et + biasMax
	int weightMax = 50;//de m�me
	
	
	//Affichage temps
	int posXChrono = 0;
	int posYChrono = 100;
	//Affichage score
	int posXScore = posXChrono + 100;
	int posYScore = posYChrono;
	
	int longueurLevel = 100000;
	//Bloc
	int hauteurBloc = 50;
	int longueurBloc = 100;
	
	int hauteurPerso = 50;
	int longueurPerso = 50;
	
	//Gravit�
	float g = (float) 6;
	//Tick li� � la gravit� de saut
	int compteurMax = 80;
	
	//D�placement perso
	int vitesseX = 1;
	int vitesseY = 1;
	//Temps entre chaque d�placement perso
	int tpsDeplacement = 1;
	//Jump
	int jumpY = 200;
	
	//Palier cr�ation level up
	int palierUp = 10;
	int limitePalierUp = hauteurFenetre - 100;
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
	
	//Background img
	String backgroundStr = "C:\\\\Users\\\\Dapawan\\\\eclipse-workspace\\\\IA\\\\src\\\\mario\\\\images\\\\background_1.png";
	
}
