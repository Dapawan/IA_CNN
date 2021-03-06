package mario;

public class CoucheNeuronale implements Valeurs{

	
	/*
	 * 
	 * On aura 4 Entr�es 
	 * E1 : PosX perso
	 * E2 : PosY perso
	 * E3 : posX bloc le plus proche
	 * E4 : posY bloc le plus proche
	 * On aura 4 sorties
	 * S1 : d�placement Gauche
	 * S2 : d�placement Droite
	 * S3 : d�placement Haut (jump)
	 * S4 : d�placement Bas
	 *
	 *Sch�ma des couches:
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
	
	private final int NBR_COUCHE = 1;
	private final int NBR_NEURONE_PAR_COUCHE = 4;
	private final int NBR_ENTREE_PAR_NEURONE = 4;
	
	private static int numeroMutation;
	private static boolean incrMutation;
	private static double oldValue;
	
	
	public int score;
	
	private Double[] entree;
	
	private Neurone[][] neuroneArray;
	
	public CoucheNeuronale() {
		this.neuroneArray = new Neurone[NBR_COUCHE][NBR_NEURONE_PAR_COUCHE];
		this.score = 0;
		for(int i = 0; i < NBR_COUCHE; i++)
		{
			for(int a = 0; a < NBR_NEURONE_PAR_COUCHE; a++)
			{
				this.neuroneArray[i][a] = new Neurone(NBR_ENTREE_PAR_NEURONE);
			}
		}
		
	}
	
	public Double[] calculSortie(Double[] entree2)
	{
		Double[] result = new Double[NBR_ENTREE_PAR_NEURONE];
		//On save le vecteur d'entr�e
		this.entree = entree2;
		
		//On parcourt toutes les neurones
		for(int i = 0; i < NBR_COUCHE; i++)
		{
			for(int a = 0; a < NBR_NEURONE_PAR_COUCHE; a++)
			{
				this.neuroneArray[i][a].calculResult(entree2);
			}
			//On change le vecteur d'entree � chaque couche trait�e
			for(int x = 0; x < entree2.length; x++)
			{
				//On affecte les valeurs r�cemment calcul�es en entr�e de la prochaine couche neuronale
				entree2[x] = this.neuroneArray[i][x].result;
			}
			
		}
		
		for(int x = 0; x < NBR_ENTREE_PAR_NEURONE; x++)
		{
			//On recopie les r�sultats des neurones de la derni�re couche
			result[x] = this.neuroneArray[NBR_COUCHE - 1][x].result;
		}
		
		return result;
	}
	
	public CoucheNeuronale mutation(CoucheNeuronale[] coucheNeuronale, int progression)
	{
		//On copie le 1er �l�ment
		CoucheNeuronale result = coucheNeuronale[0];
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
		//int numeroCouche = (int) (Math.random() * NBR_COUCHE);
		//int numeroNeurone = (int) (Math.random() * NBR_NEURONE_PAR_COUCHE);
		/*
		 * On choisit quelle entr�e affect�e
		 */
		//int numeroEntree = (int) (Math.random() * NBR_ENTREE_PAR_NEURONE);
		/*
		 * On choisit bias ou weight
		 */
		//int isBias = (int) Math.random();
		
		/*if(isBias == 1)
		{
			result.neuroneArray[numeroCouche][numeroNeurone].bias[numeroEntree] += (Math.random() * (biasMax / 20));
		}
		else
		{
			result.neuroneArray[numeroCouche][numeroNeurone].weight[numeroEntree] += (Math.random() * (weightMax / 20));
		}
		*/
		
		if(progression < 0)
		{
			if(oldValue != 0)
			{//On met l'ancienne valeur
				result.neuroneArray[0][numeroMutation].weight[numeroMutation] = oldValue;
			}
			
			if(numeroMutation >= NBR_NEURONE_PAR_COUCHE)
			{
				numeroMutation = 0;
				if(incrMutation == false)
				{
					incrMutation = true;
				}
				else
				{
					incrMutation = false;
					//On passe au num�ro suivant 
					numeroMutation++;
				}
			}
		}
		oldValue = result.neuroneArray[0][numeroMutation].weight[numeroMutation];
		if(incrMutation == true)
		{
			result.neuroneArray[0][numeroMutation].weight[numeroMutation] += (progression);
		}
		else
		{
			result.neuroneArray[0][numeroMutation].weight[numeroMutation] -= (progression);
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		for(int i = 0; i < NBR_COUCHE; i++)
		{
			result += "Couche N� " + i + "\n"; 
			for(int a = 0; a < NBR_NEURONE_PAR_COUCHE; a++)
			{
				result += "Neurone N� " + a + "\n"; 
				for(int x = 0; x < NBR_ENTREE_PAR_NEURONE; x++)
				{
					result += "bias N� " + x + " = " + neuroneArray[i][a].bias[x] + "\n";
					result += "weight N� " + x + " = " + neuroneArray[i][a].weight[x] + "\n";	
				}
			}
		}
		
		return result;
	}
	
}
