package panels;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EntreesPanel extends JPanel {
    private JPanel entreesPanel;
    private JScrollPane scrollPane;

    public EntreesPanel() {
        setLayout(new BorderLayout());
        initializeEntreesPanel();
        scrollPane = new JScrollPane(entreesPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeEntreesPanel() {
        entreesPanel = new JPanel();
        entreesPanel.setLayout(new BoxLayout(entreesPanel, BoxLayout.Y_AXIS));

        // Example categories for entrees
        String[] categories = {"Meat Dishes", "Vegetarian Dishes", "Seafood Dishes"};
        for (String category : categories) {
            JLabel categoryLabel = new JLabel(category);
            categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
            entreesPanel.add(categoryLabel);
            entreesPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Example menu items for each category
            for (int i = 1; i <= 4; i++) {
                addMenuItem("Entree " + i + " from " + category, "Delicious " + category.toLowerCase() + " item " + i, 12.99 + i, entreesPanel);
            }
        }
    }

    private void addMenuItem(String name, String description, double cost, JPanel panel) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new GridLayout(1, 5));
        itemPanel.setBackground(panel.getComponentCount() % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);

        JLabel nameLabel = new JLabel(name);
        JLabel descriptionLabel = new JLabel(description);
        JLabel costLabel = new JLabel("$" + String.format("%.2f", cost));
        JLabel quantityLabel = new JLabel("0");
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");

        addButton.addActionListener(new ActionListener() {
            private int quantity = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                quantity++;
                quantityLabel.setText(String.valueOf(quantity));
            }
        });

        removeButton.addActionListener(new ActionListener() {
            private int quantity = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (quantity > 0) {
                    quantity--;
                    quantityLabel.setText(String.valueOf(quantity));
                }
            }
        });

        removeButton.setEnabled(false);
        itemPanel.add(nameLabel);
        itemPanel.add(descriptionLabel);
        itemPanel.add(costLabel);
        itemPanel.add(quantityLabel);
        itemPanel.add(addButton);
        itemPanel.add(removeButton);

        panel.add(itemPanel);
    }
}