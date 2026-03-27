package api;

import Service.CustomerService;
import Service.ReservationService;
import model.*;

import java.util.Collection;
import java.util.Date;



public class HotelResource {
    private static final HotelResource INSTANCE = new HotelResource();


    private final CustomerService customerService= CustomerService.getInstance();
    private final ReservationService reservationService=ReservationService.getInstance();

    private HotelResource(){}

    public static HotelResource getInstance(){
        return INSTANCE;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createCustomer(String email,String first, String last){
        customerService.addCustomer(email,first,last);
    }

    public IRoom getRoom(String roomNumber){

        return reservationService.getRoom(roomNumber);
    }


    public Reservation bookARoom(
            String customerEmail,
            IRoom room,
            Date checkIn,
            Date checkOut){
        Customer customer = getCustomer(customerEmail);
        if(customer==null){
            throw new IllegalArgumentException("Customer not found");
        }
        return reservationService.reserveARoom(customer,room,checkIn,checkOut);
    }

    public Collection<IRoom> findARoom(Date checkIn,Date checkOut){
        return reservationService.findRooms(checkIn,checkOut);
    }

    public Collection<Reservation> getCustomersReservations(String email){
        Customer customer = getCustomer(email);
        return reservationService.getCustomersReservations(customer);
    }

    public Collection<IRoom>findRecommendedRooms(Date checkIn,Date checkOut){
        return reservationService.findRecommendedRooms(checkIn,checkOut);
    }

    public Collection<IRoom> findAvailableRooms(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }


}
