import java.sql.*;

public class BookingDAO {
    public void addBooking(String name, String email, String phone, int roomId, String checkIn, String checkOut) {
        try (Connection conn = DBConnection.getConnection()) {
            // Insert customer
            String insertCustomer = "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement custStmt = conn.prepareStatement(insertCustomer, Statement.RETURN_GENERATED_KEYS);
            custStmt.setString(1, name);
            custStmt.setString(2, email);
            custStmt.setString(3, phone);
            custStmt.executeUpdate();
            ResultSet custRs = custStmt.getGeneratedKeys();
            custRs.next();
            int customerId = custRs.getInt(1);

            // Insert booking
            String insertBooking = "INSERT INTO bookings (customer_id, room_id, check_in, check_out) VALUES (?, ?, ?, ?)";
            PreparedStatement bookStmt = conn.prepareStatement(insertBooking);
            bookStmt.setInt(1, customerId);
            bookStmt.setInt(2, roomId);
            bookStmt.setDate(3, Date.valueOf(checkIn));
            bookStmt.setDate(4, Date.valueOf(checkOut));
            bookStmt.executeUpdate();

            // Update room availability
            String updateRoom = "UPDATE rooms SET is_available = FALSE WHERE room_id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateRoom);
            updateStmt.setInt(1, roomId);
            updateStmt.executeUpdate();

            System.out.println("Booking successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelBooking(int bookingId) {
        try (Connection conn = DBConnection.getConnection()) {
            String getRoom = "SELECT room_id FROM bookings WHERE booking_id = ?";
            PreparedStatement getStmt = conn.prepareStatement(getRoom);
            getStmt.setInt(1, bookingId);
            ResultSet rs = getStmt.executeQuery();

            if (rs.next()) {
                int roomId = rs.getInt("room_id");

                String deleteBooking = "DELETE FROM bookings WHERE booking_id = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteBooking);
                deleteStmt.setInt(1, bookingId);
                deleteStmt.executeUpdate();

                String updateRoom = "UPDATE rooms SET is_available = TRUE WHERE room_id = ?";
                PreparedStatement updateRoomStmt = conn.prepareStatement(updateRoom);
                updateRoomStmt.setInt(1, roomId);
                updateRoomStmt.executeUpdate();

                System.out.println("Booking cancelled and room made available.");
            } else {
                System.out.println("Booking not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
