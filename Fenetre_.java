package mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import neurone.Poisson;
import neurone.Fenetre.Panel;

public class Fenetre_ extends JFrame implements Valeurs{
	
	public JPanel panel;
	private ArrayList<Integer> touche_tapee;
	public Map map;

	public Fenetre_() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(longueurFenetre, hauteurFenetre);
		this.setTitle("Jeux de plateforme");
		
		
		
		panel = new Panel();
		panel.setVisible(true);
		this.add(this.panel);
		this.setVisible(true);
		
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
	
	public void gestionDeplacementClavier()
	{
		if( (touche_tapee != null) && (map != null) )
		{
			int posX = map.perso.posX;
			int posY = map.perso.posY;
			
			for(Integer touche : touche_tapee)
			{
				if(touche == KeyEvent.VK_LEFT)
				{
					map.move( (posX - vitesseX), posY);
				}
				if(touche == KeyEvent.VK_RIGHT)
				{
					map.move( (posX + vitesseX), posY);
				}
				if(touche == KeyEvent.VK_UP)
				{
					map.move(posX, (posY - vitesseY) );
				}
				if(touche == KeyEvent.VK_DOWN)
				{
					map.move(posX, (posY + vitesseY) );
				}
			}
		}
	}
	
	public class Panel extends JPanel{
		
		
		
		
		public Panel() {
			// TODO Auto-generated constructor stub
			map = new Map();
		}
			@Override
			public void paint(Graphics g) {
				// TODO Auto-generated method stub
				super.paint(g);
				
				if( (map != null) && (map.perso != null) )
				{
					int xStart = 0;//map.perso.posX - espaceX;
					if(xStart < 0)
					{
						xStart = 0;
					}
					int xEnd = xStart + longueurFenetre;
					
					g.setColor(Color.BLACK);
					
					if( (map != null) && (map.listeBloc != null) )
					{
						for(Bloc bloc : map.listeBloc)
						{
							if( (bloc.posX >= xStart) && (bloc.posX <= xEnd) )
							{
								g.fillRect(bloc.posX, bloc.posY, bloc.longueur, bloc.hauteur);
							}
						}
					}
					
					g.setColor(Color.BLUE);
					
					g.fillOval(map.perso.posX, map.perso.posY, longueurPerso, hauteurPerso);
				}
				
			}
	}
	
}
