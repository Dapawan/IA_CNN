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
	
	public boolean isDeplRight;
	public int compteurImg;
	
	public Direction direction;

	public Fenetre_() {
		
		this.direction = Direction.INIT;
		
		this.compteurImg = 10;
		
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
						if(touche_tapee.get(i) == KeyEvent.VK_RIGHT)
						{
							direction = Direction.INIT;
							//System.out.println("Arret depl droite");
						}
						else if(touche_tapee.get(i) == KeyEvent.VK_LEFT)
						{
							direction = Direction.INIT;
							//System.out.println("Arret depl droite");
						}
						
						touche_tapee.remove(i);
						//System.out.println("Touche relachée");
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
						//System.out.println("Touche already exist");
					}
				}
				
				if( (test == false) && (touche_tapee.size() <= 2) )
				{
					//La touche n'est pas encore mémorisée && que 2 touches max
					touche_tapee.add(arg0.getKeyCode());
					//System.out.println("Touche ajoutée");
				}
				
			}
		});
		
		
		
	}
	
	public synchronized void gestionDeplacementClavier()
	{
		int posX;
		int posY;
		
		if( (touche_tapee != null) && (map != null) )
		{			
			for(Integer touche : touche_tapee)
			{
				posX = map.perso.posX;
				posY = map.perso.posY;
				
				if(touche == KeyEvent.VK_LEFT)
				{
					direction = Direction.LEFT;
					map.move( (posX - vitesseX), posY);
				}
				if(touche == KeyEvent.VK_RIGHT)
				{
					direction = Direction.RIGHT;
					//System.out.println("Depl droite");
					map.move( (posX + vitesseX), posY);
				}
				//Jump
				if( (touche == KeyEvent.VK_UP) && (map.perso.isJumping == false) )
				{
					map.perso.isJumping = true;
					//map.move(posX, (posY - jumpY) );					
				}
				if(touche == KeyEvent.VK_DOWN)
				{
					map.move(posX, (posY + vitesseY) );
				}

			}
		}
		try {
			Thread.sleep(tpsDeplacement);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
					int xStart = map.perso.posX;//map.perso.posX - espaceX;
					
					int xEnd = xStart + longueurFenetre;
					
					//g.setColor(Color.BLACK);
					
					if( (map != null) && (map.listeBloc != null) )
					{
						for(Bloc bloc : map.listeBloc)
						{
							//On ne dessine que ce qui peut apparaître à l'écran
							if( (bloc.posX >= xStart) || ( (bloc.posX + bloc.longueur) <= xEnd) )
							{
								//g.fillRect(bloc.posX, bloc.posY, bloc.longueur, bloc.hauteur);
								g.drawImage(bloc.img, (bloc.posX - map.perso.posX + map.posXRelativeFenetre), bloc.posY, null);
							}

						}
					}
					
					
					//Dessin du personnage
					
					/*g.setColor(Color.BLUE);
					
					g.fillOval(map.perso.posX, map.perso.posY, longueurPerso, hauteurPerso);*/
					if(direction == Direction.RIGHT)
					{
						if(compteurImg >= 90)
						{
							compteurImg = 10;
						}
						g.drawImage(map.perso.img[1][(compteurImg++/10)],map.posXRelativeFenetre, map.perso.posY, null);
					}
					else if(direction == Direction.LEFT)
					{
						if(compteurImg >= 90)
						{
							compteurImg = 10;
						}
						g.drawImage(map.perso.img[0][(compteurImg++/10)],map.posXRelativeFenetre, map.perso.posY, null);
					}
					else
					{
						g.drawImage(map.perso.img[1][1],map.posXRelativeFenetre, map.perso.posY, null);
					}
				}
				
			}
	}
	
}
