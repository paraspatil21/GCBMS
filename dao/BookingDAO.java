package dao;

import backend.Booking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO Bookings (customer_id, cylinder_id, payment_mode, upi_id, transaction_id, address, contact, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null)
                return false;
            conn.setAutoCommit(false); // ACID Transaction start

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, booking.getCustomerId());
                stmt.setInt(2, booking.getCylinderId());
                stmt.setString(3, booking.getPaymentMode());
                stmt.setString(4, booking.getUpiId());
                stmt.setString(5, booking.getTransactionId());
                stmt.setString(6, booking.getAddress());
                stmt.setString(7, booking.getContact());
                stmt.setString(8, booking.getStatus());

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    conn.commit(); // commit transaction
                    return true;
                } else {
                    conn.rollback();
                }
            } catch (SQLException e) {
                conn.rollback(); // rollback on failure
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public List<Booking> getBookingsByCustomer(int customerId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.*, c.full_name, cy.category, cy.weight, cy.price " +
                "FROM Bookings b " +
                "JOIN Customers c ON b.customer_id = c.customer_id " +
                "JOIN Cylinders cy ON b.cylinder_id = cy.cylinder_id " +
                "WHERE b.customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking b = mapResultSetToBooking(rs);
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.*, c.full_name, cy.category, cy.weight, cy.price " +
                "FROM Bookings b " +
                "JOIN Customers c ON b.customer_id = c.customer_id " +
                "JOIN Cylinders cy ON b.cylinder_id = cy.cylinder_id";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Booking b = mapResultSetToBooking(rs);
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM Bookings WHERE booking_id = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // ACID Transaction start
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, bookingId);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE Bookings SET status = ? WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking b = new Booking(
                rs.getInt("booking_id"),
                rs.getInt("customer_id"),
                rs.getInt("cylinder_id"),
                rs.getString("payment_mode"),
                rs.getString("upi_id"),
                rs.getString("transaction_id"),
                rs.getString("address"),
                rs.getString("contact"),
                rs.getString("status"),
                rs.getTimestamp("booking_date"));
        b.setCustomerName(rs.getString("full_name"));
        b.setCylinderCategory(rs.getString("category"));
        b.setCylinderWeight(rs.getString("weight"));
        b.setCylinderPrice(rs.getDouble("price"));
        return b;
    }
}
