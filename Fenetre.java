package Chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements Valeurs{

	private Panel panel;
	public Grille grille;
	private Partie partie;
	
	public int indicePieceSelectJ1 = 0;
	public int indicePieceSelectJ2 = 0;
	
	
	
	public Fenetre(Partie partie) {
		
		this.partie = partie;
		partie.fenetre = this;
		this.grille = new Grille(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(longueurFenetre, hauteurFenetre);
		setTitle("Jeux d'échec");
		setVisible(true);
		
		
		panel = new Panel();
		
		
		this.add(panel);
		
		if(nbrUser > 0)
		{
			panel.addMouseListener(partie);
		}
	}
		
	
	public class Panel extends JPanel{
		
		
		@Override
		public void paint(Graphics g) {
			
			super.paint(g);
			
			grille.dessin(g);
			
			/*
			 * Dessin des pieces
			 */
			if(pieceJ1.size() >= 1)
			{
				for(Piece piece: pieceJ1)
				{
					piece.dessin(g, grille.espaceColonne, grille.espaceLigne);
				}
			}
			if(pieceJ2.size() >= 1)
			{
				for(Piece piece: pieceJ2)
				{
					piece.dessin(g, grille.espaceColonne, grille.espaceLigne);
				}
			}
			
			/*
			 * Dessin deplacement J 1 ou 2
			 */
			dessinDeplacementJ(g);
			
			
		}
		
		public void dessinDeplacementJ(Graphics g)
		{
			if( (partie.tourDeJeu == Equipe.BLEU) && (pieceJ1.size() >= 1) )
			{
				g.setColor(Color.BLUE);
				/*
				 * Dessin des déplacements possibles
				 */
				
				/*
				 * Sécu quand une piece a été del
				 */
				while(indicePieceSelectJ1 >= pieceJ1.size())
				{
					indicePieceSelectJ1--;
				}
				for(int newColonneX = 1; newColonneX <= nbrColonne; newColonneX++)
				{
					for(int newLigneY = 1; newLigneY <= nbrLigne; newLigneY++)
					{
						if(pieceJ1.get(indicePieceSelectJ1).regleMove(newColonneX, newLigneY) == true)
						{
							/*
							 * Deplacement possible
							 */
							g.drawOval( ((newColonneX - 1) * grille.espaceColonne) + (grille.espaceColonne / 2), ((newLigneY - 1) * grille.espaceLigne) + (grille.espaceLigne / 2), (grille.espaceColonne / 2), (grille.espaceLigne / 2) );
						}
					}
				}
				
				
			}
			else if( (partie.tourDeJeu == Equipe.ROUGE) && (pieceJ2.size() >= 1) )
			{
				g.setColor(Color.RED);
				/*
				 * Dessin des déplacements possibles
				 */
				
				/*
				 * Sécu quand une piece a été del
				 */
				while(indicePieceSelectJ2 >= pieceJ2.size())
				{
					indicePieceSelectJ2--;
				}
				for(int newColonneX = 1; newColonneX <= nbrColonne; newColonneX++)
				{
					for(int newLigneY = 1; newLigneY <= nbrLigne; newLigneY++)
					{
						if(pieceJ2.get(indicePieceSelectJ2).regleMove(newColonneX, newLigneY) == true)
						{
							/*
							 * Deplacement possible
							 */
							g.drawOval( ((newColonneX - 1) * grille.espaceColonne) + (grille.espaceColonne / 2), ((newLigneY - 1) * grille.espaceLigne) + (grille.espaceLigne / 2), (grille.espaceColonne / 2), (grille.espaceLigne / 2) );
						}
					}
				}
				
				
			}
		}
		
	}

}
