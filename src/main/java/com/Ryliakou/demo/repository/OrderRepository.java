package com.Ryliakou.demo.repository;
import com.Ryliakou.demo.entity.Client;
import com.Ryliakou.demo.entity.ClientOrder;
import com.Ryliakou.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<ClientOrder, Long> {

    public List<ClientOrder> findAllByClientId(Long id);

    public List<ClientOrder> findAllByCheckinAndCheckout(Date checking, Date checkout);

    public ClientOrder findOneById(Long id);

    public List<ClientOrder> findAllByRoomId(Long id);

    public ClientOrder findOneByRoomId(Long roomId);

    public ClientOrder findClientOrderByRoom(Room room);

    public List<ClientOrder> findAllByClient(Client client);


}
