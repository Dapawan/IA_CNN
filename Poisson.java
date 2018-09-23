package neurone;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.LongUnaryOperator;

public class Poisson implements Valeurs{

	public int posX;
	public int posY;
	public int grandeur;
	public boolean vie;
	public boolean isUser;
	public int score;
	
	private final int DEPLACEMENT_MAX = 1;
	
	public Couche_neuronale couche_neuronale;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + grandeur;
		result = prime * result + (isUser ? 1231 : 1237);
		result = prime * result + posX;
		result = prime * result + posY;
		result = prime * result + score;
		result = prime * result + (vie ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poisson other = (Poisson) obj;
		if (grandeur != other.grandeur)
			return false;
		if (isUser != other.isUser)
			return false;
		if (posX != other.posX)
			return false;
		if (posY != other.posY)
			return false;
		if (score != other.score)
			return false;
		if (vie != other.vie)
			return false;
		return true;
	}

	public Poisson() {
		// TODO Auto-generated constructor stub
		
		this.posX = (int) (Math.random() * (longueurFenetrePoisson - grandeurPoisson) ) + posXminPoisson;
		this.posY = (int) (Math.random() * (hauteurFenetrePoisson - grandeurPoisson) ) + posYminPoisson;
		this.grandeur = grandeurPoisson;
		this.vie = true;
		this.isUser = false;
		this.score = 0;
	}
	
	public boolean testMove(Double posX,Double posY)
	{
		boolean result = true;
		
		if(posX > 0)
		{
			posX = (double) DEPLACEMENT_MAX;
		}
		if(posX < 0)
		{
			posX = (double) -1 * DEPLACEMENT_MAX;
		}
		if(posY > 0)
		{
			posY = (double) DEPLACEMENT_MAX;
		}
		if(posY < 0)
		{
			posY = (double) -1 * DEPLACEMENT_MAX;
		}
		
		if( (this.posX + posX)  < posXminPoisson)
		{
			result = false;
		}
		if( (this.posY + posY) < posYminPoisson)
		{
			result = false;
		}
		if( (this.posX + posX) > posXmaxPoisson)
		{
			result = false;
		}
		if( (this.posY + posY) > posYmaxPoisson)
		{
			result = false;
		}
		return result;
	}
	
	public void move(Double sortie,Double sortie2, ArrayList<Poisson> liste_poisson)
	{
		if(sortie > 0)
		{
			this.posX += DEPLACEMENT_MAX;
		}
		else if (sortie < 0)
		{
			this.posX -= DEPLACEMENT_MAX;
		}
		
		if(sortie2 > 0)
		{
			this.posY += DEPLACEMENT_MAX;
		}
		else if (sortie2 < 0)
		{
			this.posY -= DEPLACEMENT_MAX;
		}
		
		if(this.posX < posXminPoisson)
		{
			//this.posX = -1000;
			this.posX = posXminPoisson;
			//this.vie = false;
		}
		if(this.posY < posYminPoisson)
		{
			this.posY = posYminPoisson;
			//this.posY = -1000;
			//this.vie = false;
		}
		if(this.posX > posXmaxPoisson)
		{
			this.posX = posXmaxPoisson;
			//this.posX = -1980;
			//this.vie = false;
		}
		if(this.posY > posYmaxPoisson)
		{
			this.posY = posYmaxPoisson;
			//this.posY = -1080;
			//this.vie = false;
		}
		
		for(int i = 0; i < nombrePoissons; i++)
		{
			if( ((this.posX <= liste_poisson.get(i).posX + liste_poisson.get(i).grandeur) && (this.posX + this.grandeur) >= (liste_poisson.get(i).posX) ) &&
					((this.posY <= liste_poisson.get(i).posY + liste_poisson.get(i).grandeur) && (this.posY + this.grandeur) >= (liste_poisson.get(i).posY) )	&& (liste_poisson.get(i).equals(this) != true) && (liste_poisson.get(i).vie == true) && (this.vie == true)  )
			{
				//Collision
				liste_poisson.get(i).vie = false;
				this.grandeur += liste_poisson.get(i).grandeur;
				this.score += 1;
				//On regarde s'il y a à nouveau une collision
				i = 0;
			}
		}
		
	}
	
	public void move(Double sortie,Double sortie2)
	{
		if(sortie > 0)
		{
			this.posX += DEPLACEMENT_MAX;
		}
		else if (sortie < 0)
		{
			this.posX -= DEPLACEMENT_MAX;
		}
		
		if(sortie2 > 0)
		{
			this.posY += DEPLACEMENT_MAX;
		}
		else if (sortie2 < 0)
		{
			this.posY -= DEPLACEMENT_MAX;
		}
		
		if(this.posX < posXminPoisson)
		{
			//this.posX = -1000;
			this.posX = posXminPoisson;
			//this.vie = false;
		}
		if(this.posY < posYminPoisson)
		{
			this.posY = posYminPoisson;
			//this.posY = -1000;
			//this.vie = false;
		}
		if(this.posX > posXmaxPoisson)
		{
			this.posX = posXmaxPoisson;
			//this.posX = -1980;
			//this.vie = false;
		}
		if(this.posY > posYmaxPoisson)
		{
			this.posY = posYmaxPoisson;
			//this.posY = -1080;
			//this.vie = false;
		}
	}


}
