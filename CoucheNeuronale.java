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
	public final int NBR_NEURONE_PAR_COUCHE = 5;
	public final int NBR_ENTREE_PAR_NEURONE = 5;
	
	private static int numeroMutation;
	private static boolean incrMutation;
	
	private static int numeroCoucheOld;
	private static int numeroNeuroneOld;
	private static int numeroWeightOld;
	
	private static int numeroCouche;
	private static int numeroNeurone;
	private static int numeroWeight;
	private static boolean isIncr;
	private static boolean isIncrbias;
	private static boolean isChgmntBias=true;
	
	private static double oldValue;
	
	public double[] maxMin = new double[2];;
	public int score;
	
	public double[] entree;
	
	public Neurone[][] neuroneArray;
	
	
	void sortMaxMinWeight(Neurone[][] neuroneArray)
	{
		//double[] maxMin = new double[2];
		
		/*
		 * Init des valeurs extremes
		 * indice 0 : max
		 * indice 1 : min
		 */
		maxMin[0] = -10000.0;
		maxMin[1] = 10000.0;
		
		for(int i = 0; i < 2; i++)
		{
			for(int x = 0; x < NBR_COUCHE; x++)
			{
				for(int y = 0; y < NBR_ENTREE_PAR_NEURONE; y++)
				{
					for(int z = 0; z < NBR_ENTREE_PAR_NEURONE; z++)
					{
						if(i == 0)
						{
							if(maxMin[0] < neuroneArray[x][y].weight[z])
							{
								maxMin[0] = neuroneArray[x][y].weight[z];
							}
						}
						else
						{
							if(maxMin[1] > neuroneArray[x][y].weight[z])
							{
								maxMin[1] = neuroneArray[x][y].weight[z];
							}
						}
					}
				}
			}
		}
		
		
		//return maxMin;
	}
	
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
		double[] result = new double[NBR_NEURONE_PAR_COUCHE];
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
	
	
	public void mutationBETA(Neurone[][] neurones,int gen,float multiplicateur) throws CloneNotSupportedException
	{
		/*
		 * On copie le réseau
		 */
		//On parcourt toutes les neurones
		//this.neuroneArray = new Neurone[NBR_COUCHE][NBR_NEURONE_PAR_COUCHE];
		//this.neuroneArray = neurones.clone();
		for(int i = 0; i < NBR_COUCHE; i++)
		{
			for(int a = 0; a < NBR_NEURONE_PAR_COUCHE; a++)
			{
				//On fait la somme
				for(int x = 0; x < NBR_ENTREE_PAR_NEURONE; x++)
				{
					this.neuroneArray[i][a].weight[x] = new Double(0.0);
					this.neuroneArray[i][a].weight[x] = (double)neurones[i][a].weight[x];
				}
			}
		}
		
		
		/*
		 * On modifie directement le réseau neuronale
		 * 
		 * On applique 1 modif diff à chaque fois
		 * 
		 * Il faut donc ici 2 * NBR_ENTREE_PAR_NEURONE * NBR_NEURONE_PAR_COUCHE * NBR_COUCHE = 32 persos
		 * 
		 */
		//On mémorise pour raccourcir les tests
		//double valueWeight = this.neuroneArray[numeroCouche][numeroNeurone].weight[numeroWeight];
		
		
		if(isChgmntBias == true)
		{
			/*
			 * Chgmt bias + et -
			 */
			if(isIncrbias == true)
			{
				/*
				 * GESTION DU BIAS
				 */
				
				this.neuroneArray[numeroCouche][numeroNeurone].bias[numeroWeight] += (multiplicateur * incrPasNeuronebias); 
				isIncrbias = false;
				
				isChgmntBias = false; //-> PERMET DE PASSER AU CHGMT DE MODIF
			}
			else
			{
				/*
				 * GESTION DU BIAS
				 */
				
				this.neuroneArray[numeroCouche][numeroNeurone].bias[numeroWeight] -= (multiplicateur * incrPasNeuronebias); 
				
				isIncrbias = true;
			}
			
		}
		else//->ON modifie le poids de la neurone
		{
		
			if(isIncr == true)
			{
				//if(valueWeight != 0)//Paramètre pas utile pour le moment (à muter)
				//{
					this.neuroneArray[numeroCouche][numeroNeurone].weight[numeroWeight] += (multiplicateur * incrPasNeurone); 
					
					
						
					/*
					 * Gestion du saut de neurone/couche/poids
					 */
					
					numeroWeight++;
					if(numeroWeight >= NBR_ENTREE_PAR_NEURONE)
					{
						numeroWeight = 0;
						numeroNeurone++;
						if(numeroNeurone >= NBR_NEURONE_PAR_COUCHE)
						{
							numeroNeurone = 0;
							numeroCouche++;
							if(numeroCouche >= NBR_COUCHE)
							{
								numeroCouche = 0;
								System.out.println("*** Toutes les valeurs ont déjà été modifiées !! ****");
							}
						}
					}
					
					isIncr = false;
					isChgmntBias = true; //-> PERMET DE PASSER AU CHGMT DE BIAS
				
					
				/*}
				else
				{
					this.neuroneArray[numeroCouche][numeroNeurone].weight[numeroWeight] += (incrPasNeurone * 2);
				}*/
				
			}
			else
			{
				
				this.neuroneArray[numeroCouche][numeroNeurone].weight[numeroWeight] -= (multiplicateur * incrPasNeurone);
				isIncr = true;
				
				//if(valueWeight != 0)
				//{
				
						
				
				
				
					/*}
				else if(valueWeight < 0)
				{
					this.neuroneArray[numeroCouche][numeroNeurone].weight[numeroWeight] -= (incrPasNeurone * 2);
				}
				else
				{
					this.neuroneArray[numeroCouche][numeroNeurone].weight[numeroWeight] += (incrPasNeurone * 2);
				}*/
				
			}
		}
		
		
		
	}
	
	
	
	public void mutation(CoucheNeuronale[] coucheNeuronale,CoucheNeuronale coucheNeuronale_) throws CloneNotSupportedException
	{
		//On copie le 1er élément
		CoucheNeuronale result = new CoucheNeuronale();
		result = (CoucheNeuronale) coucheNeuronale[0].clone();
		
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
			result.neuroneArray[numeroCouche][numeroNeurone].weight[numeroEntree] += (Math.random() - 0.5);
		}
		coucheNeuronale_ = result;
		//return result;
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
			if(progression >= 0)
			{
				if(oldValue == 0)
				{
					result.neuroneArray[numeroCouche][numeroNeurone].weight[numeroEntree] += (Math.random() - 0.5);
				}
				else
				{
					result.neuroneArray[numeroCoucheOld][numeroNeuroneOld].weight[numeroWeightOld] += (oldValue);
				}
				oldValue = result.neuroneArray[numeroCouche][numeroNeurone].weight[numeroEntree];
			}
			/*
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
			}*/
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
	protected CoucheNeuronale clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (CoucheNeuronale) super.clone();
	}
}
