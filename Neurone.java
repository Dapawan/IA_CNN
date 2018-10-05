package mario;

public class Neurone implements Valeurs{
	
	public Double[] bias;
	public Double[] weight;
	
	public int nbrEntree;
	public static int numero;
	
	public double result;
	
	public Neurone(int nbrEntree) {
		
		this.numero++;
		//System.out.println("Cr�ation de la neurone n� " + this.numero);
		this.result = 0.0;
		
		this.nbrEntree = nbrEntree;
		//On pr�pare les valeurs pour chaque liaisons
		bias = new Double[nbrEntree];
		weight = new Double[nbrEntree];
		//Init des valeurs
		
		for(int i = 0; i < nbrEntree; i++) {
			this.bias[i] = (Math.random() - 0.5) * (biasMax * 2);
			this.weight[i] = (Math.random() - 0.5) * (weightMax * 2);
		}
		
		
	}
	
	public double calculResult(Double[] entree2)
	{
		double result = 0.0;
		
		for(int i = 0; i < entree2.length; i++)
		{
			//result += sig(weight * entree + bias)
			result += sigmoideFunction( (this.weight[i] * entree2[i]) + this.bias[i]);
		}
		
		
		//On save le r�ultat
		this.result = result;
		
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
