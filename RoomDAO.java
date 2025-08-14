import java.sql.*;

public class RoomDAO {
    public void addRoom(int roomNumber, String type, double price) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO rooms (room_number, type, price) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, roomNumber);
            ps.setString(2, type);
            ps.setDouble(3, price);
            ps.executeUpdate();
            System.out.println("Room added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listAvailableRooms() {
        try (Connection conn = DBConnection.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM rooms WHERE is_available = TRUE");
            System.out.println("\n--- Available Rooms ---");
            while (rs.next()) {
                System.out.printf("Room ID: %d | Room No: %d | Type: %s | Price: %.2f\n",
                        rs.getInt("room_id"), rs.getInt("room_number"), rs.getString("type"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
