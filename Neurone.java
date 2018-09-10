package neurone;

import java.util.Random;

public class Neurone {

	public Double[] w;
	public Double[] bias;
	
	
	public Neurone(int nbr_liaison) {
		// TODO Auto-generated constructor stub
		
		//On init le nombre de liaisons
		this.w = new Double[nbr_liaison];
		this.bias = new Double[nbr_liaison];
		
		//On init les params entre -1 et 1
		for(int i = 0; i < nbr_liaison; i++)
		{
			this.w[i] = (Math.random() * 2) - 1;
			this.bias[i] = (Math.random() * 2) - 1;
			
			System.out.println("W" + i + " = " + this.w[i] + " bias " + i + " = " + this.bias[i]);
		}
	}
}

