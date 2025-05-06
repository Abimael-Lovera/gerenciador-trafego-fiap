package com.fiap.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

@Component
public class JsonSchemaValidator {

	private static final String SCHEMA_BASE_PATH = "src/test/resources/schemas/";

	public  boolean validarJson(JsonNode jsonNode, String schemaName) {
		try {
			String schemaContent = lerArquivo(schemaName);
			JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
			JsonSchema schema = factory.getSchema(schemaContent);
			Set<ValidationMessage> validationResult = schema.validate(jsonNode);
			return validationResult.isEmpty();
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public String validarJsonComDetalhes(JsonNode jsonNode, String schemaName) {
		try {
			String schemaContent = lerArquivo(schemaName);
			JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
			JsonSchema schema = factory.getSchema(schemaContent);
			Set<ValidationMessage> validationResult = schema.validate(jsonNode);
			if (validationResult.isEmpty()) {
				return "JSON válido";
			}
			StringBuilder sb = new StringBuilder();
			for (ValidationMessage message : validationResult) {
				sb.append(message.getMessage()).append("\n");
			}
			return sb.toString();
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("Erro ao validar JSON com schema: " + schemaName, e);
		}
	}

	private static String lerArquivo(String schemaName) {
		try {
			return new String(Files.readAllBytes(Paths.get(SCHEMA_BASE_PATH+ schemaName)));
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("Erro ao ler arquivo: " + schemaName, e);
		}
	}

}
