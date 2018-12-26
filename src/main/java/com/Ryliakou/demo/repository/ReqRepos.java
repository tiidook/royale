package com.Ryliakou.demo.repository;

import com.Ryliakou.demo.entity.Req;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ReqRepos extends CrudRepository<Req, Long> {
    @Transactional
    Long deleteAllById(Long id);
}
