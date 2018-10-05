package mario;

import java.security.Timestamp;
import java.sql.Time;

public class Chrono {
	
	private long depart;
	private long fin;
	
	
	public Chrono() {
		this.depart = System.currentTimeMillis();
	}
	
	public void start()
	{
		this.depart = System.currentTimeMillis();
	}
	
	public int getTime()
	{
		this.fin = System.currentTimeMillis();
		return (int) (this.fin - this.depart);
	}

	public void stop()
	{
		this.depart = 0;
		this.fin = 0;
	}
	
	@Override
	public String toString() {
		int temps = this.getTime();
		
		int heure = 0;
		int min = 0;
		int sec = 0;
		
		
		sec = (temps / 1000);
		
		if(temps >= 60)
		{
			//De l'ordre de quelques min
			min = (temps / 60000);
			sec = (sec % 60);
		}
		if(temps >= 1440)
		{
			//De l'ordre de quelques heures
			heure = (temps / 1440000);
			min = (min % 60);
		}
		
		
		return "" + heure + " : " + min + " : " + sec;
	}
	
}
