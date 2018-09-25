package mario;

public class main {

	private static Fenetre_ fenetre;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		fenetre = new Fenetre_();
		while(true)
		{
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fenetre.gestionDeplacementClavier();
			fenetre.repaint();
		}
	}

}
