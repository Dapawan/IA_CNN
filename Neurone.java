package mario;

import java.util.Arrays;

public class Neurone implements Valeurs,Cloneable{
	
	public double[] bias;
	public double[] weight;
	
	public int nbrEntree;
	public static int numero;
	
	public double result;
	
	public Neurone(int nbrEntree,boolean isFirstCouche) {
		
		this.numero++;
		//System.out.println("Création de la neurone n° " + this.numero);
		this.result = 0.0;
		
		this.nbrEntree = nbrEntree;
		//On prépare les valeurs pour chaque liaisons
		bias = new double[nbrEntree];
		weight = new double[nbrEntree];
		//Init des valeurs
		
		if(isFirstCouche == false)
		{
			for(int i = 0; i < nbrEntree; i++) 
			{
				this.bias[i] = (Math.random() - 0.5) * (biasMax * 2);
				this.weight[i] = (Math.random() - 0.5) * (weightMax * 2);
			}
		}
		else
		{
			for(int i = 0; i < nbrEntree; i++) 
			{
				this.bias[i] = (Math.random() - 0.5) * ( (biasMax/100) * 2);
				this.weight[i] = (Math.random() - 0.5) * ( (weightMax/100) * 2);
			}
		}
		
		
	}
	
	public double calculResult(double[] entree2)
	{
		double result = 0.0;
		
		for(int i = 0; i < entree2.length; i++)
		{
			//result += (weight * entree + bias)
			//sigm du result
			result += (this.weight[i] * entree2[i]) + this.bias[i];
		}
		
		
		//On save le réultat
		this.result = sigmoideFunction(result);
		
		return result;
	}
	
	public double sigmoideFunction(double value)
	{
		double result = 0.0;
		
		/*
		 * 1/(1+exp(-k*x))
		 */
		result = (1/ (1 + Math.exp(value*-1)) ); //On prend k = 1 pour la fonction
		
		return result;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bias);
		result = prime * result + Arrays.hashCode(weight);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * Equals sur bias & poids
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Neurone other = (Neurone) obj;
		if (!Arrays.equals(bias, other.bias))
			return false;
		if (!Arrays.equals(weight, other.weight))
			return false;
		return true;
	}
	@Override
	protected Neurone clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Neurone) super.clone();
	}
	

}
