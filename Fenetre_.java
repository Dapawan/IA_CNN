package mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.w3c.dom.css.RGBColor;

import neurone.Poisson;
import neurone.Fenetre.Panel;

public class Fenetre_ extends JFrame implements Valeurs{
	
	public JPanel panel;
	private volatile ArrayList<Integer> touche_tapee;
	public Map map;
	public CoucheNeuronale coucheNeuronale;
	final NumberFormat instance = NumberFormat.getNumberInstance();
	
	public double[] sortie;
	public int compteurImg;
	public Graph graph;
	public boolean isSpacePressed = false;
	
	public Chrono chrono;
	
	public Direction direction;
	public Direction direction_old;
	
	public Fenetre_() {
			
		instance.setMaximumFractionDigits(2);
		
		graph = new Graph();
		
		this.direction = Direction.INIT;
		this.direction_old = this.direction;
		
		this.compteurImg = 10;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(longueurFenetre, hauteurFenetre);
		this.setTitle("Jeux de plateforme");
		
		//SI IA
		if(chrono == null)
		{
			chrono = new Chrono();
		}
		
		
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
				/*
				 * Gestion de la touche espace
				 */
				if(arg0.getKeyCode() == KeyEvent.VK_SPACE)
				{
					if(isSpacePressed == true)
					{
						isSpacePressed = false;
					}
					else
					{
						isSpacePressed = true;
					}
				}
				
				
				//Lancement du chrono au premier mov
				if(chrono == null)
				{
					chrono = new Chrono();
				}
				
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
	public void gestionDeplacementIA(double[] sortie, Personnage perso)
	{
		
		/*
		 * 
		 * Seuil > 0.5
		 * Plusieurs déplacements possibles à la fois
		 */
		
		int posX = perso.posX;
		int posY = perso.posY;
		
		
		for(int i = 0; i < sortie.length; i++)
		{
			if(sortie[i] >= seuilDecision)
			{
				posX = perso.posX;
				posY = perso.posY;
				
				switch(i)
				{
				case 0://Bas
					
					perso.direction = Direction.DOWN;
					//Arrête le jump
					perso.isJumping = false;
					map.move(posX, (posY + vitesseY) ,perso,Direction.DOWN);
					
					break;
					
				case 1://Gauche
					/*
					 * Incrémentation du speed
					 */
					if(map.collision(posX, posY+1, perso,Direction.DOWN) == true)
					{
						perso.compteurSprint += incrSpeedSol;
					}
					
					if(perso.compteurSprint > (speedXMAx*multiplicateur))
					{
						perso.compteurSprint = speedXMAx*multiplicateur;
					}
					
					if(perso.directionOld == Direction.RIGHT)
					{
						/*
						 * Reset speed
						 */
						perso.compteurSprint = (vitesseX*multiplicateur);
					}
					
					perso.direction = Direction.LEFT;
					perso.directionOld = perso.direction;
					map.move( (posX - (perso.compteurSprint/multiplicateur)), posY,perso,Direction.LEFT);
					break;
					
				case 2://Droite
					
					/*
					 * Incrémentation du speed
					 */
					if(map.collision(posX, posY+1, perso,Direction.DOWN) == true)
					{
						perso.compteurSprint += incrSpeedSol;
					}
					
					if(perso.compteurSprint > (speedXMAx*multiplicateur))
					{
						perso.compteurSprint = speedXMAx*multiplicateur;
					}
					
					if(perso.directionOld == Direction.LEFT)
					{
						/*
						 * Reset speed
						 */
						perso.compteurSprint = (vitesseX*multiplicateur);
					}
					
					perso.direction = Direction.RIGHT;
					perso.directionOld = perso.direction;
					map.move( (posX + (perso.compteurSprint/multiplicateur)), posY,perso,Direction.RIGHT);
					break;
					
				case 3://Saut
					if(perso.isJumping == false)
					{
						if(map.collision(posX, posY+1, perso,Direction.DOWN) == true)
						{
							perso.isJumping = true;
							perso.compteur = 0;
							perso.direction = Direction.UP;
						}
					}
					break;
				}
			}
		}
		
	}
	
	public void gestionDeplacementIA(double[] sortie)
	{
		
		/*
		 * 
		 * Seuil > 0.5
		 * Plusieurs déplacements possibles à la fois
		 */
		
		int posX = map.perso.posX;
		int posY = map.perso.posY;
		
		for(int i = 0; i < sortie.length; i++)
		{
			if(sortie[i] >= seuilDecision)
			{
				switch(i)
				{
				case 0://Bas
					map.move(posX, (posY + vitesseY) );
					this.direction = Direction.DOWN;
					break;
					
				case 1://Gauche
					map.move( (posX - vitesseX), posY);
					this.direction = Direction.LEFT;
					break;
					
				case 2://Droite
					map.move( (posX + vitesseX), posY);
					this.direction = Direction.RIGHT;
					break;
					
				case 3://Saut
					if(map.perso.isJumping == false)
					{
						map.perso.isJumping = true;
						this.direction = Direction.UP;
					}
					break;
				
				}
			}
		}
		this.direction_old = this.direction;
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
					//Arrête le jump
					map.perso.compteur = compteurMax;
					map.move(posX, (posY + vitesseY) );
				}
				this.direction_old = this.direction;

			}
		}
		try {
			Thread.sleep(tpsDeplacement);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized boolean gestionPause()
	{
		if(isSpacePressed == true)
		{
			return true;
		}
		return false;
	}
			
	
	public synchronized void gestionDeplacementClavier(Personnage perso)
	{
		int posX;
		int posY;
		
		if( (touche_tapee != null) && (map != null) )
		{			
			for(Integer touche : touche_tapee)
			{
				posX = perso.posX;
				posY = perso.posY;
				
				if(touche == KeyEvent.VK_LEFT)
				{
					/*
					 * Incrémentation du speed
					 */
					if(map.collision(posX, posY+1, perso,Direction.DOWN) == true)
					{
						perso.compteurSprint += incrSpeedSol;
					}
					
					if(perso.compteurSprint > (speedXMAx*multiplicateur))
					{
						perso.compteurSprint = speedXMAx*multiplicateur;
					}
					
					if(perso.directionOld == Direction.RIGHT)
					{
						/*
						 * Reset speed
						 */
						perso.compteurSprint = (vitesseX*multiplicateur);
						System.out.println("Changement dir");
					}
					
					perso.direction = Direction.LEFT;
					perso.directionOld = perso.direction;
					
					int depl = (perso.compteurSprint/multiplicateur);
					System.out.println("" + depl);
					map.move( (posX - depl), posY,perso,perso.direction);
				}
				if(touche == KeyEvent.VK_RIGHT)
				{
					/*
					 * Incrémentation du speed
					 */
					if(map.collision(posX, posY+1, perso,Direction.DOWN) == true)
					{
						perso.compteurSprint += incrSpeedSol;
					}
					
					if(perso.compteurSprint > (speedXMAx*multiplicateur))
					{
						perso.compteurSprint = speedXMAx*multiplicateur;
					}
					
					if(perso.directionOld == Direction.LEFT)
					{
						/*
						 * Reset speed
						 */
						perso.compteurSprint = (vitesseX*multiplicateur);
						System.out.println("Changement dir");
					}
					
					perso.direction = Direction.RIGHT;
					perso.directionOld = perso.direction;
					
					int depl = (perso.compteurSprint/100);
					System.out.println("" + depl);
					//System.out.println("Depl droite");
					map.move( (posX + depl), posY,perso,perso.direction);
				}
				//Jump
				if( (touche == KeyEvent.VK_UP) && (perso.isJumping == false))
				{
					if(map.collision(posX, posY+1, perso,Direction.DOWN) == true)
					{
						perso.isJumping = true;
					}
					//map.move(posX, (posY - jumpY) );					
				}
				if(touche == KeyEvent.VK_DOWN)
				{
					perso.direction = Direction.DOWN;
					perso.isJumping = false;
					map.move(posX, (posY + vitesseY) ,perso,perso.direction);
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
			@SuppressWarnings("unused")
			@Override
			public void paint(Graphics g) {
				// TODO Auto-generated method stub
				super.paint(g);
				Personnage perso;
				
				
				
				if(  ((map != null) && (map.perso != null)) || ((map != null) && (map.persoListe != null) && (isPlusieurIa == true)) )
				{
					
					//Background
					g.drawImage(map.backgroundImg,0,0,null);
					//On dessine le graphe score dans le cas où on ne cherche pas l'optimisation de temps
					graph.dessin(g);
					
					int xStart = -1;
					if(isPlusieurIa == false)
					{
						xStart = map.perso.posX;//map.perso.posX - espaceX;
					}
					else
					{
						//On garde le perso le plus loin
						int posX = 0;
						
						if(map.persoListe.size() == nbrIA) 
						{
							for(int i = 0; i < nbrIA; i++)
							{
								posX = map.persoListe.get(i).posX;
								if( (xStart < posX) && (map.persoListe.get(i).vie == true))
								{
									xStart = posX;
								}
							}
							if( (isFocusPlayer == true) && (map.persoListe.get(0).vie == true) )
							{
								xStart = map.persoListe.get(0).posX;
							}
							//On s'occupe du blocage à gauche
							if(xStart >= stopMvGauche)
							{
								xStart -= stopMvGauche;
							}
							else
							{
								xStart = 0;
							}
							
							g.setColor(Color.black);
							if(chrono != null)
							{
								g.drawString("Temps : " + chrono.toString(),posXChrono,posYChrono);
							}
							g.drawString("Score : " + xStart + " points", posXScore, posYScore);
							
						}
					}
					int xEnd = xStart + longueurFenetre;
					
					//g.setColor(Color.BLACK);
					
					if( (map != null) && (map.listeBloc != null) && (isPlusieurIa == false))
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
					else if(isPlusieurIa == true)
					{
						for(Bloc bloc : map.listeBloc)
						{
							//On ne dessine que ce qui peut apparaître à l'écran
							if( (bloc.posX >= xStart) || ( (bloc.posX + bloc.longueur) <= xEnd) )
							{
								//g.fillRect(bloc.posX, bloc.posY, bloc.longueur, bloc.hauteur);
								g.drawImage(bloc.img, (bloc.posX - xStart), bloc.posY, null);
							}

						}
					}
					
					
					//Dessin du personnage
					
					/*g.setColor(Color.BLUE);
					
					g.fillOval(map.perso.posX, map.perso.posY, longueurPerso, hauteurPerso);*/
					
					if(isPlusieurIa == false)
					{
						if(direction == Direction.RIGHT)
						{
							if(map.persoListe.get(0).compteurImg >= 90)
							{
								map.persoListe.get(0).compteurImg = 10;
							}
							g.drawImage(map.perso.img[1][(map.persoListe.get(0).compteurImg++/10)],map.posXRelativeFenetre, map.perso.posY, null);
						}
						else if(direction == Direction.LEFT)
						{
							if(map.persoListe.get(0).compteurImg >= 90)
							{
								map.persoListe.get(0).compteurImg = 10;
							}
							g.drawImage(map.perso.img[0][(map.persoListe.get(0).compteurImg++/10)],map.posXRelativeFenetre, map.perso.posY, null);
						}
						else//Gestion de l'orientation perso sans déplacment (garde l'orientation après mv)
						{
							if( (direction_old == Direction.RIGHT) || (direction_old == Direction.INIT) )
							{
								g.drawImage(map.perso.img[1][1],map.posXRelativeFenetre, map.perso.posY, null);
							}
							else
							{
								g.drawImage(map.perso.img[0][1],map.posXRelativeFenetre, map.perso.posY, null);
							}
						}
						
						//Affichage du score
						
						g.setColor(Color.black);
						if(chrono != null)
						{
							g.drawString("Temps : " + chrono.toString(),posXChrono,posYChrono);
						}
						g.drawString("Score : " + map.perso.score + " points", posXScore, posYScore);
						
						//Dessin couche neuronale
						if(coucheNeuronale != null)
						{
							dessinCoucheNeuronale(coucheNeuronale,g);
						}
					}
					else
					{
						//Plusieur IA
						if(map.persoListe.size() == nbrIA)
						{
							for(int i = 0; i < nbrIA; i++)
							{
								perso = map.persoListe.get(i);
								int posX = perso.posX;
								if((perso.vie == true) && (posX >= (xStart) && (posX <= xEnd) ))
								{
									//
									posX -= (xStart);
									if(posX >= stopMvGauche && isFocusPlayer == false) 
									{
										
										posX = stopMvGauche;
									}
									
									if(isFocusPlayer == true)
									{
										g.setColor(Color.BLACK);
										g.drawString("" + i, posX + (perso.longueurPerso/2), perso.posY - 10);
									}
									
									if(perso.direction == Direction.RIGHT)
									{
										if(++perso.compteurImg >= 90)
										{
											perso.compteurImg = 10;
										}
										g.drawImage(perso.img[1][(perso.compteurImg/10)],posX, perso.posY, null);
									}
									else if(perso.direction == Direction.LEFT)
									{
										if(++perso.compteurImg >= 90)
										{
											perso.compteurImg = 10;
										}
										g.drawImage(perso.img[0][(perso.compteurImg/10)],posX, perso.posY, null);
									}
									else//Gestion de l'orientation perso sans déplacment (garde l'orientation après mv)
									{
										if( (perso.directionOld == Direction.RIGHT) || (perso.directionOld == Direction.INIT) )
										{
											g.drawImage(perso.img[1][1],posX, perso.posY, null);
										}
										else
										{
											g.drawImage(perso.img[0][1],posX, perso.posY, null);
										}
									}
								}
							}
							
							//Affichage nbr alive
							g.drawString("Alive : " + map.nbrPersoAlive() + "/" + nbrIA, posXAlive, posYAlive);
							//Affichage génération
							g.drawString("Gen : " + map.compteurGeneration , posXGene, posYGene);
							
							
							
							
							//Dessin couche neuronale
							if(coucheNeuronale != null)
							{
								dessinCoucheNeuronale(coucheNeuronale,g);
							}
							
						}
					}
					
					
					
					
				}
				
			}
			
			
			public void dessinCoucheNeuronale(CoucheNeuronale coucheNeuronale,Graphics g)
			{
				int posX = posXCoucheNeuronale;
				int posY = posYCoucheNeuronale;
				int oldPosX = 0;
				int oldPosY = 0;
				
				/*
				 * Affichage coloris
				 * VERT Foncé --> Valeur positive max
				 * ROUGE Foncé --> Valeur négative max
				 */
				g.drawString("Valeur MAX + : " + String.format("%.4f", (float)coucheNeuronale.maxMin[0]) + "  MAX - : " + String.format("%.4f", (float)coucheNeuronale.maxMin[1]), posX , posY - 50);
				
				//Les entrées sont avant
				g.setColor(Color.BLUE);
				for(int i = 0; i < coucheNeuronale.NBR_ENTREE_PAR_NEURONE; i++)
				{
					g.drawRect(posX, posY, grandeurNeurone, grandeurNeurone);
					g.drawString("" + coucheNeuronale.entree[i], posX + (grandeurNeurone / 3), posY + (grandeurNeurone / 2));
					posY += (grandeurNeurone);
				}
				
				for(int i = 0; i < coucheNeuronale.NBR_COUCHE; i++)
				{
					//On se décale à droite d'une couche
					posX += (grandeurNeurone + longueurLiaisons);
					oldPosX = posX;
					//On retourne à la hauteur init
					posY = posYCoucheNeuronale;
					for(int a = 0; a < coucheNeuronale.NBR_NEURONE_PAR_COUCHE; a++)
					{
						g.setColor(Color.BLACK);
						//On dessine la neurone
						g.drawOval(posX, posY, grandeurNeurone, grandeurNeurone);
						//On mémorise la hauteur de la neurone
						oldPosY = posY;
						
						if(coucheNeuronale.neuroneArray[i][a].result >= seuilDecision)
						{
							g.setColor(resultatSupSeuil);
						}
						else
						{
							g.setColor(resultatInfSeuil);
						}
						g.drawString(String.format("%.2f", (float)coucheNeuronale.neuroneArray[i][a].result), posX + (grandeurNeurone / 3), posY + (grandeurNeurone / 2));
						//On retourne à la hauteur init
						posY = posYCoucheNeuronale;
						for(int x = 0; x < coucheNeuronale.NBR_ENTREE_PAR_NEURONE; x++)
						{
							if(coucheNeuronale.neuroneArray[i][a].weight[x] > 0)
							{
								g.setColor(new Color(0, (int)(255 * (coucheNeuronale.neuroneArray[i][a].weight[x] / coucheNeuronale.maxMin[0])) , 0) );
							}
							else 
							{
								g.setColor(new Color((int)(255 * (coucheNeuronale.neuroneArray[i][a].weight[x] / coucheNeuronale.maxMin[1])), 0 , 0));
							}
							if(coucheNeuronale.neuroneArray[i][a].weight[x] != 0)
							{
								g.drawLine( (posX - longueurLiaisons), posY + (grandeurNeurone / 2), posX , oldPosY + (grandeurNeurone / 2));
								//g.drawString(String.format("%.4f + %.4f", (float)coucheNeuronale.neuroneArray[i][a].weight[x],(float)coucheNeuronale.neuroneArray[i][a].bias[x]), (posX - longueurLiaisons) + (longueurLiaisons / (a+2)) - 50,  posY + (((a+1-i)*grandeurNeurone) / 2) - 50);
							}
							posY += grandeurNeurone;
						}
						//On retourne à la posY
						posY = oldPosY;
						posY += grandeurNeurone;
					}
				}
				/*
				 * Bas
				 * Gauche
				 * Droite
				 * Haut
				 */
				g.setColor(Color.BLACK);
				posX += grandeurNeurone + 50;
				posY = posYCoucheNeuronale + (grandeurNeurone / 2);
				g.drawString("Bas", posX, posY);
				g.drawString("Gauche", posX, posY + grandeurNeurone);
				g.drawString("Droite", posX, posY + (2*grandeurNeurone));
				g.drawString("Haut", posX, posY + (3*grandeurNeurone));
				
				
			}
			
			
	}
	
	
	
}
