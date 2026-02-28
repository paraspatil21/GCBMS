package frontend;

import backend.Booking;
import backend.Session;
import dao.BookingDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerDashboard extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private BookingDAO dao;

    public CustomerDashboard() {
        System.out.println("CustomerDashboard Constructor started");
        this.dao = new BookingDAO();

        setTitle("Customer Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome, " + Session.currentCustomerName, SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        String[] columns = { "Booking Id", "Cylinder Type", "Weight", "Price", "Payment Mode", "Payment Status",
                "Booking Date" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton bookBtn = new JButton("Book Cylinder");
        JButton refreshBtn = new JButton("Refresh");
        JButton logoutBtn = new JButton("Logout");

        bookBtn.addActionListener(e -> {
            new CylinderSelectionFrame().setVisible(true);
            dispose();
        });

        refreshBtn.addActionListener(e -> loadData());

        logoutBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        bottomPanel.add(bookBtn);
        bottomPanel.add(refreshBtn);
        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        try {
            loadData();
            System.out.println("CustomerBookings loaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("CustomerDashboard Constructor finished");
    }

    private void loadData() {
        model.setRowCount(0);
        List<Booking> list = dao.getBookingsByCustomer(Session.currentCustomerId);
        for (Booking b : list) {
            model.addRow(new Object[] {
                    b.getBookingId(),
                    b.getCylinderCategory(),
                    b.getCylinderWeight(),
                    b.getCylinderPrice(),
                    b.getPaymentMode(),
                    b.getStatus(),
                    b.getBookingDate()
            });
        }
    }
}
