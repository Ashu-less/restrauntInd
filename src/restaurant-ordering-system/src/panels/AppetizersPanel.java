package panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class AppetizersPanel extends JPanel {
    private JPanel appetizersPanel;
    private JScrollPane scrollPane;
    private List<CartItem> cart;
    private ViewCartPanel viewCartPanel;

    public AppetizersPanel(List<CartItem> cart, ViewCartPanel viewCartPanel) {
        this.cart = cart;
        this.viewCartPanel = viewCartPanel;
        setLayout(new BorderLayout());
        initializeComponents();
    }

    private void initializeComponents() {
        JLabel heading = new JLabel("Appetizers", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        add(heading, BorderLayout.NORTH);

        // Main panel for appetizers
        appetizersPanel = new JPanel();
        appetizersPanel.setLayout(new GridLayout(0, 1)); 
        loadAppetizers();

        scrollPane = new JScrollPane(appetizersPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadAppetizers() {
        
        String[][] appetizers = {
            {"Bruschetta", "Grilled bread topped with tomatoes and basil.", "5.99", "src/restaurant-ordering-system/src/panels/assets/images/Bruschetta.jpg"},
            {"Stuffed Mushrooms", "Mushrooms filled with cheese and herbs.", "6.99", "src/restaurant-ordering-system/src/panels/assets/images/Stuffed Mushrooms.jpg"},
            {"Chicken Wings", "Spicy chicken wings served with ranch.", "8.99", "src/restaurant-ordering-system/src/panels/assets/images/Chicken Wings.jpg"},
            {"Caprese Salad", "Fresh mozzarella, tomatoes, and basil.", "7.99", "src/restaurant-ordering-system/src/panels/assets/images/Caprese Salad.jpg"},
            {"Spring Rolls", "Crispy rolls filled with vegetables.", "4.99", "src/restaurant-ordering-system/src/panels/assets/images/Spring Rolls.jpg"},
            {"Nachos", "Tortilla chips topped with cheese and jalapenos.", "6.49", "src/restaurant-ordering-system/src/panels/assets/images/Nachos.jpg"}
        };

        for (String[] item : appetizers) {
            JPanel itemPanel = createMenuItemPanel(item[0], item[1], Double.parseDouble(item[2]), item[3]);
            appetizersPanel.add(itemPanel);
        }
    }

    private JPanel createMenuItemPanel(String name, String description, double cost, String imagePath) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        itemPanel.setBackground(appetizersPanel.getComponentCount() % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);

        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        itemPanel.add(imageLabel, BorderLayout.WEST);

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setOpaque(false); 
        JPanel textPanel = new JPanel(new GridLayout(0, 1));
        textPanel.setOpaque(false); 
        textPanel.add(new JLabel(name, SwingConstants.LEFT));
        textPanel.add(new JLabel(description, SwingConstants.LEFT));
        textPanel.add(new JLabel(String.format("$%.2f", cost), SwingConstants.LEFT));

        JPanel quantityPanel = new JPanel();
        quantityPanel.setOpaque(false); 
        JLabel quantityLabel = new JLabel("0");
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        removeButton.setEnabled(false);

        final int[] quantity = {0};

        addButton.addActionListener(e -> {
            quantity[0]++;
            quantityLabel.setText(String.valueOf(quantity[0]));
            removeButton.setEnabled(quantity[0] > 0);

            CartItem cartItem = findOrCreateCartItem(name, description, cost);
            cartItem.increaseQuantity();
            viewCartPanel.refresh();
        });

        removeButton.addActionListener(e -> {
            if (quantity[0] > 0) {
                quantity[0]--;
                quantityLabel.setText(String.valueOf(quantity[0]));
                removeButton.setEnabled(quantity[0] > 0);

                viewCartPanel.removeItemFromCart(name); 
            }
        });

        quantityPanel.add(quantityLabel);
        quantityPanel.add(addButton);
        quantityPanel.add(removeButton);

        detailsPanel.add(textPanel, BorderLayout.CENTER);
        detailsPanel.add(quantityPanel, BorderLayout.SOUTH);
        itemPanel.add(detailsPanel, BorderLayout.CENTER);

        return itemPanel;
    }

    private CartItem findOrCreateCartItem(String name, String description, double cost) {
        
        for (CartItem item : cart) {
            if (item.getMenuItem().getName().equals(name)) {
                return item;
            }
        }
        MenuItem menuItem = new MenuItem(name, description, cost, null);
        CartItem newItem = new CartItem(menuItem, 0);
        cart.add(newItem);
        return newItem;
}

    private Color getBackgroundColor() {
        return (appetizersPanel.getComponentCount() % 2 == 0) ? Color.LIGHT_GRAY : Color.WHITE;
    }
}