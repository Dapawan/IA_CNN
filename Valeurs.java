package mario;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public interface Valeurs {

	int hauteurFenetre = 1000;
	int longueurFenetre = 1000;
	
	//Focus perso player
	boolean isFocusPlayer = false;
	
	//Time IA to die when AFK
	int TimeToDieAFK = 100;//En ms
	
	//Lancement IA
	boolean isIa = false;
	//Reprise ancien resultat
	boolean ancienResult = true;
	
	int nbrEssaiAVReset = 30;
	boolean isPlusieurIa = false;
	int nbrIA = 3;//32;
	int nbrResultatStocke = 2;
	
	//Neurone
	double biasMax = 0.02d;//Entre - biasMax et + biasMax
	double weightMax = 0.02d;//de même
	//Neurone inr/decr
	double incrPasNeurone = 0.005d;
	double incrPasNeuronebias = 0.005d;
	
	
	
	//Dessin coucheNeuronale
	int posXCoucheNeuronale = 10;
	int posYCoucheNeuronale = 100;
	
	int grandeurNeurone = 50;
	int longueurLiaisons = 500;
	
	Color liaisonPositive = Color.GREEN;
	Color liaisonNegative = Color.RED;
	
	Color resultatSupSeuil = Color.BLACK;
	Color resultatInfSeuil = Color.BLUE;
	
	//Seuil de décision
	double seuilDecision = 0.75d;
	
	
	//Affichage temps
	int posXChrono = 0;
	int posYChrono = 100;
	//Affichage score
	int posXScore = posXChrono + 100;
	int posYScore = posYChrono;
	//Affichage perso alive
	int posXAlive = posXScore + 150;
	int posYAlive = posYChrono;
	//Affichage géné
	int posXGene = posXChrono;
	int posYGene = posYChrono + 100;
	
	//Dessin graph
	int posXGraph = 70;
	int posYGraph = posYGene + 220;
	int posOrigineGraph = posYGraph + 300;
	int largeurEntrePointAbscisse = 10;
	int hauteurTraitGraduation = 5;
	
	int longueurLevel = 10000;
	//Bloc
	int hauteurBloc = 50;
	int longueurBloc = 100;
	/*
	int hauteurPerso = 50;
	int longueurPerso = 50;
	*/
	
	//Gravité
	float g = 10;//(float) 6;
	
	
	//Déplacement perso
	int vitesseX = 1;
	int vitesseY = 5;
	int speedXMAx = 9;
	int multiplicateur = 100;
	int decrSpeedJump = 6;
	int incrSpeedSol = 5;
	//Tick lié à la gravité de saut
	int compteurMax = 100/vitesseX;
	//Temps entre chaque déplacement perso
	int tpsDeplacement = 5;
	//Jump
	int jumpY = 200;
	
	//Palier création level up
	int palierUp = 20;
	int limitePalierUp = hauteurFenetre - 400;
	int limitePalierDown = hauteurFenetre;
	
	
	//Affichage min gauche
	int stopMvGauche = (longueurFenetre / 2);
	
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
	String pathResult = path + "Resultats\\";
}
