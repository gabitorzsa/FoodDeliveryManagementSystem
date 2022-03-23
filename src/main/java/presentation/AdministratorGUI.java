package presentation;

import model.BaseProduct;
import model.MenuItem;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdministratorGUI extends JFrame {
    private JLabel nameLabel = new JLabel("Base product name: ");
    private JTextField tfName = new JTextField(10);
    private JLabel ratingLabel = new JLabel("Rating: ");
    private JTextField tfRating = new JTextField(10);
    private JLabel caloriesLabel = new JLabel("Calories: ");
    private JTextField tfCalories = new JTextField(10);
    private JLabel proteinLabel = new JLabel("Protein: ");
    private JTextField tfProtein = new JTextField(10);
    private JLabel fatLabel = new JLabel("Fat: ");
    private JTextField tfFat = new JTextField(10);
    private JLabel sodiumLabel = new JLabel("Sodium: ");
    private JTextField tfSodium = new JTextField(10);
    private JLabel priceLabel = new JLabel("Price: ");
    private JTextField tfPrice = new JTextField(10);

    private JButton deleteProduct = new JButton("Delete product");
    private JButton addAndEditButton = new JButton("Add/edit");
    private JButton logoutButton = new JButton("Logout");
    private JButton addCompositeButton = new JButton("Add composite");

    private JLabel allProductsLabel = new JLabel("All products");
    private JTextArea productsArea = new JTextArea();
    private JTable tableComponent;

    String[] columnNames = {"Title"};

    public AdministratorGUI(ListSelectionListener tableItemClickListener, ActionListener addButtonActionListener, ActionListener deleteButtonActionListener, ActionListener logoutButtonActionListener) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        JPanel labelAndFieldsPanel = new JPanel();
        labelAndFieldsPanel.setLayout(new GridLayout(7, 2));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        JPanel scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));

        labelAndFieldsPanel.add(nameLabel);
        labelAndFieldsPanel.add(tfName);
        labelAndFieldsPanel.add(ratingLabel);
        labelAndFieldsPanel.add(tfRating);
        labelAndFieldsPanel.add(caloriesLabel);
        labelAndFieldsPanel.add(tfCalories);
        labelAndFieldsPanel.add(proteinLabel);
        labelAndFieldsPanel.add(tfProtein);
        labelAndFieldsPanel.add(fatLabel);
        labelAndFieldsPanel.add(tfFat);
        labelAndFieldsPanel.add(sodiumLabel);
        labelAndFieldsPanel.add(tfSodium);
        labelAndFieldsPanel.add(priceLabel);
        labelAndFieldsPanel.add(tfPrice);

        buttonsPanel.add(logoutButton);
        buttonsPanel.add(addAndEditButton);
        buttonsPanel.add(deleteProduct);
        buttonsPanel.add(addCompositeButton);

        actionPanel.add(labelAndFieldsPanel);
        actionPanel.add(buttonsPanel);

        JScrollPane tableScrollPane = new JScrollPane(productsArea);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScrollPane.setEnabled(true);
        tableScrollPane.setPreferredSize(new Dimension(700, 100));
        tableScrollPane.setVisible(true);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Title");
        tableComponent = new JTable(tableModel);

        tableComponent.setBounds(0, 0, 700, 1000);
        productsArea.add(tableComponent);

        addAndEditButton.addActionListener(addButtonActionListener);
        deleteProduct.addActionListener(deleteButtonActionListener);
        logoutButton.addActionListener(logoutButtonActionListener);

        tablePanel.add(allProductsLabel);
        tablePanel.add(tableScrollPane);

        mainPanel.add(actionPanel);
        mainPanel.add(tablePanel);

        tableComponent.getSelectionModel().addListSelectionListener(tableItemClickListener);

        productsArea.setEditable(false);
        this.setPreferredSize(new Dimension(800, 300));
        this.setContentPane(mainPanel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Administrator");
        setLocationRelativeTo(null);
    }

    public void setItems(ArrayList<MenuItem> items) {
        ((DefaultTableModel) tableComponent.getModel()).setRowCount(0);
        items.stream().forEach(item -> {
            ((DefaultTableModel) tableComponent.getModel())
                    .addRow(new Object[]{item.getProductTitle()});
        });
        tableComponent.repaint();
    }

    public void addItem(MenuItem item) {
        ((DefaultTableModel) tableComponent.getModel())
                .addRow(new Object[]{item.getProductTitle()});
        tableComponent.repaint();
    }

    public void setEditMenuItem(MenuItem item) {
        BaseProduct model = (BaseProduct) item;
        System.out.println(model.toString());
        tfName.setText(model.getTitle());
        tfRating.setText(String.valueOf(model.getRating()));
        tfCalories.setText(String.valueOf(model.getCalories()));
        tfFat.setText(String.valueOf(model.getFat()));
        tfProtein.setText(String.valueOf(model.getProtein()));
        tfSodium.setText(String.valueOf(model.getSodium()));
        tfPrice.setText(String.valueOf(model.getPrice()));
    }

    public MenuItem getEditMenuItem(){
        BaseProduct model = new BaseProduct();
        model.setTitle(tfName.getText());
        model.setRating(Double.parseDouble(tfRating.getText()));
        model.setCalories(Integer.parseInt(tfCalories.getText()));
        model.setFat(Integer.parseInt(tfFat.getText()));
        model.setProtein(Integer.parseInt(tfProtein.getText()));
        model.setSodium(Integer.parseInt(tfSodium.getText()));
        model.setPrice(Integer.parseInt(tfPrice.getText()));

        return model;
    }

    public void clearEditArea() {
        tfName.setText("");
        tfRating.setText("");
        tfProtein.setText("");
        tfFat.setText("");
        tfSodium.setText("");
        tfCalories.setText("");
        tfPrice.setText("");
    }

    public JTable getTableComponent(){
        return this.tableComponent;
    }
}
