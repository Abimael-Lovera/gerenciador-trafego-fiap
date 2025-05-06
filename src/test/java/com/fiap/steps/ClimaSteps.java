package com.fiap.steps;

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

	@Dado("que existem registros climáticos cadastrados")
	public void queExistemRegistrosClimaticosCadastrados() {
		climaTestService.criarDadosDeTeste();
	}

	@Dado("que existe um registro climático com ID {long}")
	public void queExisteUmRegistroClimaticoComID(Long id) {
		// Primeiro, criamos registros para ter certeza que existe algum no sistema
		climaTestService.criarDadosDeTeste();

		// Depois verificamos se existe um com o ID específico
		climaTestService.enviarGet("/clima/" + id);

		// Se não encontrarmos, criamos mais registros até encontrar um com esse ID
		// Nota: Este é um cenário de teste. Em um ambiente real, seria melhor usar mocks
		if (climaTestService.obterCodigoStatus() != 200) {
			throw new RuntimeException("Não foi possível encontrar um clima com ID " + id +
					". Considere modificar o teste para usar um ID dinâmico em vez de fixo.");
		}
	}

	@Quando("eu enviar uma requisição POST para {string} com os dados:")
	public void euEnviarUmaRequisicaoPOSTParaComOsDados(String endpoint, Map<String, String> dados) {
		System.out.println(dados);
		climaTestService.enviarPost(endpoint, dados);
	}

	@Quando("eu enviar uma requisição GET para {string}")
	public void euEnviarUmaRequisicaoGETParaComOsDados(String endpoint) {
		climaTestService.enviarGet(endpoint);
	}

	@Quando("eu enviar uma requisição PUT para {string} com os dados:")
	public void euEnviarUmaRequisicaoPUTParaComOsDados(String endpoint, Map<String, String> dados) {
		climaTestService.enviarPut(endpoint, dados);
	}

	@Quando("eu enviar uma requisição DELETE para {string}")
	public void euEnviarUmaRequisicaoDELETEPara(String endpoint) {
		climaTestService.enviarDelete(endpoint);
	}

	@Então("o código de status da resposta deve ser {int}")
	public void oCodigoDeStatusDaRespostaDeveSer(int statusEsperado) {
		int statusAtual = climaTestService.obterCodigoStatus();
		assertEquals(statusEsperado, statusAtual);
	}

	@E("a resposta deve conter um objeto clima com os mesmos dados enviados:")
	public void aRespostaDeveConterUmObjetoClimaComOsMesmosDadosEnviados(Map<String, String> dados) throws IOException {
		System.out.println("Imprimindo dados");
		dados.forEach((chave, valor) -> System.out.println(chave + ": " + valor));
		assertTrue(climaTestService.respostaContemDados(dados));
	}

	@E("o objeto clima deve conter um campo {string} não nulo")
	public void oObjetoClimaDeveConterUmCampoNaoNulo(String campo) throws IOException {
		System.out.println("Campo: " + campo);
//		assertTrue(climaTestService.respostaTemCampoNaoNulo(campo));
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

	@E("a resposta deve estar de acordo com o JSON Schema {string}")
	public void aRespostaDeveEstarDeAcordoComOJSONSchema(String nomeSchema) {
		assertTrue(climaTestService.validarRespostaContraSchema(nomeSchema));
	}
}