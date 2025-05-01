package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import panels.AppetizersPanel;
import panels.CheckoutPanel;
import panels.EntreesPanel;
import panels.DessertsPanel;
import panels.ViewCartPanel;




public class main {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public main() {
        frame = new JFrame("Restaurant Ordering System");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        List<CartItem> cart = new ArrayList<>();

        AppetizersPanel appetizersPanel = new AppetizersPanel();
        EntreesPanel entreesPanel = new EntreesPanel();
        DessertsPanel dessertsPanel = new DessertsPanel();
        ViewCartPanel viewCartPanel = new ViewCartPanel(cart);
        CheckoutPanel checkoutPanel = new CheckoutPanel();



        mainPanel.add(appetizersPanel, "Appetizers");
        mainPanel.add(entreesPanel, "Entrees");
        mainPanel.add(dessertsPanel, "Desserts");
        mainPanel.add(viewCartPanel, "View Cart");
        mainPanel.add(checkoutPanel, "Checkout");

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        String[] options = { "Appetizers", "Entrees", "Desserts", "View Cart", "Checkout" };
        for (String option : options) {
            JMenuItem item = new JMenuItem(option);
            item.addActionListener(e -> cardLayout.show(mainPanel, option));
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
