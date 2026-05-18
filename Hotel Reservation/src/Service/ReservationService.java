package Service;

import model.*;
import java.util.*;

public class ReservationService {

    private static final ReservationService INSTANCE = new ReservationService();
    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Set<Reservation> reservations = new HashSet<>();

    private ReservationService() {}

    public static ReservationService getInstance() {
        return INSTANCE;
    }

    public void addRoom(IRoom room) {
        if (room == null || room.getRoomNumber() == null) {
            throw new IllegalArgumentException("Invalid room");
        }

        if (rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException("Room already exists");
        }

        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getRoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public void printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found");
        } else {
            reservations.forEach(System.out::println);
        }
    }

    public Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
        validateDates(checkIn, checkOut);
        return getAvailableRooms(checkIn, checkOut);
    }

    private void validateDates(Date checkIn, Date checkOut) {
        Date today = new Date();
        if (checkIn.before(today)) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }
        if (!checkOut.after(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }
    }

    private Collection<IRoom> getAvailableRooms(Date checkIn, Date checkOut) {
        List<IRoom> availableRooms = new ArrayList<>(rooms.values());

        for (Reservation r : reservations) {
            if (datesOverlap(checkIn, checkOut,
                    r.getCheckInDate(), r.getCheckOutDate())) {
                availableRooms.remove(r.getRoom());
            }
        }
        return availableRooms;
    }

    private boolean datesOverlap(Date s1, Date e1, Date s2, Date e2) {
        return !(e1.before(s2) || s1.after(e2));
    }

    public Reservation reserveARoom(Customer customer, IRoom room,
                                    Date checkIn, Date checkOut) {

        for (Reservation r : reservations) {
            if (r.getRoom().equals(room) &&
                    datesOverlap(checkIn, checkOut,
                            r.getCheckInDate(), r.getCheckOutDate())) {
                throw new IllegalArgumentException(
                        "Room already booked for selected dates");
            }
        }

        Reservation reservation =
                new Reservation(customer, room, checkIn, checkOut);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<Reservation> getCustomersReservations(Customer customer) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getCustomer().equals(customer)) {
                result.add(r);
            }
        }
        return result;
    }

    public Collection<IRoom> findRecommendedRooms(Date checkIn, Date checkOut) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(checkIn);
        cal.add(Calendar.DATE, 7);
        Date newCheckIn = cal.getTime();

        cal.setTime(checkOut);
        cal.add(Calendar.DATE, 7);
        Date newCheckOut = cal.getTime();

        return findRooms(newCheckIn, newCheckOut);
    }
}
