package frontend;

import backend.Customer;
import backend.Session;
import dao.AuthService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JComboBox<String> roleComboBox;
    private JButton loginBtn;
    private JButton clearBtn;
    private JLabel errorLabel;
    private AuthService authService;

    public LoginFrame() {
        authService = new AuthService();
        setTitle("Gas Cylinder Booking System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Gas Cylinder Booking System");
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        userField = new JTextField(15);
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passField = new JPasswordField(15);
        panel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        roleComboBox = new JComboBox<>();
        roleComboBox.addItem("Customer");
        roleComboBox.addItem("Admin");
        panel.add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginBtn = new JButton("Login");
        clearBtn = new JButton("Clear");
        btnPanel.add(loginBtn);
        btnPanel.add(clearBtn);
        panel.add(btnPanel, gbc);

        gbc.gridy = 5;
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.BLACK);
        panel.add(errorLabel, gbc);

        add(panel);

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userField.setText("");
                passField.setText("");
                errorLabel.setText(" ");
                roleComboBox.setSelectedIndex(0);
            }
        });

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();
                String role = roleComboBox.getSelectedItem().toString();

                Frame nextFrame = null;

                if (username.isEmpty() || password.isEmpty()) {
                    errorLabel.setText("Username and password are required");
                    return;
                }

                if (role.equals("Admin") &&
                        username.equals("admin1") &&
                        password.equals("admin123")) {

                    nextFrame = new AdminDashboard();
                }

                if (role.equals("Customer")) {
                    Customer customer = authService.loginCustomer(username, password);
                    System.out.println("Customer object: " + customer);

                    if (customer != null) {
                        Session.currentCustomerId = customer.getCustomerId();
                        Session.currentCustomerName = customer.getFullName();
                        nextFrame = new CustomerDashboard();
                    }
                }

                if (nextFrame != null) {
                    nextFrame.setVisible(true);
                    dispose();
                } else {
                    errorLabel.setText("Invalid credentials");
                }
            }
        });
    }
}
