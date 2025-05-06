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

	/**
	 * Cria registros climáticos via API para testes
	 */
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

	/**
	 * Envia requisição POST
	 */
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

	/**
	 * Envia requisição GET
	 */
	public void enviarGet(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<>(headers);
		ultimaResposta = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
	}

	/**
	 * Envia requisição PUT
	 */
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

	/**
	 * Envia requisição DELETE
	 */
	public void enviarDelete(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<>(headers);
		ultimaResposta = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
	}

	/**
	 * Obtém o código de status da última resposta
	 */
	public int obterCodigoStatus() {
		return ultimaResposta.getStatusCodeValue();
	}

	/**
	 * Obtém a resposta como JSON
	 */
	public JsonNode obterRespostaComoJson() throws IOException {
		return objectMapper.readTree(ultimaResposta.getBody());
	}

	/**
	 * Verifica se a resposta contém os dados especificados
	 */
	public boolean respostaContemDados(Map<String, String> dados) throws IOException {
		JsonNode resposta = obterRespostaComoJson();

		for (Map.Entry<String, String> entry : dados.entrySet()) {
			String campo = entry.getKey();
			String valorEsperado = entry.getValue();

			if (!resposta.has(campo)) {
				return false;
			}

			String valorResposta = resposta.get(campo).asText();

			if (!valorResposta.equals(valorEsperado)) {
				if (campo.equals("nrTemperatura") || campo.equals("nrUmidade")) {
					// Para campos numéricos, compara com tolerância
					double valorRespostaNum = Double.parseDouble(valorResposta);
					double valorEsperadoNum = Double.parseDouble(valorEsperado);

					if (Math.abs(valorRespostaNum - valorEsperadoNum) > 0.001) {
						return false;
					}
				} else {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Verifica se a resposta tem um campo não nulo
	 */
	public boolean respostaTemCampoNaoNulo(String campo) throws IOException {
		JsonNode resposta = obterRespostaComoJson();
		return resposta.has(campo) && !resposta.get(campo).isNull();
	}

	/**
	 * Verifica se a resposta contém uma lista paginada
	 */
	public boolean respostaContemListaPaginada() throws IOException {
		JsonNode resposta = obterRespostaComoJson();
		return resposta.has("content") && resposta.has("pageable") &&
				resposta.has("totalElements") && resposta.has("totalPages");
	}

	/**
	 * Verifica se a resposta contém um objeto com ID específico
	 */
	public boolean respostaContemId(Long id) throws IOException {
		JsonNode resposta = obterRespostaComoJson();
		return resposta.has("idClima") && resposta.get("idClima").asLong() == id;
	}

	/**
	 * Verifica se a resposta contém uma mensagem de erro específica
	 */
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

	/**
	 * Valida a resposta contra um schema JSON
	 */
	public boolean validarRespostaContraSchema(String nomeSchema) {
		try {
			JsonNode resposta = obterRespostaComoJson();
			return schemaValidator.validarJson(resposta, nomeSchema);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao validar resposta contra schema: " + nomeSchema, e);
		}
	}

	/**
	 * Converte dados de string para tipos apropriados
	 */
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