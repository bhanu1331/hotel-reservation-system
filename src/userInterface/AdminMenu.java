package userInterface;

import api.AdminResource;
import model.*;

import java.util.*;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void showAdminMenu() {
        String choice;

        do {
            System.out.println("\nAdmin Panel");
            System.out.println("1.See all customers ");
            System.out.println("2.See all rooms ");
            System.out.println("3.See all reservations ");
            System.out.println("4.Add a room");
            System.out.println("5.Back to Main Menu");

            System.out.println("Choose an option");
            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    adminResource.getAllCustomers().forEach(System.out::println);
                    break;
                case "2":
                    adminResource.getAllRooms().forEach(System.out::println);
                    break;
                case "3":
                    adminResource.displayAllReservations();
                    break;
                case "4":
                    addRoom();
                    break;
            }
        } while (!choice.equals("5"));
    }

    private static void addRoom() {
        try {
            System.out.print("Enter room number: ");
            String roomNumber = scanner.nextLine().trim();
            System.out.print("Enter room price: ");
            double price = Double.parseDouble(scanner.nextLine());
            if (price < 0) {
                System.out.println("Price cannot be negative");
                return;
            }

            System.out.print("Enter room type (1 = SINGLE, 2 = DOUBLE): ");
            String typeInput = scanner.nextLine();

            RoomType roomType;
            if ("1".equals(typeInput)) {
                roomType = RoomType.SINGLE;
            } else if ("2".equals(typeInput)) {
                roomType = RoomType.DOUBLE;
            } else {
                System.out.println("Invalid room type");
                return;
            }

            IRoom room = (price == 0)
                    ? new FreeRoom(roomNumber, roomType)
                    : new Room(roomNumber, price, roomType);

            adminResource.addRoom(Collections.singletonList(room));
            System.out.println("Room added successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}