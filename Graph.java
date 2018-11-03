package mario;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Graph implements Valeurs{
	
	ArrayList<Integer> scoreListe;
	
	ArrayList<Double> tempsParBlocListe;
	
	float pasY = 0;
	int maxScore = 0;
	int indiceScoreMax = 0;
	
	/*
	 * Object permettant de représenter les différents scores atteinds en fonction du numéro de mutation
	 * 
	 * Ou bien les temps de parcours en tps/bloc
	 */
	public Graph() {
		if(optimisationIaBoucleInf == false)
		{
			this.scoreListe = new ArrayList<>();
		}
		else
		{
			this.tempsParBlocListe = new ArrayList<>();
		}
	}
	
	public void addTime(double temps)
	{
		this.tempsParBlocListe.add(temps);
	}
	
	public void addScore(int score)
	{
		this.scoreListe.add(new Integer(score));
	}
	
	public boolean isBestTime()
	{
		if(tempsParBlocListe.size() <= 1)
		{
			return true;
		}
		
		int indexB = tempsParBlocListe.size() - 1;
		
		if(tempsMin() > tempsParBlocListe.get(indexB))
		{
			return true;
		}
		
		return false;
	}
	
	
	public double tempsMin()
	{
		double result = 10000.0;
		
		for(int i = 0; i < (tempsParBlocListe.size() - 1); i++)
		{
			if(result > tempsParBlocListe.get(i))
			{
				result = tempsParBlocListe.get(i);
			}
		}
		
		return result;
	}
	
	public double tempsMax()
	{
		double result = 0.0;
		
		for(double value : this.tempsParBlocListe)
		{
			if(result < value)
			{
				result = value;
			}
		}
		
		return result;
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
		if(optimisationIaBoucleInf == false)
		{
			pasY = (float)(posOrigineGraph - posYGraph) / (float)longueurLevel;
		}
		else
		{
			pasY = (float)(posOrigineGraph - posYGraph) / (float)tempsMax();
		}
	}
	
	public void dessinCourbes(Graphics g)
	{
		int posY = posOrigineGraph;
		int oldPosY = posOrigineGraph;
		int indiceX = 0;
		g.setColor(Color.MAGENTA);
		
		
		if(optimisationIaBoucleInf == false)
		{
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
		else
		{
			for(Double temps : tempsParBlocListe) 
			{
				indiceX++;
				posY = posOrigineGraph - (int)(pasY * temps);
				int t = 0;
				int tp = 0;
				t= posXGraph + ((indiceX-1) * largeurEntrePointAbscisse);
				tp = posXGraph + (indiceX * largeurEntrePointAbscisse);
				g.drawLine(t, oldPosY, tp, posY);
				oldPosY = posY;
			}
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
		int nbrValeurs = 0;
		if(optimisationIaBoucleInf == false)
		{
			nbrValeurs = scoreListe.size();
		}
		else
		{
			nbrValeurs = tempsParBlocListe.size();
		}
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
		if(optimisationIaBoucleInf == false)
		{
			g.drawString("" + longueurLevel, posXGraph - 50, posYGraph);
		}
		else
		{
			g.drawString( (String.format("%.2f", (float)tempsMax())), posXGraph - 50, posYGraph);
		}
		//Graduations x
		for(int i = 0; i < nbrValeurs; i++)
		{
			posX = posXGraph + (i * largeurEntrePointAbscisse);
			g.drawLine(posX, posOrigineGraph - hauteurTraitGraduation, posX, posOrigineGraph + hauteurTraitGraduation);
			g.drawString("" + i, posX, posOrigineGraph + hauteurTraitGraduation + 10);
		}
		
	}
	

}
