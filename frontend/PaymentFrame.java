package frontend;

import backend.Booking;
import backend.Session;
import backend.Cylinder;
import dao.BookingDAO;
import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;

public class PaymentFrame extends JFrame {
    private Cylinder cylinder;
    private String contact;
    private String address;
    private BookingDAO bookingDAO;

    public PaymentFrame(Cylinder cylinder, String contact, String address) {
        this.cylinder = cylinder;
        this.contact = contact;
        this.address = address;
        this.bookingDAO = new BookingDAO();

        setTitle("Payment");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Complete Payment", SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new CardLayout());

        JPanel selectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        selectionPanel.add(new JLabel("Total Amount: ₹" + cylinder.getPrice()), gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JButton codBtn = new JButton("Cash on Delivery");
        selectionPanel.add(codBtn, gbc);

        gbc.gridx = 1;
        JButton onlineBtn = new JButton("Online Payment");
        selectionPanel.add(onlineBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton backBtn = new JButton("Back");
        selectionPanel.add(backBtn, gbc);

        // Online Payment Panel
        JPanel onlinePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(10, 10, 10, 10);
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.gridwidth = 2;

        onlinePanel.add(new JLabel("Scan QR code using any UPI app to pay."), gbc2);

        gbc2.gridy = 1;
        gbc2.gridwidth = 1;
        onlinePanel.add(new JLabel("Upi Id:"), gbc2);
        gbc2.gridx = 1;
        JTextField upiField = new JTextField(15);
        onlinePanel.add(upiField, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 2;
        onlinePanel.add(new JLabel("Transaction Id:"), gbc2);
        gbc2.gridx = 1;
        JTextField trnField = new JTextField(15);
        onlinePanel.add(trnField, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 3;
        gbc2.gridwidth = 2;
        JButton submitOnlineBtn = new JButton("Submit Payment");
        onlinePanel.add(submitOnlineBtn, gbc2);

        gbc2.gridy = 4;
        JButton cancelOnlineBtn = new JButton("Cancel");
        onlinePanel.add(cancelOnlineBtn, gbc2);

        mainPanel.add(selectionPanel, "selection");
        mainPanel.add(onlinePanel, "online");

        add(mainPanel, BorderLayout.CENTER);
        CardLayout cl = (CardLayout) mainPanel.getLayout();

        backBtn.addActionListener(e -> {
            new CustomerDetailsFrame(cylinder).setVisible(true);
            dispose();
        });

        codBtn.addActionListener(e -> {
            Booking b = new Booking(0, Session.currentCustomerId, cylinder.getCylinderId(),
                    "COD", null, null, address, contact, "CONFIRMED", new Timestamp(System.currentTimeMillis()));
            if (bookingDAO.createBooking(b)) {
                JOptionPane.showMessageDialog(this, "Your gas cylinder has been booked successfully.");
                new CustomerDashboard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Booking failed!");
            }
        });

        onlineBtn.addActionListener(e -> cl.show(mainPanel, "online"));

        cancelOnlineBtn.addActionListener(e -> cl.show(mainPanel, "selection"));

        submitOnlineBtn.addActionListener(e -> {
            String upi = upiField.getText().trim();
            String trn = trnField.getText().trim();

            if (upi.isEmpty() || trn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter all payment details.");
                return;
            }

            Booking b = new Booking(0, Session.currentCustomerId, cylinder.getCylinderId(),
                    "Online", upi, trn, address, contact, "CONFIRMED", new Timestamp(System.currentTimeMillis()));
            if (bookingDAO.createBooking(b)) {
                JOptionPane.showMessageDialog(this, "Your gas cylinder booking has been completed successfully.");
                new CustomerDashboard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Payment / Booking failed. Rolling back transaction.");
            }
        });
    }
}
