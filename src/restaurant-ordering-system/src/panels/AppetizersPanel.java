import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppetizersPanel extends JPanel {
    private JPanel appetizersPanel;
    private JScrollPane scrollPane;

    public AppetizersPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
    }

    private void initializeComponents() {
        // Heading
        JLabel heading = new JLabel("Appetizers", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        add(heading, BorderLayout.NORTH);

        // Main panel for appetizers
        appetizersPanel = new JPanel();
        appetizersPanel.setLayout(new GridLayout(0, 1)); // Vertical layout for items
        loadAppetizers();

        // Scrollable view
        scrollPane = new JScrollPane(appetizersPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadAppetizers() {
        // Sample appetizer items
        String[][] appetizers = {
            {"Bruschetta", "Grilled bread topped with tomatoes and basil.", "5.99", "assets/images/appetizer1.jpg"},
            {"Stuffed Mushrooms", "Mushrooms filled with cheese and herbs.", "6.99", "assets/images/appetizer2.jpg"},
            {"Chicken Wings", "Spicy chicken wings served with ranch.", "8.99", "assets/images/appetizer3.jpg"},
            {"Caprese Salad", "Fresh mozzarella, tomatoes, and basil.", "7.99", "assets/images/appetizer4.jpg"},
            {"Spring Rolls", "Crispy rolls filled with vegetables.", "4.99", "assets/images/appetizer5.jpg"},
            {"Nachos", "Tortilla chips topped with cheese and jalapenos.", "6.49", "assets/images/appetizer6.jpg"}
        };

        for (String[] item : appetizers) {
            JPanel itemPanel = createMenuItemPanel(item[0], item[1], Double.parseDouble(item[2]), item[3]);
            appetizersPanel.add(itemPanel);
        }
    }

    private JPanel createMenuItemPanel(String name, String description, double cost, String imagePath) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        itemPanel.setBackground(getBackgroundColor());

        // Image
        JLabel imageLabel = new JLabel(new ImageIcon(imagePath));
        itemPanel.add(imageLabel, BorderLayout.WEST);

        // Details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(0, 1));
        detailsPanel.add(new JLabel(name, SwingConstants.LEFT));
        detailsPanel.add(new JLabel(description, SwingConstants.LEFT));
        detailsPanel.add(new JLabel(String.format("$%.2f", cost), SwingConstants.LEFT));

        // Quantity and buttons
        JPanel quantityPanel = new JPanel();
        JLabel quantityLabel = new JLabel("0");
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        removeButton.setEnabled(false);

        addButton.addActionListener(new ActionListener() {
            private int quantity = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                quantity++;
                quantityLabel.setText(String.valueOf(quantity));
                removeButton.setEnabled(quantity > 0);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            private int quantity = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (quantity > 0) {
                    quantity--;
                    quantityLabel.setText(String.valueOf(quantity));
                    removeButton.setEnabled(quantity == 0);
                }
            }
        });

        quantityPanel.add(quantityLabel);
        quantityPanel.add(addButton);
        quantityPanel.add(removeButton);
        detailsPanel.add(quantityPanel);
        itemPanel.add(detailsPanel, BorderLayout.CENTER);

        return itemPanel;
    }

    private Color getBackgroundColor() {
        return (appetizersPanel.getComponentCount() % 2 == 0) ? Color.LIGHT_GRAY : Color.WHITE;
    }
}