package com.Ryliakou.demo.repository;
import com.Ryliakou.demo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long>{

    public List<Client> findAllById(Long id);
    public Client findClientById(Long id);
    public Client findClientByEmail(String email);
    public List<Client> findAllByEmail(String email);
    public void deleteClientById(Long id);
    public void deleteAllById(Long id);
    Client findByLogin(String login);
    Client findClientByLoginAndPass(String login,String pass);
    Client findClientByLogin(String login);



}
