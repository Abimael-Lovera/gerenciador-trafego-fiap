package com.fiap.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fiap.service.ClimaTestService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClimaSteps {

	@Autowired
	private ClimaTestService climaTestService;

	private Long ultimoIdClimaCriado;

	@Dado("que existem registros climáticos cadastrados")
	public void queExistemRegistrosClimaticosCadastrados() {
		climaTestService.criarDadosDeTeste();
	}

	@Dado("que existe um registro climático com ID {long}")
	public void queExisteUmRegistroClimaticoComID(Long id) {
		climaTestService.criarDadosDeTeste();

		climaTestService.enviarGet("/clima/" + id);

		if (climaTestService.obterCodigoStatus() != 200) {
			throw new RuntimeException("Não foi possível encontrar um clima com ID " + id +
					". Considere modificar o teste para usar um ID dinâmico em vez de fixo.");
		}
	}

	@Dado("que o registro climático com ID {long} foi excluído")
	public void queORegistroClimaticoComIDFoiExcluido(Long id) {
		climaTestService.enviarGet("/clima/" + id);

		if (climaTestService.obterCodigoStatus() == 200) {
			climaTestService.enviarDelete("/clima/" + id);

			if (climaTestService.obterCodigoStatus() != 200) {
				throw new RuntimeException("Não foi possível excluir o clima com ID " + id);
			}
		}
	}

	@Quando("eu enviar uma requisição POST para {string} com os dados:")
	public void euEnviarUmaRequisicaoPOSTParaComOsDados(String endpoint, Map<String, String> dados) {
		System.out.println("Enviando POST para " + endpoint + " com dados: " + dados);
		climaTestService.enviarPost(endpoint, dados);
		try {
			JsonNode resposta = climaTestService.obterRespostaComoJson();
			if (resposta.has("idClima")) {
				ultimoIdClimaCriado = resposta.get("idClima").asLong();
				System.out.println("ID do clima criado capturado: " + ultimoIdClimaCriado);
			}
		} catch (Exception e) {
			System.err.println("Erro ao capturar ID do clima criado: " + e.getMessage());
		}
	}

	@Quando("eu enviar uma requisição GET para {string}")
	public void euEnviarUmaRequisicaoGETParaComOsDados(String endpoint) {
		if (endpoint.contains("{idClima}")) {
			if (ultimoIdClimaCriado == null) {
				throw new RuntimeException("Não há ID de clima disponível. Certifique-se de criar um clima antes de referenciá-lo.");
			}
			String endpointAjustado = endpoint.replace("{idClima}", ultimoIdClimaCriado.toString());
			System.out.println("Substituindo {idClima} por " + ultimoIdClimaCriado + " no endpoint: " + endpoint + " -> " + endpointAjustado);
			endpoint = endpointAjustado;
		}
		climaTestService.enviarGet(endpoint);
	}

	@Quando("eu enviar uma requisição PUT para {string} com os dados:")
	public void euEnviarUmaRequisicaoPUTParaComOsDados(String endpoint, Map<String, String> dados) {
		if (endpoint.contains("{idClima}")) {
			if (ultimoIdClimaCriado == null) {
				throw new RuntimeException("Não há ID de clima disponível. Certifique-se de criar um clima antes de referenciá-lo.");
			}
			String endpointAjustado = endpoint.replace("{idClima}", ultimoIdClimaCriado.toString());
			System.out.println("Substituindo {idClima} por " + ultimoIdClimaCriado + " no endpoint: " + endpoint + " -> " + endpointAjustado);
			endpoint = endpointAjustado;
		}
		climaTestService.enviarPut(endpoint, dados);
	}

	@Quando("eu enviar uma requisição DELETE para {string}")
	public void euEnviarUmaRequisicaoDELETEPara(String endpoint) {
		if (endpoint.contains("{idClima}")) {
			if (ultimoIdClimaCriado == null) {
				throw new RuntimeException("Não há ID de clima disponível. Certifique-se de criar um clima antes de referenciá-lo.");
			}
			String endpointAjustado = endpoint.replace("{idClima}", ultimoIdClimaCriado.toString());
			System.out.println("Substituindo {idClima} por " + ultimoIdClimaCriado + " no endpoint: " + endpoint + " -> " + endpointAjustado);
			endpoint = endpointAjustado;
		}
		climaTestService.enviarDelete(endpoint);
	}

	@Então("o código de status da resposta deve ser {int}")
	public void oCodigoDeStatusDaRespostaDeveSer(int statusEsperado) {
		int statusAtual = climaTestService.obterCodigoStatus();
		assertEquals(statusEsperado, statusAtual);
	}

	@E("a resposta deve conter um objeto clima com os mesmos dados enviados:")
	public void aRespostaDeveConterUmObjetoClimaComOsMesmosDadosEnviados(Map<String, String> dados) throws IOException {
		System.out.println("Verificando dados na resposta:");
		dados.forEach((chave, valor) -> System.out.println(chave + ": " + valor));
		assertTrue(climaTestService.respostaContemDados(dados));
	}

	@E("a resposta deve conter um objeto clima com os dados atualizados:")
	public void aRespostaDeveConterUmObjetoClimaComOsDadosAtualizados(Map<String, String> dados) throws IOException {
		System.out.println("Verificando dados atualizados na resposta:");
		dados.forEach((chave, valor) -> System.out.println(chave + ": " + valor));
		assertTrue(climaTestService.respostaContemDados(dados));
	}

	@E("o objeto clima deve conter um campo {string} não nulo")
	public void oObjetoClimaDeveConterUmCampoNaoNulo(String campo) throws IOException {
		System.out.println("Verificando se campo não é nulo: " + campo);
		assertTrue(climaTestService.respostaTemCampoNaoNulo(campo));

		if (campo.equals("idClima")) {
			JsonNode resposta = climaTestService.obterRespostaComoJson();
			ultimoIdClimaCriado = resposta.get("idClima").asLong();
			System.out.println("Capturado ID do clima: " + ultimoIdClimaCriado);
		}
	}

	@E("a resposta deve conter uma lista paginada de climas")
	public void aRespostaDeveConterUmaListaPaginadaDeClimas() throws IOException {
		assertTrue(climaTestService.respostaContemListaPaginada());
	}

	@E("a resposta deve conter um objeto clima com ID {long}")
	public void aRespostaDeveConterUmObjetoClimaComID(Long id) throws IOException {
		assertTrue(climaTestService.respostaContemId(id));
	}

	@E("a resposta deve conter uma mensagem de erro informando que o clima não foi encontrado")
	public void aRespostaDeveConterUmaMensagemDeErroInformandoQueOClimaNaoFoiEncontrado() throws IOException {
		assertTrue(climaTestService.respostaContemErro("Clima não encontrado"));
	}

	@E("a resposta deve conter erros de validação")
	public void aRespostaDeveConterErrosDeValidacao() throws IOException {
		assertTrue(climaTestService.respostaContemErrosDeValidacao());
	}

	@E("a resposta deve conter o objeto clima que foi excluído")
	public void aRespostaDeveConterOObjetoClimaQueFoiExcluido() throws IOException {
		assertTrue(climaTestService.respostaContemObjetoClimaValido());
	}

	@E("a resposta deve estar de acordo com o JSON Schema {string}")
	public void aRespostaDeveEstarDeAcordoComOJSONSchema(String nomeSchema) {
		assertTrue(climaTestService.validarRespostaContraSchema(nomeSchema));
	}
}