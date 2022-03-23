package presentation;

import business.DeliveryService;
import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EmployeeGUI extends JFrame implements Observer {

    private JLabel orderLabel = new JLabel("Active orders: ");
    private JTextArea orderTableArea = new JTextArea(40, 40);
    private JButton logOutButton = new JButton("Log out");

    private ArrayList<String> activeOrders = new ArrayList<>();

    public EmployeeGUI(ActionListener logoutButtonActionListener) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(orderLabel);
        mainPanel.add(orderTableArea);
        mainPanel.add(logOutButton);

        logOutButton.addActionListener(logoutButtonActionListener);

        orderTableArea.setEditable(false);
        this.setPreferredSize(new Dimension(400,400));
        this.setContentPane(mainPanel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Employee");
    }

    public void addOrder(Order order) {

        orderTableArea.append("------ Order ID: " + order.getOrderID() + " ------");
        orderTableArea.append("\n");
        order.getItems().forEach(item -> {
            orderTableArea.append(item.getProductTitle());
            orderTableArea.append("\n");
        });
        orderTableArea.append("---------------------");
        orderTableArea.append("\n");

        this.activeOrders.add(order.toString());
    }

    @Override
    public void update(DeliveryService deliveryService) {

    }
}
