package com.Ryliakou.demo.service;

import com.Ryliakou.demo.entity.Client;
import com.Ryliakou.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }
    public Client getClientById(Long id){return  clientRepository.findClientById(id);}
    public Client saveClient(Client client){
        return clientRepository.save(client);
    }

}
