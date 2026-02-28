package frontend;

import backend.Cylinder;
import backend.Session;
import dao.CylinderDAO;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CylinderSelectionFrame extends JFrame {
    private JComboBox<String> categoryCombo;
    private JComboBox<String> weightCombo;
    private JLabel priceLabel;
    private CylinderDAO dao;
    private Cylinder selectedCylinder;

    public CylinderSelectionFrame() {
        this.dao = new CylinderDAO();

        setTitle("Cylinder Selection");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Select Cylinder");
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryCombo = new JComboBox<>();
        panel.add(categoryCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Weight:"), gbc);
        gbc.gridx = 1;
        weightCombo = new JComboBox<>();
        panel.add(weightCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        priceLabel = new JLabel("0.00");
        panel.add(priceLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton bookBtn = new JButton("Book Now");
        panel.add(bookBtn, gbc);

        gbc.gridy = 5;
        JButton backBtn = new JButton("Back to Dashboard");
        panel.add(backBtn, gbc);

        add(panel);

        loadCategories();

        categoryCombo.addActionListener(e -> {
            loadWeights();
        });

        weightCombo.addActionListener(e -> {
            updatePrice();
        });

        bookBtn.addActionListener(e -> {
            if (selectedCylinder != null) {
                new CustomerDetailsFrame(selectedCylinder).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a valid cylinder.");
            }
        });

        backBtn.addActionListener(e -> {
            new CustomerDashboard().setVisible(true);
            dispose();
        });
    }

    private void loadCategories() {
        categoryCombo.removeAllItems();
        List<String> categories = dao.getCategories();
        for (String c : categories)
            categoryCombo.addItem(c);
        if (categoryCombo.getItemCount() > 0) {
            categoryCombo.setSelectedIndex(0);
        }
    }

    private void loadWeights() {
        weightCombo.removeAllItems();
        if (categoryCombo.getSelectedItem() != null) {
            String cat = categoryCombo.getSelectedItem().toString();
            List<String> weights = dao.getWeightsByCategory(cat);
            for (String w : weights)
                weightCombo.addItem(w);
            if (weightCombo.getItemCount() > 0) {
                weightCombo.setSelectedIndex(0);
            }
        }
    }

    private void updatePrice() {
        if (categoryCombo.getSelectedItem() != null && weightCombo.getSelectedItem() != null) {
            String cat = categoryCombo.getSelectedItem().toString();
            String weight = weightCombo.getSelectedItem().toString();
            selectedCylinder = dao.getCylinderDetails(cat, weight);
            if (selectedCylinder != null) {
                priceLabel.setText(String.valueOf(selectedCylinder.getPrice()));
            } else {
                priceLabel.setText("N/A");
            }
        }
    }
}
