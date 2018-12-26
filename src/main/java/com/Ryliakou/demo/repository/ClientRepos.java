package com.Ryliakou.demo.repository;

import com.Ryliakou.demo.entity.Client;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ClientRepos extends CrudRepository<Client, Long> {
    @Transactional
    Long deleteAllById(Long id);
}
