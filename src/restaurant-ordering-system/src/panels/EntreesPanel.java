package panels;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class EntreesPanel extends JPanel {
    private JPanel entreesPanel;
    private JScrollPane scrollPane;
    private List<CartItem> cart;
    private ViewCartPanel viewCartPanel;
    
    private static final String[][] ENTREES = {
        {"Grilled Chicken", "Juicy grilled chicken breast.", "15.99", "src/restaurant-ordering-system/src/panels/assets/images/Grilled Chicken.jpg"},
        {"Vegetable Stir Fry", "Fresh vegetables in a savory sauce.", "12.99", "src/restaurant-ordering-system/src/panels/assets/images/Vegetable Stir Fry.jpg"},
        {"Salmon Fillet", "Grilled salmon with lemon butter.", "18.99", "src/restaurant-ordering-system/src/panels/assets/images/Salmon Fillet.jpg"},
        {"Beef Steak", "Tender beef steak cooked to perfection.", "20.99", "src/restaurant-ordering-system/src/panels/assets/images/Beef Steak.jpg"},
        {"Pasta Primavera", "Pasta with fresh vegetables and light sauce.", "14.99", "src/restaurant-ordering-system/src/panels/assets/images/Pasta Primavera.jpg"},
        {"Shrimp Scampi", "Shrimp sautéed in garlic butter sauce.", "19.99", "src/restaurant-ordering-system/src/panels/assets/images/Shrimp Scampi.jpg"},
        {"Chicken Alfredo", "Creamy Alfredo sauce with grilled chicken.", "16.99", "src/restaurant-ordering-system/src/panels/assets/images/Chicken Alfredo.jpg"},
        {"Eggplant Parmesan", "Breaded eggplant with marinara and cheese.", "13.99", "src/restaurant-ordering-system/src/panels/assets/images/Eggplant Parmesan.jpg"},
        {"Lamb Chops", "Grilled lamb chops with rosemary.", "22.99", "src/restaurant-ordering-system/src/panels/assets/images/Lamb_Chops.jpg"},
        {"Tofu Stir Fry", "Tofu with mixed vegetables in a savory sauce.", "11.99", "src/restaurant-ordering-system/src/panels/assets/images/Tofu Stir Fry.jpg"},
        {"Crab Cakes", "Golden crab cakes with tartar sauce.", "17.99", "src/restaurant-ordering-system/src/panels/assets/images/Crab Cakes.jpg"},
        {"BBQ Ribs", "Slow-cooked ribs with BBQ sauce.", "21.99", "src/restaurant-ordering-system/src/panels/assets/images/BBQ Ribs.jpg"}
    };

    public EntreesPanel(List<CartItem> cart, ViewCartPanel viewCartPanel) {
        this.cart = cart;
        this.viewCartPanel = viewCartPanel;
        setLayout(new BorderLayout());
        initializeEntreesPanel();
        scrollPane = new JScrollPane(entreesPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeEntreesPanel() {
        entreesPanel = new JPanel();
        entreesPanel.setLayout(new BoxLayout(entreesPanel, BoxLayout.Y_AXIS));

        for (String[] entree : ENTREES) {
            addMenuItem(entree[0], entree[1], Double.parseDouble(entree[2]), entreesPanel);
        }
    }

    private void addMenuItem(String name, String description, double cost, JPanel panel) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        itemPanel.setBackground(panel.getComponentCount() % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);

        JLabel imageLabel = new JLabel(new ImageIcon(name)); 
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

        panel.add(itemPanel);
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