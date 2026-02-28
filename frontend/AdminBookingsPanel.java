package frontend;

import backend.Booking;
import dao.BookingDAO;
import dao.CylinderDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class AdminBookingsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private BookingDAO dao;
    private TableRowSorter<DefaultTableModel> sorter;

    public AdminBookingsPanel() {
        setLayout(new BorderLayout());
        dao = new BookingDAO();

        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.add(new JLabel("Customer Id:"));
        JTextField customerFilter = new JTextField(10);
        filterPanel.add(customerFilter);

        filterPanel.add(new JLabel("Category:"));
        JComboBox<String> categoryCombo = new JComboBox<>(new String[] { "All", "Domestic", "Commercial" });
        filterPanel.add(categoryCombo);

        filterPanel.add(new JLabel("Payment:"));
        JComboBox<String> paymentCombo = new JComboBox<>(new String[] { "All", "Online", "COD" });
        filterPanel.add(paymentCombo);

        JButton filterBtn = new JButton("Filter");
        filterPanel.add(filterBtn);

        add(filterPanel, BorderLayout.NORTH);

        String[] columns = { "Booking Id", "Customer Name", "Cylinder Type", "Weight", "Price", "Payment Mode",
                "Payment Status",
                "Booking Date" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton deleteBtn = new JButton("Delete Booking");
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int bookingId = (int) table.getValueAt(row, 0);
                if (dao.deleteBooking(bookingId)) {
                    JOptionPane.showMessageDialog(this, "Booking Deleted");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete booking");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a booking to delete");
            }
        });
        JButton approveBtn = new JButton("Approve Booking");
        approveBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int bookingId = (int) table.getValueAt(row, 0);
                if (dao.updateBookingStatus(bookingId, "Approved")) {
                    JOptionPane.showMessageDialog(this, "Booking Approved successfully");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to approve booking");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a booking to approve");
            }
        });

        bottomPanel.add(approveBtn);
        bottomPanel.add(deleteBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        filterBtn.addActionListener(e -> {
            String cust = customerFilter.getText().trim();
            String cat = categoryCombo.getSelectedItem().toString();
            String pay = paymentCombo.getSelectedItem().toString();

            loadData(cust, cat, pay);
        });

        loadData();
    }

    private void loadData() {
        loadData("", "All", "All");
    }

    private void loadData(String customer, String category, String payment) {
        model.setRowCount(0);
        List<Booking> list = dao.getAllBookings();

        for (Booking b : list) {
            boolean matchCust = customer.isEmpty() || String.valueOf(b.getCustomerId()).equals(customer);
            boolean matchCat = category.equals("All") || b.getCylinderCategory().equalsIgnoreCase(category);
            boolean matchPay = payment.equals("All") || b.getPaymentMode().equalsIgnoreCase(payment);

            if (matchCust && matchCat && matchPay) {
                model.addRow(new Object[] {
                        b.getBookingId(),
                        b.getCustomerName(),
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
}
