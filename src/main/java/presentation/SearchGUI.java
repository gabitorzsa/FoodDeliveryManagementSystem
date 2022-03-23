package presentation;

import business.DeliveryService;
import business.UserService;
import data.ReportGenerator;
import model.BaseProduct;
import model.MenuItem;
import model.Order;
import model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchGUI extends JFrame {

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

    private JButton searchButton = new JButton("Search");
    private JButton closeButton = new JButton("Close");
    private JTextArea searchArea = new JTextArea();

    public SearchGUI(ActionListener searchProductActionListener, ActionListener closeButtonActionListener) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(8, 2));

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        searchPanel.add(nameLabel);
        searchPanel.add(tfName);
        searchPanel.add(ratingLabel);
        searchPanel.add(tfRating);
        searchPanel.add(caloriesLabel);
        searchPanel.add(tfCalories);
        searchPanel.add(proteinLabel);
        searchPanel.add(tfProtein);
        searchPanel.add(fatLabel);
        searchPanel.add(tfFat);
        searchPanel.add(sodiumLabel);
        searchPanel.add(tfSodium);
        searchPanel.add(priceLabel);
        searchPanel.add(tfPrice);
        searchPanel.add(closeButton);
        searchPanel.add(searchButton);

        displayPanel.add(searchArea);

        mainPanel.add(searchPanel);
        mainPanel.add(displayPanel);

        searchButton.addActionListener(searchProductActionListener);
        closeButton.addActionListener(closeButtonActionListener);

        searchArea.setEditable(false);
        this.setPreferredSize(new Dimension(600, 300));
        this.setContentPane(mainPanel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Search");
        setLocationRelativeTo(null);
    }

    public List<MenuItem> searchProductArea(ArrayList<MenuItem> items) {

        Stream<MenuItem> stream = items.stream();

        if (tfName.getText().length() > 0) {
            System.out.println(tfName.getText());
            stream = stream.filter(item -> ((BaseProduct) item).getTitle().toLowerCase().contains(tfName.getText().toLowerCase()));
        }
         if (tfRating.getText().length() > 0) {
            stream = stream.filter(item -> ((BaseProduct) item).getRating() == Double.parseDouble(tfRating.getText()));
        }
         if (tfCalories.getText().length() > 0) {
            stream = stream.filter(item -> ((BaseProduct) item).getCalories() == Integer.parseInt(tfCalories.getText()));
        }
         if (tfFat.getText().length() > 0) {
            stream = stream.filter(item -> ((BaseProduct) item).getFat() == Integer.parseInt(tfFat.getText()));
        }
         if (tfProtein.getText().length() > 0) {
            stream = stream.filter(item -> ((BaseProduct) item).getProtein() == Integer.parseInt(tfProtein.getText()));
        }
         if (tfSodium.getText().length() > 0) {
            stream = stream.filter(item -> ((BaseProduct) item).getSodium() == Integer.parseInt(tfSodium.getText()));
        }
         if (tfPrice.getText().length() > 0) {
            stream = stream.filter(item -> ((BaseProduct) item).getPrice() == Integer.parseInt(tfPrice.getText()));
        }

        List<MenuItem> result = stream.collect(Collectors.toList());

        result.forEach(item -> {
            System.out.println(item.toString());
        });

        return result;
    }

    public void displaySearchResult(ArrayList<MenuItem> items) {
        List<MenuItem> result = searchProductArea(items);

        clearSearchArea();

        result.forEach(item ->{
            searchArea.append(item.getProductTitle());
            searchArea.append("\n");
        });
    }
    public void closeScreen() {
        this.setVisible(false);
    }

    public void clearSearchArea(){
        searchArea.setText("");
    }
}
