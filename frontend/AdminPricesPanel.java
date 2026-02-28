package frontend;

import backend.Cylinder;
import dao.CylinderDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPricesPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private CylinderDAO dao;

    public AdminPricesPanel() {
        setLayout(new BorderLayout());
        dao = new CylinderDAO();

        String[] columns = { "Cylinder Id", "Cylinder Type", "Weight", "Price" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JTextField priceField = new JTextField(10);
        JButton updateBtn = new JButton("Update Price");

        bottomPanel.add(new JLabel("New Price:"));
        bottomPanel.add(priceField);
        bottomPanel.add(updateBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) table.getValueAt(row, 0);
                try {
                    double newPrice = Double.parseDouble(priceField.getText().trim());
                    if (dao.updatePrice(id, newPrice)) {
                        JOptionPane.showMessageDialog(this, "Price Updated");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update price");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Price");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a cylinder to update");
            }
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<Cylinder> list = dao.getAllCylinders();
        for (Cylinder c : list) {
            model.addRow(new Object[] {
                    c.getCylinderId(),
                    c.getCategory(),
                    c.getWeight(),
                    c.getPrice()
            });
        }
    }
}
