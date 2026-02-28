package frontend;

import backend.Cylinder;
import backend.Session;
import javax.swing.*;
import java.awt.*;

public class CustomerDetailsFrame extends JFrame {
    private Cylinder cylinder;

    public CustomerDetailsFrame(Cylinder cylinder) {
        this.cylinder = cylinder;

        setTitle("Customer Details");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Delivery Details");
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(Session.currentCustomerName, 20);
        nameField.setEditable(false);
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1;
        JTextField contactField = new JTextField(20);
        panel.add(contactField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Pin Code:"), gbc);
        gbc.gridx = 1;
        JTextField pinField = new JTextField(20);
        panel.add(pinField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Full Address:"), gbc);
        gbc.gridx = 1;
        JTextArea addressArea = new JTextArea(4, 20);
        addressArea.setLineWrap(true);
        panel.add(new JScrollPane(addressArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton proceedBtn = new JButton("Proceed to Payment");
        panel.add(proceedBtn, gbc);

        gbc.gridy = 6;
        JButton backBtn = new JButton("Back");
        panel.add(backBtn, gbc);

        add(panel);

        proceedBtn.addActionListener(e -> {
            String contact = contactField.getText().trim();
            String address = addressArea.getText().trim() + " - " + pinField.getText().trim();

            if (contact.isEmpty() || address.isEmpty() || pinField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all details.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            new PaymentFrame(cylinder, contact, address).setVisible(true);
            dispose();
        });

        backBtn.addActionListener(e -> {
            new CylinderSelectionFrame().setVisible(true);
            dispose();
        });
    }
}
