package com.Ryliakou.demo.repository;

import com.Ryliakou.demo.entity.Client;
import com.Ryliakou.demo.entity.ClientReview;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository  extends JpaRepository<ClientReview, Long>{
      public List<ClientReview> findAllByClient(Client client);
}
