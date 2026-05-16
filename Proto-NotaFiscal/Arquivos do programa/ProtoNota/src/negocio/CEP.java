package negocio;

import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;


public class CEP {
	
	public static String getCEP(String cepText) throws IOException {
		if (cepText.length() != 8) {
			return "error";
		}
		if (cepText.equals("")) {
			return "error";
		}
		if (Pattern.matches("[a-zA-Z]+", cepText) == true) {
			return "error";
		}
		
		URL url = new URL("https://viacep.com.br/ws/" + cepText + "/json/");
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		
		int responseCode = conn.getResponseCode();
		
		if(responseCode != 200) {
			throw new RuntimeException("HttpResponseCode" + responseCode);
		} else {
			StringBuilder informationString = new StringBuilder();
			Scanner scanner = new Scanner(url.openStream());
			
			while(scanner.hasNext()) {
				informationString.append(scanner.nextLine());
			}
			scanner.close();
			
			return informationString.toString();
		}
	}
	
	public static String[] processViaCEP(String json) {
		String[] info = CEP.seperateJSONby(json, ',', 8);
		
		//logradouro
		String logadouro = info[0];
		//complemento
		//bairro
		//localidade
		//uf
		//estado

		return info;
		
	}
	
	public static String[] seperateJSONby(String json, char c, int amount) {
		String[] separated = new String[amount];
		int temp = 0;
		int counter = 0;
		for (int i = 0; i < json.length(); i++) {
			if (c == json.charAt(i)) {
				separated[counter] = json.substring(temp + 1, i-1).strip();
				counter++;
				if (counter == amount -1) break;
				temp = i;
			}
		}
		
		return separated;
	}
}

