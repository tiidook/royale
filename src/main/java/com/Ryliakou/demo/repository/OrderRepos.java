package com.Ryliakou.demo.repository;

import com.Ryliakou.demo.entity.ClientOrder;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface OrderRepos extends CrudRepository<ClientOrder, Long> {
    @Transactional
    Long deleteAllById(Long id);

}
