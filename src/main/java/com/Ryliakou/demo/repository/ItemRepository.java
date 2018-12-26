package com.Ryliakou.demo.repository;
import com.Ryliakou.demo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {


}
