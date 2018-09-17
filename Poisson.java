package neurone;

import java.util.Comparator;

public class Poisson{

	public int posX;
	public int posY;
	public int grandeur;
	public boolean vie;
	public int score;
	
	public Couche_neuronale couche_neuronale;
	
	public Poisson() {
		// TODO Auto-generated constructor stub
		
		this.posX = (int) (Math.random() * 1000) + 50;
		this.posY = (int) (Math.random() * 1080) + 50;
		this.grandeur = 5;
		this.vie = true;
		this.score = 0;
	}
	
	public void move(Double sortie,Double sortie2)
	{
		this.posX += sortie;
		this.posY += sortie2;
		
		if(this.posX < 50)
		{
			this.posX = -1000;
			this.vie = false;
		}
		if(this.posY < 50)
		{
			this.posY = -1000;
			this.vie = false;
		}
		if(this.posX > 1000)
		{
			this.posX = -1980;
			this.vie = false;
		}
		if(this.posY > 1000)
		{
			this.posX = -1080;
			this.vie = false;
		}
	}


}
