import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewCartPanel extends JPanel {
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JTextField tipField;
    private JLabel subtotalLabel;
    private JLabel taxLabel;
    private JLabel tipLabel;
    private JLabel totalLabel;

    public ViewCartPanel(List<CartItem> cartItems) {
        setLayout(new BorderLayout());

        // Heading
        JLabel heading = new JLabel("View Cart", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        add(heading, BorderLayout.NORTH);

        // Table for cart items
        String[] columnNames = {"Item Name", "Quantity", "Cost", "Extended Cost"};
        tableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        add(scrollPane, BorderLayout.CENTER);

        // Populate table with cart items
        for (CartItem item : cartItems) {
            Object[] rowData = {
                item.getMenuItem().getName(),
                item.getQuantity(),
                String.format("%.2f", item.getMenuItem().getCost()),
                String.format("%.2f", item.getQuantity() * item.getMenuItem().getCost())
            };
            tableModel.addRow(rowData);
        }

        // Tip entry field
        JPanel tipPanel = new JPanel();
        tipPanel.add(new JLabel("Tip Percentage:"));
        tipField = new JTextField(5);
        tipPanel.add(tipField);
        add(tipPanel, BorderLayout.SOUTH);

        // Receipt section
        JPanel receiptPanel = new JPanel(new GridLayout(4, 2));
        receiptPanel.add(new JLabel("Subtotal:"));
        subtotalLabel = new JLabel("0.00");
        receiptPanel.add(subtotalLabel);
        receiptPanel.add(new JLabel("Tax (8.25%):"));
        taxLabel = new JLabel("0.00");
        receiptPanel.add(taxLabel);
        receiptPanel.add(new JLabel("Tip:"));
        tipLabel = new JLabel("0.00");
        receiptPanel.add(tipLabel);
        receiptPanel.add(new JLabel("Total:"));
        totalLabel = new JLabel("0.00");
        receiptPanel.add(totalLabel);
        add(receiptPanel, BorderLayout.EAST);

        // Calculate totals
        calculateTotals(cartItems);
    }

    private void calculateTotals(List<CartItem> cartItems) {
        double subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getQuantity() * item.getMenuItem().getCost();
        }
        subtotalLabel.setText(String.format("%.2f", subtotal));
        double tax = subtotal * 0.0825;
        taxLabel.setText(String.format("%.2f", tax));
        double tip = getTipAmount();
        tipLabel.setText(String.format("%.2f", tip));
        double total = subtotal + tax + tip;
        totalLabel.setText(String.format("%.2f", total));
    }

    private double getTipAmount() {
        try {
            int tipPercentage = Integer.parseInt(tipField.getText());
            return tipPercentage / 100.0 * Double.parseDouble(subtotalLabel.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}