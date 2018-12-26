package com.Ryliakou.demo.utils;

import com.Ryliakou.demo.config.WebSecurityConfig;
import com.Ryliakou.demo.entity.Client;
import com.Ryliakou.demo.entity.ClientOrder;
import com.Ryliakou.demo.entity.Room;
import com.Ryliakou.demo.repository.ClientRepository;
import com.Ryliakou.demo.repository.OrderRepository;
import com.Ryliakou.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UtilLogic {

    @Autowired
    ClientRepository clientRepository;

    public static String getCurrentUserName(){
        WebSecurityConfig webSecurityConfig = new WebSecurityConfig();
        return webSecurityConfig.getCurrentUsername();
    }

    public static boolean checkEmptyRooms(String checkin, String checkout, Client client,
                                          RoomRepository roomRepository, OrderRepository orderRepository,
                                          Map<String,Object> model, String type, String status){

        List<Room> rooms = roomRepository.findAllByTypeAndStatus(type, status);

        if(rooms != null && rooms.size() > 0){
            ClientOrder clientOrderr = new ClientOrder(checkin,checkout,client,rooms.get(0));
            orderRepository.save(clientOrderr);
            model.put("mess","Бронь выполнена успешно");
            Room room = roomRepository.findRoomById(rooms.get(0).getId());
            room.setStatus("ordered");
            roomRepository.save(room);
            return true;
        }
        return false;
    }

    public static void disposeSession(){
        WebSecurityConfig webSecurityConfig = new WebSecurityConfig();
        webSecurityConfig.disposeSession();
    }

    public static boolean checkRoom(List<ClientOrder> orders, String checkin, String checkout, DateFormat format,
                                    OrderRepository orderRepository, Map<String,Object> model,
                                    ClientRepository clientRepository) throws ParseException {
        ClientOrder clientOrder;
        Room emptyRoom;
        boolean check = false;
        Date in = format.parse(checkin);
        Date out = format.parse(checkout);

        System.out.println("working.. ");
        for (int i = 0 ; i < orders.size(); i++) {
            System.out.println("!!!!!!!!!!!!! " + orders.get(i));
        /*    if (orders.get(i).getCheckin() != null && out.before(format.parse(orders.get(i).getCheckin()))) {

                emptyRoom = orders.get(i).getRoom();
                clientOrder = new ClientOrder(checkin, checkout,
                        clientRepository.findClientByLogin(getCurrentUserName()), emptyRoom);

                orderRepository.save(clientOrder);
                model.put("mess", "Бронь выполнена успешно");
                return true;
            }*/

          /*  if (orders.get(i).getCheckin() != null && orders.get(j).getCheckout() != null
                    && in.after(format.parse(orders.get(i).getCheckout()))
                    && out.before(format.parse(orders.get(j).getCheckin()))) {
                emptyRoom = orders.get(i).getRoom();
                clientOrder = new ClientOrder(checkin, checkout,
                        clientRepository.findClientByLogin(getCurrentUserName()), emptyRoom);

                orderRepository.save(clientOrder);
                model.put("mess", "Бронь выполнена успешно");
                return true;
            }*/
/*
            if (orders.get(i).getCheckout() != null && orders.get(j) == null &&
                    in.after(format.parse(orders.get(i).getCheckout()))) {
                emptyRoom = orders.get(i).getRoom();
                clientOrder = new ClientOrder(checkin, checkout,
                        clientRepository.findClientByLogin(getCurrentUserName()), emptyRoom);

                orderRepository.save(clientOrder);
                model.put("mess", "Бронь выполнена успешно");
                return true;
            }
        }*/
            if (in.after(format.parse(orders.get(i).getCheckout())) && ((i + 1) <= orders.size())) {
                emptyRoom = orders.get(i).getRoom();
                clientOrder = new ClientOrder(checkin, checkout,
                        clientRepository.findClientByLogin(getCurrentUserName()), emptyRoom);

                orderRepository.save(clientOrder);
                model.put("mess", "Бронь выполнена успешно");
                return true;
            }
            if (in.before(format.parse(orders.get(i).getCheckin())) && ((i - 1) < 0)) {
                emptyRoom = orders.get(i).getRoom();
                clientOrder = new ClientOrder(checkin, checkout,
                        clientRepository.findClientByLogin(getCurrentUserName()), emptyRoom);

                orderRepository.save(clientOrder);
                model.put("mess", "Бронь выполнена успешно");
                return true;
            }

            if ((i + 1) <= orders.size() && in.after(format.parse(orders.get(i).getCheckout()))
                    && out.before(format.parse(orders.get(i + 1).getCheckin()))){
                emptyRoom = orders.get(i).getRoom();
                clientOrder = new ClientOrder(checkin, checkout,
                        clientRepository.findClientByLogin(getCurrentUserName()), emptyRoom);

                orderRepository.save(clientOrder);
                model.put("mess", "Бронь выполнена успешно");
                return true;
            }
        }
        return check;
    }
}
