package Chess;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements Valeurs{

	private Panel panel;
	private Grille grille;
	
	
	public Fenetre() {
		
		this.grille = new Grille(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(longueurFenetre, hauteurFenetre);
		setTitle("Jeux d'échec");
		setVisible(true);
		
		
		panel = new Panel();
		
		
		this.add(panel);
		
	}
	
	
	public class Panel extends JPanel{
		
		
		@Override
		public void paint(Graphics g) {
			
			super.paint(g);
			
			grille.dessin(g);
			
			
		}
		
	}

}
