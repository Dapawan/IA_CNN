package neurone;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame{
	
	public JPanel panel;
	public ArrayList<Poisson> liste_poisson;
	public float temps_sec;
	public float moyenne;
	
	public Fenetre(int nbr_poissons) {
		// TODO Auto-generated constructor stub
		this.setVisible(true);
		this.setSize(1920, 1080);
		this.setTitle("Jeux de poissons");
		
		panel = new Panel();
		panel.setVisible(true);
		this.add(this.panel);
		
		temps_sec = 0;
		liste_poisson = new ArrayList<>();
		
		for(int i = 0; i < nbr_poissons; i++)
		{
			liste_poisson.add(new Poisson());
		}
		
	}
	
	public class Panel extends JPanel{
		
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			g.setColor(Color.black);
			for(Poisson poisson : liste_poisson)
			{
				if(poisson.vie == true)
				{
					g.fillOval(poisson.posX, poisson.posY, poisson.grandeur, poisson.grandeur);
				}
			}
			
			g.drawString("" + temps_sec + " sec" + " Moyenne = " +  moyenne, 200, 20);
			
		}

	}
}

