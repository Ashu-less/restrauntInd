package panels;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;



// and have a text Field for the tip

public class main {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public main() {
        frame = new JFrame("Restaurant Ordering System");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(new BorderLayout()); 

        List<CartItem> cart = new ArrayList<>();
        ViewCartPanel viewCartPanel = new ViewCartPanel(cart);
        AppetizersPanel appetizersPanel = new AppetizersPanel(cart, viewCartPanel);
        EntreesPanel entreesPanel = new EntreesPanel(cart, viewCartPanel);
        DessertsPanel dessertsPanel = new DessertsPanel(cart, viewCartPanel);

        JPanel cardPanel = new JPanel(cardLayout); 
        cardPanel.add(appetizersPanel, "Appetizers");
        cardPanel.add(entreesPanel, "Entrees");
        cardPanel.add(dessertsPanel, "Desserts");
        cardPanel.add(viewCartPanel, "View Cart");

        mainPanel.add(cardPanel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        String[] options = { "Appetizers", "Entrees", "Desserts", "View Cart" };
        for (String option : options) {
            JMenuItem item = new JMenuItem(option);
            item.addActionListener(e -> cardLayout.show(cardPanel, option));
            menu.add(item);
        }

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new main());
    }
}
