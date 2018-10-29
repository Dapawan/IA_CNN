package Chess;

public class Neurone implements Valeurs{

	public double[] bias;
	public double[] weight;
	public double result;
	
	public int nbrEntree;
	
	public Neurone(int nbrEntree) 
	{
		this.nbrEntree = nbrEntree;
		//On prépare les valeurs pour chaque liaisons
		bias = new double[nbrEntree];
		weight = new double[nbrEntree];
		
		//On init les valeurs des paramètres
		for(int i = 0; i < nbrEntree; i++) 
		{
			this.bias[i] = (Math.random() - 0.5) * ( (biasMax) * 2);
			this.weight[i] = (Math.random() - 0.5) * ( (weightMax) * 2);
		}
	}

	public double calculResult(double[] entree)
	{
		double result = 0.0;
		
		for(int i = 0; i < entree.length; i++)
		{
			//result += (weight * entree + bias)
			//sigm du result
			result += (this.weight[i] * entree[i]) + this.bias[i];
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
	
}
