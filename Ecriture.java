package mario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ecriture implements Valeurs{
	
	public File file;
	
	public CoucheNeuronale coucheNeuronale;
	
	public Ecriture(CoucheNeuronale coucheNeuronale,String nameFile) {
		this.coucheNeuronale = coucheNeuronale;
		this.file = new File(pathResult + nameFile);
	}
	
	public void start()
	{
		String str;
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			str = coucheNeuronale.toString();
			
			fw.write(str);
			
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
