package presentation;

import model.BaseProduct;
import model.MenuItem;
import model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientGUI extends JFrame {

    private JButton searchButton= new JButton("Search");

    private JTextArea orderArea = new JTextArea();
    private JLabel totalPriceLabel = new JLabel("Total:");
    private JLabel totalPriceComputed = new JLabel("0 lei");

    private JButton logoutButton = new JButton("Logout");
    private JButton orderButton = new JButton("Order");
    private JButton clearButton = new JButton("Clear order");

    private JLabel allProductsLabel = new JLabel("All products");
    private JTextArea productsArea = new JTextArea();
    private JTable tableComponent;

    private int orderPrice = 0;
    private ArrayList<String> order = new ArrayList<>();

    String[] columnNames = {"Title"};

    private User loggedInUser;

    public ClientGUI(ListSelectionListener clientTableItemClickListener, ActionListener clientOrderListener,
                     ActionListener logoutButtonActionListener, ActionListener clearButtonActionListener,
                     ActionListener searchButtonActionLister, User loggedInUser) {
        this.loggedInUser = loggedInUser;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(1, 1));
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new GridLayout(1, 2));
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 3));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        JPanel scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));

        searchPanel.add(searchButton);

        pricePanel.add(totalPriceLabel);
        pricePanel.add(totalPriceComputed);

        orderPanel.add(orderArea);
        orderPanel.add(pricePanel);

        buttonsPanel.add(logoutButton);
        buttonsPanel.add(orderButton);
        buttonsPanel.add(clearButton);

        actionPanel.add(searchPanel);
        actionPanel.add(orderPanel);
        actionPanel.add(buttonsPanel);

        JScrollPane tableScrollPane = new JScrollPane(productsArea);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScrollPane.setEnabled(true);
        tableScrollPane.setPreferredSize(new Dimension(600, 1000));
        tableScrollPane.setVisible(true);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Title");
        tableComponent = new JTable(tableModel);

        tableComponent.setBounds(0, 0, 600, 1000);
        productsArea.add(tableComponent);

        tablePanel.add(allProductsLabel);
        tablePanel.add(tableScrollPane);

        mainPanel.add(actionPanel);
        mainPanel.add(tablePanel);

        tableComponent.getSelectionModel().addListSelectionListener(clientTableItemClickListener);

        logoutButton.addActionListener(logoutButtonActionListener);
        orderButton.addActionListener(clientOrderListener);
        clearButton.addActionListener(clearButtonActionListener);
        searchButton.addActionListener(searchButtonActionLister);

        orderArea.setEditable(false);
        productsArea.setEditable(false);
        this.setPreferredSize(new Dimension(600, 400));
        this.setContentPane(mainPanel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Client");
        setLocationRelativeTo(null);
    }

    public void addDataToTable(ArrayList<MenuItem> items) {
        items.stream().forEach(item -> {
            ((DefaultTableModel) tableComponent.getModel())
                    .addRow(new Object[]{item.getProductTitle()});
        });
    }

    public void addToOrderArea(MenuItem item) {
        BaseProduct product = (BaseProduct) item;
        orderArea.append(product.getTitle());
        orderArea.append("\n");
        orderPrice = orderPrice + product.getPrice();
        totalPriceComputed.setText(orderPrice + " LEI");

        this.order.add(item.getProductTitle());
    }

    public JTable getTableComponent(){
        return this.tableComponent;
    }

    public ArrayList<String> getSelectedMenuItems(){
        return order;
    }

    public void clearOrder(){
        order.clear();
        orderPrice = 0;
        totalPriceComputed.setText("0 lei");
        orderArea.setText("");
    }

    public void closeScreen(){
        this.setVisible(false);
    }
}
