package panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class ViewCartPanel extends JPanel {
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel subtotalLabel, taxLabel, tipLabel, totalLabel;
    private final double TAX_RATE = 0.0825;
    private JTextField tipPercentageField;
    private List<CartItem> cart;

    public ViewCartPanel(List<CartItem> cart) {
        this.cart = cart;
        setLayout(new BorderLayout());

        String[] columnNames = {"Item Name", "Quantity", "Cost", "Extended Cost"};
        tableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        JPanel totalsPanel = new JPanel(new GridLayout(5, 1));
        subtotalLabel = new JLabel("Subtotal: $0.00");
        taxLabel = new JLabel("Tax: $0.00");
        tipLabel = new JLabel("Tip: $0.00");
        totalLabel = new JLabel("Total: $0.00");

        JPanel tipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel tipPercentageLabel = new JLabel("Tip Percentage:");
        tipPercentageField = new JTextField("10", 5);

        PlainDocument doc = (PlainDocument) tipPercentageField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length);
            }
        });

        tipPercentageField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refresh();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refresh();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refresh();
            }
        });

        tipPanel.add(tipPercentageLabel);
        tipPanel.add(tipPercentageField);

        totalsPanel.add(subtotalLabel);
        totalsPanel.add(taxLabel);
        totalsPanel.add(tipPanel);
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
            if (item.getQuantity() > 0) { 
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
        int tipPercentage;
        try {
            String tipText = tipPercentageField.getText().trim();
            tipPercentage = tipText.isEmpty() ? 0 : Math.max(0, Integer.parseInt(tipText));
        } catch (NumberFormatException e) {
            tipPercentage = 0;
        }
        double tip = subtotal * tipPercentage / 100.0;
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

    
    public void removeItemFromCart(String itemName) {
        for (CartItem item : cart) {
            if (item.getMenuItem().getName().equals(itemName)) {
                item.decreaseQuantity();
                if (item.getQuantity() <= 0) {
                    cart.remove(item); 
                }
                break;
            }
        }
        refresh();
    }
}