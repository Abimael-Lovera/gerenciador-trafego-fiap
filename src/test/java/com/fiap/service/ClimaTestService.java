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

			// Cria o primeiro registro
			HttpEntity<String> request1 = new HttpEntity<>(objectMapper.writeValueAsString(clima1), headers);
			restTemplate.exchange("/clima", HttpMethod.POST, request1, String.class);

			// Cria o segundo registro
			HttpEntity<String> request2 = new HttpEntity<>(objectMapper.writeValueAsString(clima2), headers);
			restTemplate.exchange("/clima", HttpMethod.POST, request2, String.class);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar dados de teste", e);
		}
	}


	public void enviarPost(String url, Map<String, String> dados) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Map<String, Object> dadosConvertidos = converterDados(dados);

			HttpEntity<String> request = new HttpEntity<>(
					objectMapper.writeValueAsString(dadosConvertidos), headers);

			ultimaResposta = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao enviar requisição POST", e);
		}
	}


	public void enviarGet(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<>(headers);
		ultimaResposta = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
	}


	public void enviarPut(String url, Map<String, String> dados) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Map<String, Object> dadosConvertidos = converterDados(dados);

			HttpEntity<String> request = new HttpEntity<>(
					objectMapper.writeValueAsString(dadosConvertidos), headers);

			ultimaResposta = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao enviar requisição PUT", e);
		}
	}


	public void enviarDelete(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<>(headers);
		ultimaResposta = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
	}

	public int obterCodigoStatus() {
		return ultimaResposta.getStatusCode().value();
	}


	public JsonNode obterRespostaComoJson() throws IOException {
		return objectMapper.readTree(ultimaResposta.getBody());
	}


	public boolean respostaContemDados(Map<String, String> dados) throws IOException {
		JsonNode resposta = obterRespostaComoJson();
		dados.forEach((chave, valor) -> System.out.println(chave + ": " + valor));
		for (Map.Entry<String, String> entry : dados.entrySet()) {
			String chave = entry.getKey();
			String valor = entry.getValue();

			if (!resposta.has(chave) || !valor.equals(resposta.get(chave).asText())) {
				return false;
			}
		}
		return true;
	}

	public boolean respostaTemCampoNaoNulo(String campo) throws IOException {
		System.out.println("[ClimaService]Campo: " + campo);
		JsonNode resposta = obterRespostaComoJson();
		System.out.println("[ClimaService]Resposta: " + resposta);
		return resposta.has(campo) && !resposta.get(campo).isNull();
	}


	public boolean respostaContemListaPaginada() throws IOException {
		JsonNode resposta = obterRespostaComoJson();
		return resposta.has("content") && resposta.has("pageable") &&
				resposta.has("totalElements") && resposta.has("totalPages");
	}

	public boolean respostaContemId(Long id) throws IOException {
		JsonNode resposta = obterRespostaComoJson();
		return resposta.has("idClima") && resposta.get("idClima").asLong() == id;
	}


	public boolean respostaContemErro(String mensagemParcial) throws IOException {
		JsonNode resposta = obterRespostaComoJson();

		if (resposta.has("title")) {
			return resposta.get("title").asText().contains(mensagemParcial);
		}

		if (resposta.has("detail")) {
			return resposta.get("detail").asText().contains(mensagemParcial);
		}

		return false;
	}

	public boolean validarRespostaContraSchema(String nomeSchema) {
		try {
			JsonNode resposta = obterRespostaComoJson();
			return schemaValidator.validarJson(resposta, nomeSchema);
		} catch (Exception e) {
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