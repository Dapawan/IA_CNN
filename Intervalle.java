package mario;

public class Intervalle {
	
	public int intervalleDebut = new Integer(0);
	public int intervalleFin = new Integer(0);
	
	
	public Intervalle() {
		intervalleDebut = 0;
		intervalleFin = 0;
	}
	
	public boolean isIn(int value)
	{
		if( (value >= intervalleDebut) && (value <= intervalleFin) )
		{
			return true;
		}
		
		return false;
	}
	/*
	 * Valeurs en communs entre 2 intervalles
	 */
	
	public boolean isIn(Intervalle intervalle)
	{
		/*
		 * On regarde si l'intervalle est dans l'autre
		 */
		if( ((intervalle.intervalleDebut >= intervalleDebut) &&
				(intervalle.intervalleDebut <= intervalleFin)) 
				
				||
				
				((intervalle.intervalleFin >= intervalleDebut) &&
				(intervalle.intervalleFin <= intervalleFin)) )			
		{
			return true;
		}
		/*
		 * On regarde si l'autre est dans l'intervalle
		 */
		if( ((intervalleDebut >= intervalle.intervalleDebut) &&
				(intervalleDebut <= intervalle.intervalleFin)) 
				
				||
				
				((intervalleFin >= intervalle.intervalleDebut) &&
				(intervalleFin <= intervalle.intervalleFin)) )
		{
			return true;
		}
		
		return false;
				
	}

}
