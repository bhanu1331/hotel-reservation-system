package userInterface;

import api.HotelResource;
import model.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        launchMenu();
    }

    public static void launchMenu() {
        String option;

        do {
            System.out.println("\nWelcome to our Hotel Reservation Application");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an Account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            option = input.nextLine().trim();

            switch (option) {
                case "1":
                    findAndReserve();
                    break;
                case "2":
                    seeReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.showAdminMenu();
                    break;
                case "5":
                    System.out.println("Thank you for using the system");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (!option.equals("5"));
    }

    private static void findAndReserve() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);

            System.out.print("Enter check-in date (dd-MM-yyyy): ");
            Date checkIn = sdf.parse(input.nextLine());

            System.out.print("Enter check-out date (dd-MM-yyyy): ");
            Date checkOut = sdf.parse(input.nextLine());

            Collection<IRoom> availableRooms =
                    hotelResource.findARoom(checkIn, checkOut);

            if (availableRooms.isEmpty()) {
                System.out.println("No rooms available for given dates.");
                System.out.println("Searching for recommended rooms (+7 days)...");

                Collection<IRoom> recommendedRooms =
                        hotelResource.findRecommendedRooms(checkIn, checkOut);

                if (recommendedRooms.isEmpty()) {
                    System.out.println("No recommended rooms available.");
                    return;
                }

                Calendar cal = Calendar.getInstance();
                cal.setTime(checkIn);
                cal.add(Calendar.DATE, 7);
                Date newCheckIn = cal.getTime();

                cal.setTime(checkOut);
                cal.add(Calendar.DATE, 7);
                Date newCheckOut = cal.getTime();

                System.out.println("\nRecommended dates:");
                System.out.println("From: " + newCheckIn);
                System.out.println("To  : " + newCheckOut);

                System.out.println("\nAvailable Recommended Rooms:");
                recommendedRooms.forEach(System.out::println);

                System.out.print("Do you want to book for these dates? (yes/no): ");
                String choice = input.nextLine().trim().toLowerCase();

                if (!choice.equals("yes")) {
                    System.out.println("Booking cancelled.");
                    return;
                }

                checkIn = newCheckIn;
                checkOut = newCheckOut;
                availableRooms = recommendedRooms;
            } else {
                System.out.println("\nAvailable Rooms:");
                availableRooms.forEach(System.out::println);
            }

            System.out.print("Enter your email: ");
            String email = input.nextLine();

            Customer customer = hotelResource.getCustomer(email);
            if (customer == null) {
                System.out.println("Customer not found. Please create an account first.");
                return;
            }

            System.out.print("Enter room number: ");
            String roomNumber = input.nextLine();

            IRoom selectedRoom = null;
            for (IRoom r : availableRooms) {
                if (r.getRoomNumber().equals(roomNumber)) {
                    selectedRoom = r;
                    break;
                }
            }

            if (selectedRoom == null) {
                System.out.println("Invalid room number. Choose from listed rooms only.");
                return;
            }

            hotelResource.bookARoom(
                    email,
                    selectedRoom,
                    new Date(checkIn.getTime()),
                    new Date(checkOut.getTime())
            );

            System.out.println("Room booked successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void seeReservations() {
        System.out.print("Enter email: ");
        hotelResource.getCustomersReservations(input.nextLine())
                .forEach(System.out::println);
    }

    private static void createAccount() {
        try {
            System.out.print("First name: ");
            String first = input.nextLine();

            System.out.print("Last name: ");
            String last = input.nextLine();

            System.out.print("Email: ");
            String email = input.nextLine();

            hotelResource.createCustomer(email, first, last);
            System.out.println("Customer account created successfully");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
