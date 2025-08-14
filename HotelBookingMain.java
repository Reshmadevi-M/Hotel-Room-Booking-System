import java.util.Scanner;

public class HotelBookingMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RoomDAO roomDAO = new RoomDAO();
        BookingDAO bookingDAO = new BookingDAO();

        while (true) {
            System.out.println("\n==== Hotel Booking Console ====");
            System.out.println("1. Add Room");
            System.out.println("2. View Available Rooms");
            System.out.println("3. Book a Room");
            System.out.println("4. Cancel Booking");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Room number: ");
                    int rno = sc.nextInt(); sc.nextLine();
                    System.out.print("Type: ");
                    String type = sc.nextLine();
                    System.out.print("Price: ");
                    double price = sc.nextDouble(); sc.nextLine();
                    roomDAO.addRoom(rno, type, price);
                    break;

                case 2:
                    roomDAO.listAvailableRooms();
                    break;

                case 3:
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();
                    System.out.print("Room ID to book: ");
                    int roomId = sc.nextInt(); sc.nextLine();
                    System.out.print("Check-in date (YYYY-MM-DD): ");
                    String checkIn = sc.nextLine();
                    System.out.print("Check-out date (YYYY-MM-DD): ");
                    String checkOut = sc.nextLine();
                    bookingDAO.addBooking(name, email, phone, roomId, checkIn, checkOut);
                    break;

                case 4:
                    System.out.print("Booking ID to cancel: ");
                    int bookingId = sc.nextInt(); sc.nextLine();
                    bookingDAO.cancelBooking(bookingId);
                    break;

                case 0:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
