package mario;

import java.util.ArrayList;
import java.util.Comparator;

public class Resultat implements Comparator<CoucheNeuronale>{
	
	public ArrayList<CoucheNeuronale> CoucheNeuronaleListe;
	
	public Resultat() {
		CoucheNeuronaleListe = new ArrayList<>();
	}
	
	public void ajout(CoucheNeuronale coucheNeuronale)
	{
		this.CoucheNeuronaleListe.add(coucheNeuronale);
	}

	@Override
	public int compare(CoucheNeuronale o1, CoucheNeuronale o2) {
		return (o2.score - o1.score);
	}
	
	
	
	
	

}
