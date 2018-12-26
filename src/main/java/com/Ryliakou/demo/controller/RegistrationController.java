package com.Ryliakou.demo.controller;

import com.Ryliakou.demo.entity.Client;
import com.Ryliakou.demo.entity.Role;
import com.Ryliakou.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Client client, Map<String,Object> model){

        Client clientFromDb=clientRepository.findByLogin(client.getLogin());

        if(clientFromDb!=null){
            model.put("message","User exists");
            return "registration";
        }

        client.setActive(true);
        client.setRoles(Collections.singleton(Role.Client));

        clientRepository.save(client);

        return "redirect:/login";
    }
}
