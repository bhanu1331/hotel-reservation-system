package model;

import java.util.Date;

public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final Date checkIn;
    private final Date checkOut;

    public Reservation(Customer customer, IRoom room,
                       Date checkIn, Date checkOut) {
        this.customer = customer;
        this.room = room;
        this.checkIn = new Date(checkIn.getTime());
        this.checkOut = new Date(checkOut.getTime());
    }

    public Customer getCustomer() { return customer; }
    public IRoom getRoom() { return room; }
    public Date getCheckInDate() { return checkIn; }
    public Date getCheckOutDate() { return checkOut; }

    @Override
    public String toString() {
        return "\nCustomer: " + customer +
                "\nRoom: " + room +
                "\nFrom: " + checkIn +
                "\nTo  : " + checkOut;
    }
}
