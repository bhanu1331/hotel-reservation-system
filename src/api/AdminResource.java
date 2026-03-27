package api;

import Service.CustomerService;
import Service.ReservationService;
import model.Customer;
import model.IRoom;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static final AdminResource INSTANCE = new AdminResource();


    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    private AdminResource(){}

    public static AdminResource getInstance(){
        return INSTANCE;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms){
        rooms.forEach(reservationService::addRoom);
    }

    public Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    public void displayAllReservations(){
        reservationService.printAllReservation();
    }
}
