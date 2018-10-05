package mario;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bloc implements Valeurs{
	
	public boolean isBloquant;
	public int posX;
	public int posY;
	public int hauteur;
	public int longueur;
	public boolean isFlag;
	
	public BufferedImage img;
	
	public Bloc(int posX,int posY) {
		this.isBloquant = true;
		this.posX = posX;
		this.posY = posY;
		this.isFlag = false;
		
		try {
			this.img = ImageIO.read(new File("C:\\Users\\Dapawan\\eclipse-workspace\\IA\\src\\mario\\images\\sol_base.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*this.hauteur = hauteurBloc;
		this.longueur = longueurBloc;*/
		
		this.hauteur = img.getHeight();
		this.longueur = img.getWidth();
	}
	
	public Bloc(int posX,int posY,boolean isFlag) {
		this.isBloquant = false;
		this.posX = posX;
		this.posY = posY;
		this.isFlag = isFlag;
		try {
			this.img = ImageIO.read(new File("C:\\Users\\Dapawan\\eclipse-workspace\\IA\\src\\mario\\images\\flag.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*this.hauteur = hauteurBloc;
		this.longueur = longueurBloc;*/
		
		this.hauteur = img.getHeight();
		this.longueur = img.getWidth();
		
		//Permet de placer au-dessus
		this.posY = (this.posY - this.hauteur);
	}

}
