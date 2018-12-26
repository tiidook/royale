package com.Ryliakou.demo.controller;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import com.Ryliakou.demo.config.WebSecurityConfig;
import com.Ryliakou.demo.entity.*;
import com.Ryliakou.demo.repository.*;
import com.Ryliakou.demo.service.ClientService;
import com.Ryliakou.demo.service.ItemService;
import com.Ryliakou.demo.service.OrderService;
import com.Ryliakou.demo.utils.UtilLogic;
import org.apache.catalina.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Order;
import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@Controller
public class ShopController {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ClientService clientService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientRepos clientRepos;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReqRepository reqRepository;

    @Autowired
    ReqRepos reqRepos;

    @Autowired
    OrderRepos orderRepos;


    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @GetMapping("/")
    public  String hi(Map<String, Object> model) {
        WebSecurityConfig webSecurityConfig=new WebSecurityConfig();
        String log=webSecurityConfig.getCurrentUsername();
        Client client=clientRepository.findClientByLogin(log);
        Set<Role> roles=client.getRoles();
        System.out.println(roles.toString());
        if(roles.toString().equals("[Admin]")){
            return "redirect:/adminpage";
        }

        List<ClientReview> clientReviews= reviewRepository.findAll();

        if(clientReviews.size()==0){
            return "index";
        }

        model.put("reviews",clientReviews);


        return "index";
    }


    @GetMapping("/adminpage")
    public String adminpage(Map<String,Object> model){
        WebSecurityConfig webSecurityConfig=new WebSecurityConfig();
        String log=webSecurityConfig.getCurrentUsername();

        Client client=clientRepository.findClientByLogin(log);
        String fio=client.getFio();
        System.out.println(fio);

        Long userid=client.getId();
        List<Client> clients=clientRepository.findAll();
        if(clients.size()==0 || clients==null){
            model.put("mess","Клиентов нет!");
        }
        model.put("clients",clients);
        List<ClientOrder> clientOrders = orderRepository.findAll();
        if(clientOrders.size()==0 || clientOrders==null){
            model.put("mess","Список пуст");
        }
        model.put("orders",clientOrders);

        List<ClientReview> clientReviews=reviewRepository.findAll();
        model.put("reviews",clientReviews);
        if(clientReviews.size()==0){
            model.put("messaga","Отзывов нет");
        }


        model.put("client",client);
        model.put("fio",client.getFio());
        model.put("login",client.getLogin());
        model.put("pass",client.getPass());
        model.put("email",client.getEmail());
        model.put("phone",client.getPhone());

        List<Req> reqs=reqRepository.findAll();
        model.put("comments",reqs);
        if(reqs.size()==0){
           return "adminpage";
        }


        return "adminpage";
    }

    @GetMapping("/index")
    public  String index(Map<String, Object> model) {

        return "index";
    }


    @GetMapping("/doublebed")
    public  String doublebed(Map<String, Object> model) {

        WebSecurityConfig webSecurityConfig=new WebSecurityConfig();
        String log=webSecurityConfig.getCurrentUsername();
        Client client=clientRepository.findClientByLogin(log);
        Set<Role> roles=client.getRoles();
        System.out.println(roles.toString());
        if(roles != null && roles.toString().equals("[Admin]")){
            return "redirect:/adminpage";
        }
        return "doublebed";
    }

    @GetMapping("/doublebedtv")
    public  String doublebedtv(Map<String, Object> model) {

        String log = UtilLogic.getCurrentUserName();
        Client client = clientRepository.findClientByLogin(log);
        Set<Role> roles = client.getRoles();
        System.out.println(roles.toString());

        if (roles.toString().equals("[Admin]")) {
            return "redirect:/adminpage";
        }

        return "doublebedtv";
    }


    @GetMapping("/personalpage")
    public  String personalpage(Map<String, Object> model) {

        WebSecurityConfig webSecurityConfig=new WebSecurityConfig();
        String log=webSecurityConfig.getCurrentUsername();

        Client client=clientRepository.findClientByLogin(log);
        String fio=client.getFio();
        System.out.println(fio);

        Long userid=client.getId();
        List<ClientOrder> clientOrders=orderRepository.findAllByClient(client);
        if(clientOrders.size()==0 || clientOrders==null){
            model.put("mess","У вас еще не было заказов");
        }
        model.put("orders",clientOrders);

        List<ClientReview> clientReviews=reviewRepository.findAllByClient(client);
        model.put("reviews",clientReviews);
        if(clientReviews.size()==0){
            model.put("messaga","У вас еще не было отзывов");
        }
        model.put("client",client);
        model.put("fio",client.getFio());
        model.put("login",client.getLogin());
        model.put("pass",client.getPass());
        model.put("email",client.getEmail());
        model.put("phone",client.getPhone());

        return "personalpage";
    }

    @GetMapping("/twodoublebeds")
    public  String twodoublebeds(Map<String, Object> model) {
        WebSecurityConfig weConfig=new WebSecurityConfig();
        String login=weConfig.getCurrentUsername();
        Client client=clientRepository.findClientByLogin(login);
        Set<Role> roles=client.getRoles();
        System.out.println(roles.toString());
        if(roles.toString().equals("[Admin]")){
            return "redirect:/adminpage";
        }
        //Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        WebSecurityConfig webSecurityConfig=new WebSecurityConfig();
        String log=webSecurityConfig.getCurrentUsername();
        model.put("mess",log);
        return "twodoublebeds";
    }

    @GetMapping("/tvcitchen")
    public  String tvcitchen(Map<String, Object> model) {

        WebSecurityConfig webSecurityConfig=new WebSecurityConfig();
        String log=webSecurityConfig.getCurrentUsername();
        Client client=clientRepository.findClientByLogin(log);
        Set<Role> roles=client.getRoles();
        System.out.println(roles.toString());
        if(roles.toString().equals("[Admin]")){
            return "redirect:/adminpage";
        }
        return "tvcitchen";
    }

    @GetMapping("/hello")
    private String getAllItems(Map<String, Object> model){
    System.out.println("работает");
    List<Client> clients=clientRepository.findAll();
    model.put("clients",clients );
    return "hello";

    }


    @PostMapping("/doublebed")
    public String checkRooms (@RequestParam String checkin, @RequestParam String checkout,

                              Map<String,Object> model) throws ParseException {

        String log = UtilLogic.getCurrentUserName();

        Client client = clientRepository.findClientByLogin(log);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Date in = format.parse(checkin);
        Date out = format.parse(checkout);

        if(in.after(out)){
            model.put("mess","Некорректная дата");
            return "doublebed";
        }

        if(UtilLogic.checkEmptyRooms(checkin,checkout, client, roomRepository, orderRepository, model,
                "1", "empty")){
            return "doublebed";
        }

        List<ClientOrder> orders = new ArrayList<>();

        List<ClientOrder> firstRoom = new ArrayList<>(orderRepository.findAllByRoomId(1l));
        List<ClientOrder> secondRoom = new ArrayList<>(orderRepository.findAllByRoomId(2l));
        List<ClientOrder> thirdRoom = new ArrayList<>(orderRepository.findAllByRoomId(3l));
        List<ClientOrder> fourthRoom = new ArrayList<>(orderRepository.findAllByRoomId(4l));
        List<ClientOrder> fifthRoom = new ArrayList<>(orderRepository.findAllByRoomId(5l));

        for (int i = 0; i < fifthRoom.size(); i++){
            System.out.println(fifthRoom.get(i));
        }

        if (UtilLogic.checkRoom(firstRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "doublebed";
        }else if(UtilLogic.checkRoom(secondRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "doublebed";
        }else if (UtilLogic.checkRoom(secondRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "doublebed";
        }else if (UtilLogic.checkRoom(thirdRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "doublebed";
        }else if (UtilLogic.checkRoom(fourthRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "doublebed";
        }else if (UtilLogic.checkRoom(fifthRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "doublebed";
        }else {
            model.put("mess","Свободных номеров на этот период нет");
            return "doublebed";
        }
    }

    @PostMapping("/doublebedtv")
    public String ordertv (@RequestParam String checkin, @RequestParam String checkout,
                           Map<String,Object> model) throws ParseException {
        String log = UtilLogic.getCurrentUserName();

        Client client = clientRepository.findClientByLogin(log);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Date in = format.parse(checkin);
        Date out = format.parse(checkout);

        if(in.after(out)){
            model.put("mess","Некорректная дата");
            return "doublebedtv";
        }

        if(UtilLogic.checkEmptyRooms(checkin,checkout, client, roomRepository, orderRepository, model,
                "2", "empty")){
            return "doublebedtv";
        }

        List<ClientOrder> orders = new ArrayList<>();

        List<ClientOrder> firstRoom = new ArrayList<>(orderRepository.findAllByRoomId(5l));
        List<ClientOrder> secondRoom = new ArrayList<>(orderRepository.findAllByRoomId(6l));
        List<ClientOrder> thirdRoom = new ArrayList<>(orderRepository.findAllByRoomId(7l));
        List<ClientOrder> fourthRoom = new ArrayList<>(orderRepository.findAllByRoomId(8l));
        List<ClientOrder> fifthRoom = new ArrayList<>(orderRepository.findAllByRoomId(9l));

        for (int i = 0; i < fifthRoom.size(); i++){
            System.out.println(fifthRoom.get(i));
        }

        if (UtilLogic.checkRoom(firstRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "doublebedtv";
        }else if(UtilLogic.checkRoom(secondRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "doublebedtv";
        }else if (UtilLogic.checkRoom(secondRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "doublebedtv";
        }else if (UtilLogic.checkRoom(thirdRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "doublebedtv";
        }else if (UtilLogic.checkRoom(fourthRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "doublebedtv";
        }else if (UtilLogic.checkRoom(fifthRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "doublebedtv";
        }else {
            model.put("mess","Свободных номеров на этот период нет");
            return "doublebedtv";
        }

    }

    @PostMapping("/tvcitchen")
    public String tvcitchen (@RequestParam String checkin, @RequestParam String checkout,
                             Map<String,Object> model) throws ParseException {
        String log = UtilLogic.getCurrentUserName();

        Client client = clientRepository.findClientByLogin(log);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Date in = format.parse(checkin);
        Date out = format.parse(checkout);

        if(in.after(out)){
            model.put("mess","Некорректная дата");
            return "tvcitchen";
        }

        if(UtilLogic.checkEmptyRooms(checkin,checkout, client, roomRepository, orderRepository, model,
                "3", "empty")){
            return "tvcitchen";
        }

        List<ClientOrder> orders = new ArrayList<>();

        List<ClientOrder> firstRoom = new ArrayList<>(orderRepository.findAllByRoomId(10l));
        List<ClientOrder> secondRoom = new ArrayList<>(orderRepository.findAllByRoomId(11l));
        List<ClientOrder> thirdRoom = new ArrayList<>(orderRepository.findAllByRoomId(12l));
        List<ClientOrder> fourthRoom = new ArrayList<>(orderRepository.findAllByRoomId(13l));
        List<ClientOrder> fifthRoom = new ArrayList<>(orderRepository.findAllByRoomId(14l));

        for (int i = 0; i < fifthRoom.size(); i++){
            System.out.println(fifthRoom.get(i));
        }

        if (UtilLogic.checkRoom(firstRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "tvcitchen";
        }else if(UtilLogic.checkRoom(secondRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "tvcitchen";
        }else if (UtilLogic.checkRoom(secondRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "tvcitchen";
        }else if (UtilLogic.checkRoom(thirdRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "tvcitchen";
        }else if (UtilLogic.checkRoom(fourthRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "tvcitchen";
        }else if (UtilLogic.checkRoom(fifthRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "tvcitchen";
        }else {
            model.put("mess","Свободных номеров на этот период нет");
            return "tvcitchen";
        }
    }

    @PostMapping("/twodoublebeds")
    public String twodoublebedss (@RequestParam String checkin, @RequestParam String checkout,
                                  Map<String,Object> model) throws ParseException {

        String log = UtilLogic.getCurrentUserName();

        Client client = clientRepository.findClientByLogin(log);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Date in = format.parse(checkin);
        Date out = format.parse(checkout);

        if(in.after(out)){
            model.put("mess","Некорректная дата");
            return "twodoublebeds";
        }

        if(UtilLogic.checkEmptyRooms(checkin,checkout, client, roomRepository, orderRepository, model,
                "4", "empty")){
            return "twodoublebeds";
        }

        List<ClientOrder> orders = new ArrayList<>();

        List<ClientOrder> firstRoom = new ArrayList<>(orderRepository.findAllByRoomId(16l));
        List<ClientOrder> secondRoom = new ArrayList<>(orderRepository.findAllByRoomId(17l));
        List<ClientOrder> thirdRoom = new ArrayList<>(orderRepository.findAllByRoomId(18l));
        List<ClientOrder> fourthRoom = new ArrayList<>(orderRepository.findAllByRoomId(19l));
        List<ClientOrder> fifthRoom = new ArrayList<>(orderRepository.findAllByRoomId(20l));

        for (int i = 0; i < fifthRoom.size(); i++){
            System.out.println(fifthRoom.get(i));
        }

        if (UtilLogic.checkRoom(firstRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "twodoublebeds";
        }else if(UtilLogic.checkRoom(secondRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "twodoublebeds";
        }else if (UtilLogic.checkRoom(secondRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "twodoublebeds";
        }else if (UtilLogic.checkRoom(thirdRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "twodoublebeds";
        }else if (UtilLogic.checkRoom(fourthRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "twodoublebeds";
        }else if (UtilLogic.checkRoom(fifthRoom,checkin,checkout,format,orderRepository,model,clientRepository)){
            return "twodoublebeds";
        }else {
            model.put("mess","Свободных номеров на этот период нет");
            return "twodoublebeds";
        }

    }



    @PostMapping("/hello")
    public String add(@RequestParam String fio, @RequestParam String login, @RequestParam String pass,
                      @RequestParam String email , @RequestParam String phone, Map<String,Object> model) {

            System.out.println("ок");
            Client newclient = new Client(fio, login, pass, email, phone, false);
            clientRepository.save(newclient);
            List<Client> clientss = clientRepository.findAll();
            String ok = "Perfect";
            model.put("message", ok);
            model.put("clients", clientss);
            return "hello";

    }

    @PostMapping("/personalpage")
    public String update(@RequestParam String fio, @RequestParam String login, @RequestParam String pass,
                         @RequestParam String email , @RequestParam String phone, Map<String,Object> model) {

        //WebSecurityConfig webSecurityConfig = new WebSecurityConfig();
        //String log = webSecurityConfig.getCurrentUsername();
        String log = UtilLogic.getCurrentUserName();
        Client newclient = clientRepository.findClientByLogin(log);
        Long idc = newclient.getId();
        Client client=new Client(idc,fio,login,pass,email,phone,true);

        System.out.println("ок");
        clientRepository.save(client);
        return "personalpage";
    }

    @PostMapping("filter")
    public String filter(@RequestParam Long filter, Map<String,Object> model){

        List<Client> clients;
        if(filter!=null && !filter.toString().isEmpty()){
            clients=clientRepository.findAllById(filter);
        }else{
            clients=clientRepository.findAll();
        }

        model.put("clients",clients);
        return "hello";
    }
    @PostMapping("/addcomment")
    public String addcomment(@RequestParam String comment, Map<String,Object> model){


        if(comment!=null && !comment.toString().isEmpty()){
        Req req=new Req(comment);
        reqRepository.save(req);

        }else{
            return "rersonalpage";
        }
        return "personalpage";
    }

    @GetMapping("/exit")
    public String logout(Map<String, Object> model){
        UtilLogic.disposeSession();
        return "login";
    }

    @PostMapping("/index")
    public String addReview(@RequestParam String text, Map<String,Object> model){


        ClientReview clientReview=new ClientReview();

        WebSecurityConfig webSecurityConfig=new WebSecurityConfig();
        String log=webSecurityConfig.getCurrentUsername();

        Client client=clientRepository.findClientByLogin(log);

        List<ClientReview> allReviews;
        if(text!=null && !text.isEmpty()){
             clientReview = new ClientReview(text,client);
             reviewRepository.save(clientReview);
             allReviews = reviewRepository.findAll();

        }else{
            allReviews = reviewRepository.findAll();
        }

        model.put("reviews",allReviews);
        return "index";
    }


    @RequestMapping(value= "/hello/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(Long id)
    {
        clientRepository.deleteAllById(id);
        List<Client> clients;
        clients=clientRepository.findAll();
    }

    @PostMapping("delete")
    public String delete(@RequestParam Long delete, Map<String,Object> model){

        List<Client> clients;

        if(!(delete == null && delete.toString().isEmpty())){
            clientRepos.deleteAllById(delete);
            clients = clientRepository.findAll();
        }else{
            clients = clientRepository.findAll();
        }

        model.put("clients",clients);
        return "hello";
    }
    @PostMapping("delorder")
    public String delorder(@RequestParam Long delorder, Map<String,Object> model){

        List<ClientOrder> clientOrders;
        if(delorder!=null && !delorder.toString().isEmpty()){

            roomRepository.findRoomById(orderRepository.findOneById(delorder).getRoom().getId()).setStatus("empty");
            orderRepos.deleteAllById(delorder);;
            clientOrders = orderRepository.findAll();
        }else{
            clientOrders=orderRepository.findAll();
        }

        model.put("orders",clientOrders);
        return "personalpage";
    }

    @PostMapping("delorderadmin")
    public String dell(@RequestParam Long delorder, Map<String,Object> model){

        List<ClientOrder> clientOrders;
        if(delorder!=null && !delorder.toString().isEmpty()){
            orderRepos.deleteAllById(delorder);
            clientOrders=orderRepository.findAll();
        }else{
            clientOrders=orderRepository.findAll();
        }

        model.put("orders",clientOrders);
        return "adminpage";
    }

    @PostMapping("delclient")
    public String delclient(@RequestParam Long delclient, Map<String,Object> model){

        List<Client> clients;
        if(delclient!=null && !delclient.toString().isEmpty()){
            clientRepos.deleteAllById(delclient);
            clients=clientRepository.findAll();
        }else{
            clients=clientRepository.findAll();
        }

        model.put("clients",clients);
        return "adminpage";
    }

    @PostMapping("approve")
    public String approve(@RequestParam Long approve, Map<String,Object> model){

        List<Req> reqes;
        if(!(approve == null && approve.toString().isEmpty())){
            reqRepos.deleteAllById(approve);
            reqes = reqRepository.findAll();
        }else{
            reqes = reqRepository.findAll();
        }
        model.put("comments",reqes);
        return "adminpage";
    }
}
