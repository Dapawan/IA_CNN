package mario;

public class CoucheNeuronale implements Valeurs,Cloneable{

	
	/*
	 * 
	 * On aura 4 Entrées 
	 * E1 : PosX perso
	 * E2 : PosY perso
	 * E3 : posX bloc le plus proche
	 * E4 : posY bloc le plus proche
	 * On aura 4 sorties
	 * S1 : déplacement Gauche
	 * S2 : déplacement Droite
	 * S3 : déplacement Haut (jump)
	 * S4 : déplacement Bas
	 *
	 *Schéma des couches:
	 *
	 *		C1	C2	C3 (couches neuronales)
	 *
	 *	E1	N1	N5	S1
	 *	E2	N2	N6	S2
	 *	E3	N3	N7	S3
	 *	E4	N4	N8	S4
	 *
	 *
	 *S => comme une neurone
	 *
	 */
	
	public final int NBR_COUCHE = 1;
	public final int NBR_NEURONE_PAR_COUCHE = 4;
	public final int NBR_ENTREE_PAR_NEURONE = 4;
	
	private static int numeroMutation;
	private static int numeroNeurone;
	private static boolean incrMutation;
	
	private static int numeroCoucheOld;
	private static int numeroNeuroneOld;
	private static int numeroWeightOld;
	
	private static double oldValue;
	
	
	public int score;
	
	public double[] entree;
	
	public Neurone[][] neuroneArray;
	
	public CoucheNeuronale() {
		this.neuroneArray = new Neurone[NBR_COUCHE][NBR_NEURONE_PAR_COUCHE];
		this.entree = new double[NBR_ENTREE_PAR_NEURONE];
		this.score = 0;
		for(int i = 0; i < NBR_COUCHE; i++)
		{
			for(int a = 0; a < NBR_NEURONE_PAR_COUCHE; a++)
			{
				this.neuroneArray[i][a] = new Neurone(NBR_ENTREE_PAR_NEURONE);
			}
		}
		
	}
	
	public double[] calculSortie(double[] entree2)
	{
		double[] result = new double[NBR_ENTREE_PAR_NEURONE];
		double[] entreeTemp = new double[entree2.length];
		//On save le vecteur d'entrée
		for(int i = 0; i < entree2.length; i++)
		{
			this.entree[i] = entree2[i];
			//Entree temporaire pour la récursivitée
			entreeTemp[i] = entree2[i];
		}
		
		
		
		//On parcourt toutes les neurones
		for(int i = 0; i < NBR_COUCHE; i++)
		{
			for(int a = 0; a < NBR_NEURONE_PAR_COUCHE; a++)
			{
				this.neuroneArray[i][a].calculResult(entreeTemp);
			}
			//On change le vecteur d'entree à chaque couche traitée
			for(int x = 0; x < entree2.length; x++)
			{
				//On affecte les valeurs récemment calculées en entrée de la prochaine couche neuronale
				entreeTemp[x] = this.neuroneArray[i][x].result;
			}
			
		}
		
		for(int x = 0; x < NBR_ENTREE_PAR_NEURONE; x++)
		{
			//On recopie les résultats des neurones de la dernière couche
			result[x] = this.neuroneArray[NBR_COUCHE - 1][x].result;
		}
		
		return result;
	}
	
	public CoucheNeuronale mutation(CoucheNeuronale[] coucheNeuronale, int progression)
	{
		//On copie le 1er élément
		CoucheNeuronale result = new CoucheNeuronale();
		result = coucheNeuronale[0];
		
		for(int index = 1; index < coucheNeuronale.length; index++)
		{
			//On moyenne avec les autres
			//On parcourt toutes les neurones
			for(int i = 0; i < NBR_COUCHE; i++)
			{
				for(int a = 0; a < NBR_NEURONE_PAR_COUCHE; a++)
				{
					//On fait la somme
					for(int x = 0; x < NBR_ENTREE_PAR_NEURONE; x++)
					{
						result.neuroneArray[i][a].bias[x] += coucheNeuronale[index].neuroneArray[i][a].bias[x];
						result.neuroneArray[i][a].weight[x] += coucheNeuronale[index].neuroneArray[i][a].weight[x];
					}
					//On fait la moyenne
					for(int x = 0; x < NBR_ENTREE_PAR_NEURONE; x++)
					{
						result.neuroneArray[i][a].bias[x] /= (NBR_ENTREE_PAR_NEURONE+1);
						result.neuroneArray[i][a].weight[x] /= (NBR_ENTREE_PAR_NEURONE+1);
					}
					
				}
			}
			
		}
		
		//On applique une petite mutation
		/*
		 * On choisit une couche et une neurone
		 * 
		 */
		int numeroCouche = (int) (Math.random() * NBR_COUCHE);
		
		int numeroNeurone = (int) (Math.random() * NBR_NEURONE_PAR_COUCHE);
		
		/*
		 * On choisit quelle entrée affectée
		 */
		int numeroEntree = (int) (Math.random() * NBR_ENTREE_PAR_NEURONE);
		
		/*
		 * On choisit bias ou weight
		 */
		int isBias = (int) Math.random();
		isBias = 0;
		
		if(isBias == 1)
		{
			result.neuroneArray[numeroCouche][numeroNeurone].bias[numeroEntree] += (Math.random() * (biasMax / 20));
		}
		else
		{
			result.neuroneArray[numeroCouche][numeroNeurone].weight[numeroEntree] += (Math.random() * (weightMax / 20));
			if(progression > 0)
			{
				if(oldValue == 0)
				{
					result.neuroneArray[numeroCouche][numeroNeurone].weight[numeroEntree] += (Math.random() - 0.5);
				}
				else
				{
					result.neuroneArray[numeroCoucheOld][numeroNeuroneOld].weight[numeroWeightOld] += (Math.random());
				}
				oldValue = result.neuroneArray[numeroCouche][numeroNeurone].weight[numeroEntree];
			}
			else
			{
				result.neuroneArray[numeroCoucheOld][numeroNeuroneOld].weight[numeroWeightOld] = oldValue;
				//On modifie autre chose
				if(numeroWeightOld == numeroEntree)
				{
					if( (++numeroEntree) >= NBR_ENTREE_PAR_NEURONE)
					{
						numeroEntree = 0;
					}
					result.neuroneArray[numeroCouche][numeroNeurone].weight[numeroEntree] += (Math.random() - 0.5);
					oldValue = result.neuroneArray[numeroCouche][numeroNeurone].weight[numeroEntree];
					
				}
			}
		}
		numeroCoucheOld = numeroCouche;
		numeroNeuroneOld = numeroNeurone;
		numeroWeightOld = numeroEntree;
		
		/*
		if(progression <= 0)
		{
			if(oldValue != 0)
			{//On met l'ancienne valeur
				result.neuroneArray[0][numeroNeurone].weight[numeroMutation] = oldValue;
			}
			
			//On passe au numéro suivant 
			numeroMutation++;
			
			if(numeroMutation >= NBR_ENTREE_PAR_NEURONE)
			{
				numeroMutation = 0;
				numeroNeurone++;
				if(numeroNeurone >= NBR_NEURONE_PAR_COUCHE)
				{
					numeroNeurone = 0;
				}
				if(incrMutation == false)
				{
					incrMutation = true;
				}
				else
				{
					incrMutation = false;
				}
			}
		}
		if(progression == 0)
		{
			progression = 1;
			
		}
		oldValue = result.neuroneArray[0][numeroNeurone].weight[numeroMutation];
		if(incrMutation == true)
		{
			result.neuroneArray[0][numeroNeurone].weight[numeroMutation] += (progression);
		}
		else
		{
			result.neuroneArray[0][numeroNeurone].weight[numeroMutation] -= (progression);
		}
		*/
		return result;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		for(int i = 0; i < NBR_COUCHE; i++)
		{
			result += "Couche N° " + i + "\n"; 
			for(int a = 0; a < NBR_NEURONE_PAR_COUCHE; a++)
			{
				result += "Neurone N° " + a + "\n"; 
				for(int x = 0; x < NBR_ENTREE_PAR_NEURONE; x++)
				{
					result += "bias N° " + x + " = " + neuroneArray[i][a].bias[x] + "\n";
					result += "weight N° " + x + " = " + neuroneArray[i][a].weight[x] + "\n";	
				}
			}
		}
		
		return result;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
