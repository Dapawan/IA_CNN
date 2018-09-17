package neurone;

import java.util.Random;

public class Neurone {

	public Double[] w;
	public Double[] bias;
	
	public Double result;
	
	public static int numero;
	
	
	public Neurone(int nbr_liaison) {
		// TODO Auto-generated constructor stub
		numero++;
		//On init le nombre de liaisons
		this.w = new Double[nbr_liaison];
		this.bias = new Double[nbr_liaison];
		
		//On init les params entre -1 et 1
		for(int i = 0; i < nbr_liaison; i++)
		{
			this.w[i] = (Math.random() * 2) - 1;
			this.bias[i] = (double) 0;//(Math.random() * 20) - 10;
			
			/*this.bias[i] = (double) numero;
			this.w[i] = (double) numero;*/
			
			//System.out.println("W" + i + " = " + this.w[i] + " bias " + i + " = " + this.bias[i]);
		}
	}
	
	public double sigmoide(double x)
	{
		/*
		 * 1/(1+exp(-k*x))
		 */
		return (1/(1 + Math.exp(-x))); //On prend k = 1 pour la fonction
	}
}

