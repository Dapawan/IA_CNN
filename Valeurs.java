package mario;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public interface Valeurs {

	int hauteurFenetre = 1080;
	int longueurFenetre = 1000;
	
	//Neurone
	int biasMax = 0;//Entre - biasMax et + biasMax
	int weightMax = 1;//de même
	
	
	//Dessin coucheNeuronale
	int posXCoucheNeuronale = 10;
	int posYCoucheNeuronale = 200;
	
	int grandeurNeurone = 50;
	int longueurLiaisons = 200;
	
	Color liaisonPositive = Color.GREEN;
	Color liaisonNegative = Color.RED;
	
	Color resultatSupSeuil = Color.BLACK;
	Color resultatInfSeuil = Color.BLUE;
	
	
	
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
	String backgroundStr = "images\\background_1.png";
	
	
	
	
	String path = new File("").getAbsolutePath() + "\\src\\mario\\";
	//String path = new File("").getAbsolutePath() + "\\";
}
