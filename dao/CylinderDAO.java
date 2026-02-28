package dao;

import backend.Cylinder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CylinderDAO {

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM Cylinders";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<String> getWeightsByCategory(String category) {
        List<String> weights = new ArrayList<>();
        String sql = "SELECT weight FROM Cylinders WHERE category = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                weights.add(rs.getString("weight"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weights;
    }

    public Cylinder getCylinderDetails(String category, String weight) {
        String sql = "SELECT * FROM Cylinders WHERE category = ? AND weight = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            stmt.setString(2, weight);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cylinder(
                        rs.getInt("cylinder_id"),
                        rs.getString("category"),
                        rs.getString("weight"),
                        rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePrice(int cylinderId, double newPrice) {
        String sql = "UPDATE Cylinders SET price = ? WHERE cylinder_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newPrice);
            stmt.setInt(2, cylinderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Cylinder> getAllCylinders() {
        List<Cylinder> cylinders = new ArrayList<>();
        String sql = "SELECT * FROM Cylinders";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cylinders.add(new Cylinder(
                        rs.getInt("cylinder_id"),
                        rs.getString("category"),
                        rs.getString("weight"),
                        rs.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cylinders;
    }
}
