package com.Ryliakou.demo.repository;

import com.Ryliakou.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findOneById(Long id);

    List<Room> findAllByType(String type);

    List<Room> findAllByTypeAndStatus(String type, String status);

    public Room findRoomById(Long id);

}
