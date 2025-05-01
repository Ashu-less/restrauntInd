package panels;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class DessertsPanel extends JPanel {
    private JPanel dessertsPanel;
    private JScrollPane scrollPane;
    private List<CartItem> cart;
    private ViewCartPanel viewCartPanel;

    public DessertsPanel(List<CartItem> cart, ViewCartPanel viewCartPanel) {
        this.cart = cart;
        this.viewCartPanel = viewCartPanel;
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Desserts", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        add(heading, BorderLayout.NORTH);

        dessertsPanel = new JPanel();
        dessertsPanel.setLayout(new GridLayout(0, 1));

        for (int i = 1; i <= 4; i++) {
            dessertsPanel.add(createDessertItemPanel("Dessert " + i, "Delicious dessert " + i, 5.99 * i, "assets/images/dessert" + i + ".jpg"));
        }

        scrollPane = new JScrollPane(dessertsPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createDessertItemPanel(String name, String description, double cost, String imagePath) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        itemPanel.setBackground(itemPanel.getBackground().equals(Color.LIGHT_GRAY) ? Color.WHITE : Color.LIGHT_GRAY);
    
        JLabel imageLabel = new JLabel(new ImageIcon(imagePath));
        itemPanel.add(imageLabel, BorderLayout.WEST);
    
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(0, 1));
        detailsPanel.add(new JLabel(name, SwingConstants.LEFT));
        detailsPanel.add(new JLabel(description, SwingConstants.LEFT));
        detailsPanel.add(new JLabel("$" + String.format("%.2f", cost), SwingConstants.LEFT));
    
        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new FlowLayout());
    
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
            }
        });
    
        quantityPanel.add(quantityLabel);
        quantityPanel.add(addButton);
        quantityPanel.add(removeButton);
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