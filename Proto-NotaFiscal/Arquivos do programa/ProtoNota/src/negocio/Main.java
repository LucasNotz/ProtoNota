package negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import apresentacao.TelaLogin;
import apresentacao.TelaRegistro;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		//looks
		for(LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
			System.out.println(lafInfo.getClassName());
		}
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Atribtos da função
		String infoSistema = "";
		
		//Descobrir diretório para poder criar um folder no lugar correto
		Process processo = Runtime.getRuntime().exec("pwd");
		infoSistema = printResults(processo);
		
		//Definir onde folder deve estar localizado
		Path caminhoProtoFolder = Paths.get(infoSistema + "/zProtoNotaFolder");
		System.out.println("Directory path: " + caminhoProtoFolder);
		System.out.println();
		
		//Verificar se esse folder não existe
		if (!Files.exists(caminhoProtoFolder)) {
			//Definir objeto do folder 
			File protoFolder = new File(caminhoProtoFolder.toString());
			protoFolder.mkdirs(); //Criar folder
			System.out.println("Folder criado: " + caminhoProtoFolder);
			System.out.println();
			
			if(protoFolder.exists()) {
				//Criar StateFile.txt
				File stateFile = new File(protoFolder.toString() + "/StateFile.txt");
				stateFile.createNewFile();
				System.out.println("Arquivo criado: " + stateFile);
				System.out.println();
				
				//Criar instanceFolder
				//File instanceFolder = new File(protoFolder.toString() + "/instanceFolder");
				//instanceFolder.mkdirs();
				//System.out.println("Folder criado: " + instanceFolder);
				//System.out.println();
			
				//Abrir tela de registro (TelaRegistro)
				new TelaRegistro(protoFolder.toString()).setVisible(true);
				
				}
			
		} else {
			//Se alguma coisa for alterada, no sentido de que foi deletado, ou nao funcionar o programa não vai abrir (estou com preguiça)
			new TelaLogin(caminhoProtoFolder.toString()).setVisible(true);;
		}
	}

	//https://stackabuse.com/executing-shell-commands-with-java/
	public static String printResults(Process process) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    String line = "";
	    while ((line = reader.readLine()) != null) {
	        System.out.println("System directory is: " + line);
	        System.out.println();
	        break;
	    }
		return line;
	}
}
