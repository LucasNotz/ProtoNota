package test;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import negocio.CEP;

class TestAPI {

	//Automatic test to see wheter or not the api is being called and returning a correct value
	//API link to be tested:https://viacep.com.br/ws/01001000/json/
	/*Expected result 
	 * {  "cep": "01001-000",  "logradouro": "Praça da Sé",  "complemento": "lado ímpar",  "unidade": "",  "bairro": "Sé",  "localidade": "São Paulo",  "uf": "SP",  "estado": "São Paulo",  "regiao": "Sudeste",  "ibge": "3550308",  "gia": "1004",  "ddd": "11",  "siafi": "7107"}

	 * */
	@Test
	void test() {
		String expectedOutput = "{  \"cep\": \"01001-000\",  \"logradouro\": \"Praça da Sé\",  \"complemento\": \"lado ímpar\",  \"unidade\": \"\",  \"bairro\": \"Sé\",  \"localidade\": \"São Paulo\",  \"uf\": \"SP\",  \"estado\": \"São Paulo\",  \"regiao\": \"Sudeste\",  \"ibge\": \"3550308\",  \"gia\": \"1004\",  \"ddd\": \"11\",  \"siafi\": \"7107\"}";
		String apiGetResult = "";
		try {
			apiGetResult = CEP.getCEP("01001000");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(apiGetResult);
		System.out.println(expectedOutput);
		Assert.assertEquals("Pass", apiGetResult, expectedOutput);
			
	}

}
