package mario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Lecture implements Valeurs{
	
	
	public File file;
	
	public Lecture(String nameFile) {
		this.file = new File(pathResult + nameFile + ".txt");
	}
	
	
	public CoucheNeuronale traitement()
	{
		CoucheNeuronale coucheNeuronale = new CoucheNeuronale();
		
		String str = lectureBrute();
		int indexStrDebut = 0;
		int indexStrFin = 0;
		
		int numCouche = 0;
		int numNeurone = 0;
		int numEntree = 0;
		double valueBias = 0.0;
		double valueWeight = 0.0;
		
		
		while( numCouche < coucheNeuronale.NBR_COUCHE )
		{
			//On cherche le prochain "="
			while( (str.charAt(indexStrDebut) != '=') && (indexStrDebut < str.length()) )
			{
				indexStrDebut++;
			}
			indexStrDebut++;
			indexStrFin = (indexStrDebut + 1);
			while( (str.charAt(indexStrFin) != '\n') && (indexStrFin < str.length()) )
			{
				indexStrFin++;
			}
			
			/*
			 * Bias en prems
			 */
			valueBias = Double.parseDouble(str.substring(indexStrDebut, indexStrFin));
			
			indexStrDebut = indexStrFin + 1;
			indexStrFin = indexStrDebut + 1;
			
			//On cherche le prochain "="
			while( (str.charAt(indexStrDebut) != '=') && (indexStrDebut < str.length()) )
			{
				indexStrDebut++;
			}
			indexStrDebut++;
			indexStrFin = (indexStrDebut + 1);
			while( (str.charAt(indexStrFin) != '\n') && (indexStrFin < str.length()) )
			{
				indexStrFin++;
			}
			
			valueWeight = Double.parseDouble(str.substring(indexStrDebut, indexStrFin));
			
			indexStrDebut = indexStrFin + 1;
			indexStrFin = indexStrDebut + 1;
			
			coucheNeuronale.neuroneArray[numCouche][numNeurone].bias[numEntree] = valueBias;
			coucheNeuronale.neuroneArray[numCouche][numNeurone].weight[numEntree] = valueWeight;
			
			/*
			 * Gestion des numéros suivants
			 */
			if(numEntree++ >= (coucheNeuronale.NBR_ENTREE_PAR_NEURONE - 1))
			{
				numEntree = 0;
				if(numNeurone++ >= (coucheNeuronale.NBR_NEURONE_PAR_COUCHE - 1))
				{
					numNeurone = 0;
					numCouche++;
				}
			}
		
		}
		/*for(int i = 0; i < coucheNeuronale.NBR_COUCHE; i++)
		{
			
			for(int a = 0; a < coucheNeuronale.NBR_NEURONE_PAR_COUCHE; a++)
			{
				
				for(int x = 0; x < coucheNeuronale.NBR_ENTREE_PAR_NEURONE; x++)
				{
					
				}
			}
		}*/
		
		return coucheNeuronale;
		
	}
	
	
	public String lectureBrute()
	{
		String str = "";
		int c = 1;
		
		try {
			FileReader fr = new FileReader(file);
			try {
			while( (c != -1) )
			{
				
					c = fr.read();
					str += (char)c;
				
			}
			
			fr.close();
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str;
	}

}
