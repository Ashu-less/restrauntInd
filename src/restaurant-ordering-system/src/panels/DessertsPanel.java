package panels;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class DessertsPanel extends JPanel {
    private JPanel dessertsPanel;
    private JScrollPane scrollPane;
    private List<CartItem> cart;
    private ViewCartPanel viewCartPanel;

    private static final String[][] DESSERTS = {
        {"Chocolate Cake", "Rich and moist chocolate cake.", "6.99", "src/restaurant-ordering-system/src/panels/assets/images/Chocolate Cake.jpg"},
        {"Cheesecake", "Creamy cheesecake with a graham cracker crust.", "7.99", "src/restaurant-ordering-system/src/panels/assets/images/Cheesecake.jpg"},
        {"Ice Cream Sundae", "Vanilla ice cream with chocolate syrup and nuts.", "5.99", "src/restaurant-ordering-system/src/panels/assets/images/Ice Cream Sundae.jpg"},
        {"Apple Pie", "Classic apple pie with a flaky crust.", "4.99", "src/restaurant-ordering-system/src/panels/assets/images/Apple Pie.jpg"}
    };

    public DessertsPanel(List<CartItem> cart, ViewCartPanel viewCartPanel) {
        this.cart = cart;
        this.viewCartPanel = viewCartPanel;
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Desserts", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        add(heading, BorderLayout.NORTH);

        dessertsPanel = new JPanel();
        dessertsPanel.setLayout(new GridLayout(0, 1));

        for (String[] dessert : DESSERTS) {
            dessertsPanel.add(createDessertItemPanel(dessert[0], dessert[1], Double.parseDouble(dessert[2]), dessert[3]));
        }

        scrollPane = new JScrollPane(dessertsPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createDessertItemPanel(String name, String description, double cost, String imagePath) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout(10, 10));
        itemPanel.setBackground(dessertsPanel.getComponentCount() % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
    
        JLabel imageLabel = new JLabel(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        itemPanel.add(imageLabel, BorderLayout.WEST);
    
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setOpaque(false); 
        JPanel textPanel = new JPanel(new GridLayout(0, 1));
        textPanel.setOpaque(false); 
        textPanel.add(new JLabel(name, SwingConstants.LEFT));
        textPanel.add(new JLabel(description, SwingConstants.LEFT));
        textPanel.add(new JLabel("$" + String.format("%.2f", cost), SwingConstants.LEFT));
    
        JPanel quantityPanel = new JPanel();
        quantityPanel.setOpaque(false); 
        JLabel quantityLabel = new JLabel("Quantity: 0");
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        removeButton.setEnabled(false);
    
        final int[] quantity = {0};
    
        addButton.addActionListener(e -> {
            quantity[0]++;
            quantityLabel.setText("Quantity: " + quantity[0]);
            removeButton.setEnabled(quantity[0] > 0);
    
            CartItem cartItem = findOrCreateCartItem(name, description, cost);
            cartItem.increaseQuantity();
            viewCartPanel.refresh();
        });
    
        removeButton.addActionListener(e -> {
            if (quantity[0] > 0) {
                quantity[0]--;
                quantityLabel.setText("Quantity: " + quantity[0]);
                removeButton.setEnabled(quantity[0] > 0);

                CartItem cartItem = findOrCreateCartItem(name, description, cost);
                cartItem.decreaseQuantity();
                if (cartItem.getQuantity() == 0) {
                    cart.remove(cartItem);
                }
                viewCartPanel.refresh();
            }
        });
    
        quantityPanel.add(quantityLabel);
        quantityPanel.add(addButton);
        quantityPanel.add(removeButton);
        detailsPanel.add(textPanel, BorderLayout.NORTH);
        detailsPanel.add(quantityPanel);
    
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
}