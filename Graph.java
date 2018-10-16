package mario;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Graph implements Valeurs{
	
	ArrayList<Integer> scoreListe;
	
	float pasY = 0;
	int maxScore = 0;
	int indiceScoreMax = 0;
	
	/*
	 * Object permettant de représenter les différents scores atteinds en fonction du numéro de mutation
	 */
	public Graph() {
		this.scoreListe = new ArrayList<>();
		/*for(int i = 1; i < 3; i++)
		{
			this.scoreListe.add(new Integer(i * 300));
		}
		this.scoreListe.add(new Integer(longueurLevel));
		this.scoreListe.add(new Integer(longueurLevel/2));
		this.scoreListe.add(new Integer(longueurLevel/4));
		this.scoreListe.add(new Integer(longueurLevel/3));*/
	}
	
	public void addScore(int score)
	{
		this.scoreListe.add(new Integer(score));
	}
	
	/*
	 * 1.Calculer l'échelle de mesure en ordonnée ( ordonnée max == score max )
	 * 2.Tracer les axes ( + écriture des différentes valeurs)
	 * 3.Tracer les traits
	 */
	public void dessin(Graphics g)
	{
		dessinAxes(g);
		calculPasY();
		dessinCourbes(g);
		
	}
	
	public void calculPasY()
	{
		pasY = (float)(posOrigineGraph - posYGraph) / (float)longueurLevel;
	}
	
	public void dessinCourbes(Graphics g)
	{
		int posY = posOrigineGraph;
		int oldPosY = posOrigineGraph;
		int indiceX = 0;
		g.setColor(Color.MAGENTA);
		for(Integer score : scoreListe)
		{
			indiceX++;
			posY = posOrigineGraph - (int)(pasY * score);
			int t = 0;
			int tp = 0;
			t= posXGraph + ((indiceX-1) * largeurEntrePointAbscisse);
			tp = posXGraph + (indiceX * largeurEntrePointAbscisse);
			g.drawLine(t, oldPosY, tp, posY);
			oldPosY = posY;
		}
	}
	
	public void calculMaxScore()
	{
		maxScore = 0;
		indiceScoreMax = 0;
		for(Integer score : scoreListe)
		{
			if(score > maxScore)
			{
				maxScore = score;
			}
			indiceScoreMax++;
		}
	}
	
	public void dessinAxes(Graphics g)
	{
		int nbrValeurs = scoreListe.size();
		int posX = 0;
		g.setColor(Color.BLACK);
		//Axe Y
		g.drawLine(posXGraph, posYGraph, posXGraph, posOrigineGraph);
		//Axe X
		g.drawLine(posXGraph, posOrigineGraph, posXGraph + (nbrValeurs * largeurEntrePointAbscisse), posOrigineGraph);
		
		//On écrits les graduations
		//Graduation Ymax
		g.setColor(Color.BLUE);
		g.drawLine(posXGraph - hauteurTraitGraduation, posYGraph, posXGraph + hauteurTraitGraduation, posYGraph);
		g.drawString("" + longueurLevel, posXGraph - 50, posYGraph);
		//Graduations x
		for(int i = 0; i < nbrValeurs; i++)
		{
			posX = posXGraph + (i * largeurEntrePointAbscisse);
			g.drawLine(posX, posOrigineGraph - hauteurTraitGraduation, posX, posOrigineGraph + hauteurTraitGraduation);
			g.drawString("" + i, posX, posOrigineGraph + hauteurTraitGraduation + 10);
		}
		
	}
	

}
