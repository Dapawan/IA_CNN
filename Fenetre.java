package neurone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements Valeurs{
	
	public JPanel panel;
	public ArrayList<Poisson> liste_poisson;
	public float temps_sec;
	public float moyenne;
	private ArrayList<Integer> touche_tapee;
	
	
	public void gestionDeplacementClavier()
	{
		if(touche_tapee != null)
		{
			for(Integer touche : touche_tapee)
			{
				if(touche == KeyEvent.VK_LEFT)
				{
					liste_poisson.get(0).move(-1.0, 0.0, liste_poisson);
				}
				if(touche == KeyEvent.VK_RIGHT)
				{
					liste_poisson.get(0).move(1.0, 0.0,liste_poisson);
				}
				if(touche == KeyEvent.VK_UP)
				{
					liste_poisson.get(0).move(0.0, -1.0,liste_poisson);
				}
				if(touche == KeyEvent.VK_DOWN)
				{
					liste_poisson.get(0).move(0.0, 1.0,liste_poisson);
				}
			}
		}
	}
	
	public void gestionDeplacementaleatoirePoisson()
	{
		for(Poisson poisson : liste_poisson)
		{
			if(poisson.isUser == false)
			{
				double newPosX = 10;
				double newPosY = 10;
				
				
				
				newPosX = (Math.random() * 2) - 1;
				newPosY = (Math.random() * 2) - 1;
				
				
				poisson.move(newPosX, newPosY,liste_poisson);
			}
		}
	}
	
	public void gestionDeplacementPlusProche()
	{
		double minX;
		double minY;
		
		for(Poisson poisson : liste_poisson)
		{
			minX = 3000;
			minY = 3000;
			if(poisson.vie == true)
			{
				for(Poisson poisson_ : liste_poisson)
				{
					if( (poisson_.equals(poisson) == false) && (poisson_.vie == true) )
					{
						if( (minX + minY) > (Math.abs(poisson_.posX - poisson.posX) + Math.abs(poisson_.posY - poisson.posY)) )
						{
							minX = (poisson_.posX - poisson.posX);
							minY = (poisson_.posY - poisson.posY);
						}
					}
				}
				
				if( (poisson.isUser != true) && ((minX != 3000) || (minY != 3000)) )
				{
					poisson.move(minX, minY,liste_poisson);
				}
			}
		}
		
	}
	
	public Fenetre(int nbr_poissons) {
		// TODO Auto-generated constructor stub
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(longueurFenetre, hauteurFenetre);
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
		

		if(isIA == false)
		{
			liste_poisson.get(0).isUser = true;
			touche_tapee = new ArrayList<>();
			
			this.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					for(int i = 0; i < touche_tapee.size(); i++)
					{
						if(touche_tapee.get(i) == arg0.getKeyCode())
						{
							touche_tapee.remove(i);
						}
					}
				}
				
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					boolean test = false;
					for(Integer touche : touche_tapee)
					{
						if(touche == arg0.getKeyCode())
						{
							test = true;
						}
					}
					
					if(test == false && (touche_tapee.size() < 2) )
					{
						//La touche n'est pas encore mémorisée && que 2 touches max
						touche_tapee.add(arg0.getKeyCode());
					}
					
				}
			});
		}
		
		
		
	}
	
	
	public class Panel extends JPanel{
		
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			
			for(Poisson poisson : liste_poisson)
			{
				if(poisson.isUser == true)
				{
					g.setColor(Color.BLUE);
				}
				else
				{
					g.setColor(Color.black);
				}
				
				if(poisson.vie == true)
				{
					g.fillRect(poisson.posX, poisson.posY, poisson.grandeur, poisson.grandeur);
				}
			}
			//Dessin de la surface de jeu
			g.setColor(Color.GREEN);
			g.drawRect(posXminPoisson, posYminPoisson, longueurFenetrePoisson, hauteurFenetrePoisson);
			
			g.setColor(Color.BLACK);
			g.drawString("" + temps_sec + " sec" + " Moyenne = " +  moyenne, 200, 20);
			
		}

	}
}

