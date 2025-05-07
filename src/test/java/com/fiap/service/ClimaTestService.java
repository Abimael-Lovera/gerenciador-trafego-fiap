package com.fiap.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.util.JsonSchemaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class ClimaTestService {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JsonSchemaValidator schemaValidator;

	private ResponseEntity<String> ultimaResposta;


	public void criarDadosDeTeste() {
		Map<String, Object> clima1 = new HashMap<>();
		clima1.put("dsCondicao", "Ensolarado");
		clima1.put("nrTemperatura", 30.0);
		clima1.put("nrUmidade", 60.0);
		clima1.put("dtRegistro", LocalDate.now().toString());

		Map<String, Object> clima2 = new HashMap<>();
		clima2.put("dsCondicao", "Nublado");
		clima2.put("nrTemperatura", 25.0);
		clima2.put("nrUmidade", 70.0);
		clima2.put("dtRegistro", LocalDate.now().minusDays(1).toString());

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> request1 = new HttpEntity<>(objectMapper.writeValueAsString(clima1), headers);
			restTemplate.exchange("/clima", HttpMethod.POST, request1, String.class);

			HttpEntity<String> request2 = new HttpEntity<>(objectMapper.writeValueAsString(clima2), headers);
			restTemplate.exchange("/clima", HttpMethod.POST, request2, String.class);
		} catch (Exception e) {
			System.err.println("Erro ao criar dados de teste: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Erro ao criar dados de teste", e);
		}
	}


	public void enviarPost(String url, Map<String, String> dados) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Map<String, Object> dadosConvertidos = converterDados(dados);
			String              jsonBody         = objectMapper.writeValueAsString(dadosConvertidos);
			System.out.println("Enviando POST para: " + url);
			System.out.println("Corpo da requisição: " + jsonBody);

			HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
			ultimaResposta = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

			System.out.println("Resposta: " + ultimaResposta.getStatusCode().value() + " - " + ultimaResposta.getBody());
		} catch (Exception e) {
			System.err.println("Erro ao enviar requisição POST: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Erro ao enviar requisição POST", e);
		}
	}


	public void enviarGet(String url) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			System.out.println("Enviando GET para: " + url);

			HttpEntity<String> request = new HttpEntity<>(headers);
			ultimaResposta = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

			System.out.println("Resposta: " + ultimaResposta.getStatusCode().value() + " - " + ultimaResposta.getBody());
		} catch (Exception e) {
			System.err.println("Erro ao enviar requisição GET: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Erro ao enviar requisição GET", e);
		}
	}


	public void enviarPut(String url, Map<String, String> dados) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Map<String, Object> dadosConvertidos = converterDados(dados);
			String              jsonBody         = objectMapper.writeValueAsString(dadosConvertidos);
			System.out.println("Enviando PUT para: " + url);
			System.out.println("Corpo da requisição: " + jsonBody);

			HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
			ultimaResposta = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

			System.out.println("Resposta: " + ultimaResposta.getStatusCode().value() + " - " + ultimaResposta.getBody());
		} catch (Exception e) {
			System.err.println("Erro ao enviar requisição PUT: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Erro ao enviar requisição PUT", e);
		}
	}


	public void enviarDelete(String url) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			System.out.println("Enviando DELETE para: " + url);
	
			HttpEntity<String> request = new HttpEntity<>(headers);
			ultimaResposta = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
	
			System.out.println("Resposta: " + ultimaResposta.getStatusCode().value() + " - " + ultimaResposta.getBody());
		} catch (Exception e) {
			System.err.println("Erro ao enviar requisição DELETE: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Erro ao enviar requisição DELETE", e);
		}
	}

	public int obterCodigoStatus() {
		return ultimaResposta.getStatusCode().value();
	}


	public JsonNode obterRespostaComoJson() throws IOException {
		try {
			return objectMapper.readTree(ultimaResposta.getBody());
		} catch (Exception e) {
			System.err.println("Erro ao converter resposta para JSON: " + ultimaResposta.getBody());
			e.printStackTrace();
			throw e;
		}
	}


	public boolean respostaContemDados(Map<String, String> dados) throws IOException {
		JsonNode resposta = obterRespostaComoJson();
		System.out.println("Verificando dados na resposta: " + resposta);

		for (Map.Entry<String, String> entry : dados.entrySet()) {
			String campo = entry.getKey();
			String valorEsperado = entry.getValue();

			if (!resposta.has(campo)) {
				System.out.println("Campo não encontrado na resposta: " + campo);
				return false;
			}

			String valorAtual = resposta.get(campo).asText();
			if (!valorAtual.equals(valorEsperado)) {
				System.out.println("Valor diferente para campo " + campo + 
						": esperado=" + valorEsperado + ", atual=" + valorAtual);
				return false;
			}
		}

		return true;
	}

	public boolean respostaTemCampoNaoNulo(String campo) throws IOException {
		JsonNode resposta = obterRespostaComoJson();
		System.out.println("Verificando campo não nulo: " + campo + " na resposta: " + resposta);
		boolean resultado = resposta.has(campo) && !resposta.get(campo).isNull();
		System.out.println("Resultado da verificação: " + resultado);
		return resultado;
	}


	public boolean respostaContemListaPaginada() throws IOException {
		JsonNode resposta = obterRespostaComoJson();
		boolean resultado = resposta.has("content") && resposta.has("pageable") &&
				resposta.has("totalElements") && resposta.has("totalPages");
		System.out.println("Verificando se resposta contém lista paginada: " + resultado);
		return resultado;
	}

	public boolean respostaContemId(Long id) throws IOException {
		JsonNode resposta  = obterRespostaComoJson();
		boolean  resultado = resposta.has("idClima") && resposta.get("idClima").asLong() == id;
		System.out.println("Verificando se resposta contém ID " + id + ": " + resultado);
		return resultado;
	}


	public boolean respostaContemErro(String mensagemParcial) throws IOException {
		JsonNode resposta = obterRespostaComoJson();
		System.out.println("Verificando se resposta contém erro '" + mensagemParcial + "' em: " + resposta);

		if (resposta.has("title")) {
			String  titulo    = resposta.get("title").asText();
			boolean resultado = titulo.contains(mensagemParcial);
			System.out.println("Verificando no título '" + titulo + "': " + resultado);
			if (resultado) return true;
		}

		if (resposta.has("detail")) {
			String  detalhe   = resposta.get("detail").asText();
			boolean resultado = detalhe.contains(mensagemParcial);
			System.out.println("Verificando nos detalhes '" + detalhe + "': " + resultado);
			return resultado;
		}

		return false;
	}

	public boolean respostaContemErrosDeValidacao() throws IOException {
		JsonNode resposta = obterRespostaComoJson();
		System.out.println("Verificando se resposta contém erros de validação: " + resposta);

		if (resposta.has("errors") && resposta.get("errors").isArray() && resposta.get("errors").size() > 0) {
			return true;
		}

		if (resposta.has("violations") && resposta.get("violations").isArray() && resposta.get("violations").size() > 0) {
			return true;
		}

		if (resposta.has("fieldErrors") && resposta.get("fieldErrors").isArray() && resposta.get("fieldErrors").size() > 0) {
			return true;
		}

		if (resposta.has("message") && resposta.get("message").asText().contains("validation")) {
			return true;
		}

		if (resposta.has("detalhes") && resposta.get("detalhes").isObject() && resposta.get("detalhes").size() > 0) {
			return true;
		}

		if (resposta.has("title") && resposta.get("title").asText().contains("validação")) {
			return true;
		}

		if (resposta.isObject()) {
			Iterator<String> fieldNames = resposta.fieldNames();
			while (fieldNames.hasNext()) {
				String fieldName = fieldNames.next();
				if (fieldName.contains("error") || fieldName.contains("violation")) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean respostaContemObjetoClimaValido() throws IOException {
		JsonNode resposta = obterRespostaComoJson();
		System.out.println("Verificando se resposta contém um objeto clima válido: " + resposta);

		return resposta.has("idClima") &&
				resposta.has("dsCondicao") &&
				resposta.has("nrTemperatura") &&
				resposta.has("nrUmidade") &&
				resposta.has("dtRegistro");
	}

	public boolean validarRespostaContraSchema(String nomeSchema) {
		try {
			JsonNode resposta = obterRespostaComoJson();
			System.out.println("Validando resposta contra schema: " + nomeSchema);
			boolean resultado = schemaValidator.validarJson(resposta, nomeSchema);

			if (!resultado) {
				String detalhes = schemaValidator.validarJsonComDetalhes(resposta, nomeSchema);
				System.out.println("Erro na validação do schema: " + detalhes);
			}

			return resultado;
		} catch (Exception e) {
			System.err.println("Erro ao validar resposta contra schema: " + nomeSchema);
			e.printStackTrace();
			throw new RuntimeException("Erro ao validar resposta contra schema: " + nomeSchema, e);
		}
	}

	private Map<String, Object> converterDados(Map<String, String> dados) {
		Map<String, Object> resultado = new HashMap<>();

		for (Map.Entry<String, String> entry : dados.entrySet()) {
			String chave = entry.getKey();
			String valor = entry.getValue();

			if (chave.equals("nrTemperatura") || chave.equals("nrUmidade")) {
				resultado.put(chave, Double.parseDouble(valor));
			} else {
				resultado.put(chave, valor);
			}
		}

		return resultado;
	}
}