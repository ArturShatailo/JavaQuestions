package com.study.javaquestions.service.client;

import com.study.javaquestions.domain.Client;
import com.study.javaquestions.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class ClientServiceBean {

    private final ClientRepository clientRepository;

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    public Client createFromUser(User user, String chatId) {

        Client client = clientRepository.findClientByChatId(chatId)
                .orElse(new Client());

        client.setChatId(chatId);
        client.setFirstname(user.getFirstName());
        client.setLastname(user.getLastName());
        client.setUsername(user.getUserName());
        return create(client);
    }

    public Client getByChatID(String chatId) {
        return clientRepository.findClientByChatId(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with chatId = " + chatId));
    }
}
