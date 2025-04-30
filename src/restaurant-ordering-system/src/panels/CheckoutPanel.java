import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckoutPanel extends JPanel {
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JTextField tipField;
    private JLabel subtotalLabel, taxLabel, tipLabel, totalLabel;
    private final double TAX_RATE = 0.0825;

    public CheckoutPanel() {
        setLayout(new BorderLayout());

        // Create the table model and JTable
        String[] columnNames = {"Item Name", "Quantity", "Cost", "Extended Cost"};
        tableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        
        // Create the tip input field
        tipField = new JTextField(10);
        tipField.setToolTipText("Enter tip percentage");

        // Create labels for subtotal, tax, tip, and total
        subtotalLabel = new JLabel("Subtotal: $0.00");
        taxLabel = new JLabel("Tax: $0.00");
        tipLabel = new JLabel("Tip: $0.00");
        totalLabel = new JLabel("Total: $0.00");

        // Create a panel for the tip input and labels
        JPanel tipPanel = new JPanel();
        tipPanel.add(new JLabel("Tip Percentage:"));
        tipPanel.add(tipField);
        
        JPanel totalsPanel = new JPanel(new GridLayout(4, 1));
        totalsPanel.add(subtotalLabel);
        totalsPanel.add(taxLabel);
        totalsPanel.add(tipLabel);
        totalsPanel.add(totalLabel);

        // Add components to the main panel
        add(scrollPane, BorderLayout.CENTER);
        add(tipPanel, BorderLayout.NORTH);
        add(totalsPanel, BorderLayout.SOUTH);

        // Add action listener for tip field
        tipField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTotals();
            }
        });
    }

    public void addCartItem(String itemName, int quantity, double cost) {
        double extendedCost = quantity * cost;
        tableModel.addRow(new Object[]{itemName, quantity, String.format("%.2f", cost), String.format("%.2f", extendedCost)});
        updateTotals();
    }

    private void updateTotals() {
        double subtotal = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            subtotal += Double.parseDouble((String) tableModel.getValueAt(i, 3));
        }
        double tax = subtotal * TAX_RATE;
        double tipPercentage = 0;
        try {
            tipPercentage = Double.parseDouble(tipField.getText()) / 100;
        } catch (NumberFormatException e) {
            tipPercentage = 0;
        }
        double tip = subtotal * tipPercentage;
        double total = subtotal + tax + tip;

        subtotalLabel.setText("Subtotal: $" + String.format("%.2f", subtotal));
        taxLabel.setText("Tax: $" + String.format("%.2f", tax));
        tipLabel.setText("Tip: $" + String.format("%.2f", tip));
        totalLabel.setText("Total: $" + String.format("%.2f", total));
    }
}