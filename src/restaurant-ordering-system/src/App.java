import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    private JFrame frame;
    private CardLayout cardLayout;
    private AppetizersPanel appetizersPanel;
    private EntreesPanel entreesPanel;
    private DessertsPanel dessertsPanel;
    private ViewCartPanel viewCartPanel;
    private CheckoutPanel checkoutPanel;

    public App() {
        frame = new JFrame("Restaurant Ordering System");
        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);
        
        appetizersPanel = new AppetizersPanel();
        entreesPanel = new EntreesPanel();
        dessertsPanel = new DessertsPanel();
        viewCartPanel = new ViewCartPanel();
        checkoutPanel = new CheckoutPanel();

        frame.add(appetizersPanel, "Appetizers");
        frame.add(entreesPanel, "Entrees");
        frame.add(dessertsPanel, "Desserts");
        frame.add(viewCartPanel, "View Cart");
        frame.add(checkoutPanel, "Checkout");

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        
        JMenuItem appetizersItem = new JMenuItem("Appetizers");
        appetizersItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "Appetizers");
            }
        });
        
        JMenuItem entreesItem = new JMenuItem("Entrees");
        entreesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "Entrees");
            }
        });
        
        JMenuItem dessertsItem = new JMenuItem("Desserts");
        dessertsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "Desserts");
            }
        });
        
        JMenuItem viewCartItem = new JMenuItem("View Cart");
        viewCartItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "View Cart");
            }
        });
        
        JMenuItem checkoutItem = new JMenuItem("Checkout");
        checkoutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "Checkout");
            }
        });

        menu.add(appetizersItem);
        menu.add(entreesItem);
        menu.add(dessertsItem);
        menu.add(viewCartItem);
        menu.add(checkoutItem);
        
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new App();
            }
        });
    }
}