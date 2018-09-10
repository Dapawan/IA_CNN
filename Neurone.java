package neurone;

import java.util.Random;

public class Neurone {

	public double w;
	
	public Neurone() {
		// TODO Auto-generated constructor stub
		
		//Aléatoire entre -1 et 1
		this.w = (Math.random() * 2) - 1;
	}
}

