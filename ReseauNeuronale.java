package Chess;

public class ReseauNeuronale implements Valeurs{

	public Neurone[][] neuroneListe;
	
	public ReseauNeuronale() {
		/*
		 * On crée le tableau des neurones avec la taille la plus grande nécessaire
		 */
		this.neuroneListe = new Neurone[nbrNeuroneParCouche.length][sortMaxTailleCouche()];
		
		/*
		 * On indique le nbr d'entree de chaque neurone
		 */
		for(int couche = 0; couche < nbrNeuroneParCouche.length; couche++)	
		{
			/*
			 * On parcourt toutes les couches
			 */
			for(int neurone = 0; neurone < nbrNeuroneParCouche[couche]; neurone++)
			{
				/*
				 * On parcourt le nomnbre de neurone dans la couche
				 */
				int nbrEntreeTemp = 0;
				
				if(couche == 0)
				{
					nbrEntreeTemp = nbrEntree;
				}
				else
				{
					nbrEntreeTemp = nbrNeuroneParCouche[couche - 1];
				}
				neuroneListe[couche][neurone] = new Neurone(nbrEntree);
			}
			
		}
	}
	
	
	
	public double[] result(double entree[])
	{
		double[] result = new double[nbrNeuroneParCouche[nbrNeuroneParCouche.length - 1]];
		
		for(int couche = 0; couche < nbrNeuroneParCouche.length; couche++)	
		{
			/*
			 * On parcourt toutes les couches
			 */
			for(int neurone = 0; neurone < nbrNeuroneParCouche[couche]; neurone++)
			{
				/*
				 * On parcourt le nomnbre de neurone dans la couche
				 */
				neuroneListe[couche][neurone].calculResult(entree);
			}
			/*
			 * On modifie le vecteur d'entrée
			 */
			//On recrée le vecteur d'entrée avec sa nouvelle taille
			entree = new double[nbrNeuroneParCouche[couche]];
			for(int neurone = 0; neurone < nbrNeuroneParCouche[couche]; neurone++)
			{
				//On copie les valeurs
				entree[neurone] = neuroneListe[couche][neurone].result;
			}
		}
		
		/*
		 * Copie de la dernière couche dans le tableau de résultat
		 */
		for(int neurone = 0; neurone < nbrNeuroneParCouche[nbrNeuroneParCouche.length - 1]; neurone++)
		{
			//On copie les valeurs
			result[neurone] = neuroneListe[nbrNeuroneParCouche.length - 1][neurone].result;
		}
	
		return result;
		
	}
	
	
	
	
	public char sortMaxTailleCouche()
	{
		char result = 0;
		
			for(int i = 0; i < nbrNeuroneParCouche.length; i++)
			{
				if(result < nbrNeuroneParCouche[i])
				{
					result = nbrNeuroneParCouche[i];
				}
			}
		
		return result;
	}
	

}
