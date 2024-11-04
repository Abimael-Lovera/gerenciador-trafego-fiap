package com.fiap.gateway;

import com.fiap.acidente.model.Acidente;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {
    public void enviaNotificacaoDeAcidenteGrave(Acidente acidente) {
        //TODO este método poderia ser utilizado para fazer integracao com algum sistema da policia, bombeiros ou hospitais
        // para que fosse enviada uma notificação com detalhes como: horário, localização e fluxo de impacto
        // sempre que um acidente grave ocorrer
        System.out.println("Notificação enviada com sucesso");
    }
}
