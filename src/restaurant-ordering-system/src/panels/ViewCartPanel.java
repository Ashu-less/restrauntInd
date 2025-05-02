package panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewCartPanel extends JPanel {
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel subtotalLabel, taxLabel, tipLabel, totalLabel;
    private final double TAX_RATE = 0.0825;
    private final double DEFAULT_TIP_PERCENTAGE = 0.0825;
    private List<CartItem> cart;

    public ViewCartPanel(List<CartItem> cart) {
        this.cart = cart;
        setLayout(new BorderLayout());

        String[] columnNames = {"Item Name", "Quantity", "Cost", "Extended Cost"};
        tableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        JPanel totalsPanel = new JPanel(new GridLayout(4, 1));
        subtotalLabel = new JLabel("Subtotal: $0.00");
        taxLabel = new JLabel("Tax: $0.00");
        tipLabel = new JLabel("Tip: $0.00");
        totalLabel = new JLabel("Total: $0.00");
        totalsPanel.add(subtotalLabel);
        totalsPanel.add(taxLabel);
        totalsPanel.add(tipLabel);
        totalsPanel.add(totalLabel);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> checkout());

        add(scrollPane, BorderLayout.CENTER);
        add(totalsPanel, BorderLayout.SOUTH);
        add(checkoutButton, BorderLayout.NORTH);

        refresh();
    }

    public void refresh() {
        tableModel.setRowCount(0);
        double subtotal = 0;

        for (CartItem item : cart) {
            if (item.getQuantity() > 0) { // Only display items with quantity > 0
                double extendedCost = item.getExtendedCost();
                subtotal += extendedCost;
                tableModel.addRow(new Object[]{
                    item.getMenuItem().getName(),
                    item.getQuantity(),
                    String.format("$%.2f", item.getMenuItem().getCost()),
                    String.format("$%.2f", extendedCost)
                });
            }
        }

        double tax = subtotal * TAX_RATE;
        double tip = subtotal * DEFAULT_TIP_PERCENTAGE;
        double total = subtotal + tax + tip;

        subtotalLabel.setText(String.format("Subtotal: $%.2f", subtotal));
        taxLabel.setText(String.format("Tax: $%.2f", tax));
        tipLabel.setText(String.format("Tip: $%.2f", tip));
        totalLabel.setText(String.format("Total: $%.2f", total));
    }

    private void checkout() {
        JOptionPane.showMessageDialog(this, "Thank you for your order!", "Checkout", JOptionPane.INFORMATION_MESSAGE);
        cart.clear();
        refresh();
    }

    // Add a method to remove items from the cart
    public void removeItemFromCart(String itemName) {
        for (CartItem item : cart) {
            if (item.getMenuItem().getName().equals(itemName)) {
                item.decreaseQuantity();
                if (item.getQuantity() <= 0) {
                    cart.remove(item); // Remove item if quantity is 0
                }
                break;
            }
        }
        refresh();
    }
}