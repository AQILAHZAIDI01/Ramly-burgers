package rbsys;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Menu {

    private JFrame frame;
    private JTextField txtPrice;
    private JTextField txtTotal;
    private JComboBox<String> cbProduct;
    private JComboBox<Integer> cbQuantity;
    private JTable productTable;
    private DefaultTableModel tableModel;

    private ArrayList<String> productNames;  // Store product names for combo box

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Menu window = new Menu();
                    window.getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected Window getFrame() {
        return frame;
    }

    /**
     * Create the application.
     */
    public Menu() {
        initialize();
        loadProducts(); // Load products from database when initializing
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
    	 frame = new JFrame();
         frame.setResizable(false);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(1128, 737); // Set the default size of the window
         frame.setLocationRelativeTo(null); // Center the window on the screen
         frame.setTitle("Ramly Burger System : Menu"); // Set the title of the window
         frame.getContentPane().setLayout(null);

        cbProduct = new JComboBox<>();
        cbProduct.setBounds(121, 234, 239, 41);
        cbProduct.addItem("default");  // Add default item
        frame.getContentPane().add(cbProduct);

        txtPrice = new JTextField();
        txtPrice.setEditable(false);
        txtPrice.setBounds(403, 235, 189, 40);
        frame.getContentPane().add(txtPrice);
        txtPrice.setColumns(10);

        cbQuantity = new JComboBox<>();
        cbQuantity.setBounds(642, 235, 156, 40);
        cbQuantity.addItem(0); // Add default item
        // Populate quantity from 1 to 10
        for (int i = 1; i <= 10; i++) {
            cbQuantity.addItem(i);
        }
        frame.getContentPane().add(cbQuantity);

        CustomButton btnAdd = new CustomButton("ADD");
        btnAdd.setBounds(885, 233, 124, 42);
        frame.getContentPane().add(btnAdd);

        txtTotal = new JTextField();
        txtTotal.setEditable(false);
        txtTotal.setColumns(10);
        txtTotal.setBounds(885, 535, 124, 41);
        frame.getContentPane().add(txtTotal);

        CustomButton btnClear = new CustomButton("CANCEL");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleCancelAction();
            }
        });

        btnClear.setBounds(121, 595, 124, 41);
        frame.getContentPane().add(btnClear);

        CustomButton btnPayment = new CustomButton("PROCEED TO PAYMENT");
        btnPayment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToBilling();
            }
        });

        btnPayment.setBounds(790, 596, 219, 38);
        frame.getContentPane().add(btnPayment);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(121, 338, 888, 187);
        frame.getContentPane().add(scrollPane);

        // Initialize JTable
        productTable = new JTable();
        scrollPane.setViewportView(productTable);
        String[] columns = {"Product", "Price", "Quantity", "Sum"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are non-editable
            }
        };
        productTable.setModel(tableModel);

        JLabel lblCasher = new JLabel("CASHIER");
        lblCasher.setHorizontalAlignment(SwingConstants.CENTER);
        lblCasher.setForeground(new Color(255, 192, 48)); // Set color to RGB (255, 192, 48)
        lblCasher.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 48));
        lblCasher.setBounds(408, 58, 287, 92);
        frame.getContentPane().add(lblCasher);

        
        JLabel lblCart = new JLabel("CART");
        lblCart.setHorizontalAlignment(SwingConstants.LEFT);
        lblCart.setForeground(new Color(255, 192, 48)); // Set color to RGB (255, 192, 48)
        lblCart.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 35));
        lblCart.setBounds(120, 285, 125, 59);
        frame.getContentPane().add(lblCart);

        // Action listeners
        cbProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updatePrice();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String product = (String) cbProduct.getSelectedItem();

                if (product == null || product.equals("default")) {
                    // Show validation message box with "Okay" button
                    JOptionPane.showMessageDialog(frame, "Please choose a product.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double price = 0.0;
                try {
                    price = Double.parseDouble(txtPrice.getText());
                } catch (NumberFormatException ex) {
                    // Handle parsing error if txtPrice.getText() is not a valid double
                    JOptionPane.showMessageDialog(frame, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int quantity = (int) cbQuantity.getSelectedItem();
                if (quantity == 0) {
                    JOptionPane.showMessageDialog(frame, "Please choose a quantity.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double sum = price * quantity;

                // Check if the product already exists in the table
                boolean productFound = false;
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    if (product.equals(tableModel.getValueAt(row, 0))) {
                        // Product found, update quantity and sum
                        int currentQuantity = Integer.parseInt((String) tableModel.getValueAt(row, 2));
                        currentQuantity += quantity;
                        double currentSum = price * currentQuantity;

                        // Update table row with new quantity and sum
                        tableModel.setValueAt(String.valueOf(currentQuantity), row, 2);
                        tableModel.setValueAt(String.format("%.2f", currentSum), row, 3);

                        productFound = true;
                        break;
                    }
                }

                if (!productFound) {
                    // Product not found, add new row to the table
                    String[] newRow = {product, String.format("%.2f", price), String.valueOf(quantity), String.format("%.2f", sum)};
                    tableModel.addRow(newRow);
                }

                // Clear the price text field
                txtPrice.setText("");

                // Reset cbProduct and cbQuantity to their default values
                cbProduct.setSelectedIndex(0);
                cbQuantity.setSelectedIndex(0);

                // Update total
                updateTotal();
            }
        });

        // Ensure the frame is visible
        frame.setVisible(true);
        
       
        
        JLabel lblTotal = new JLabel("TOTAL");
        lblTotal.setHorizontalAlignment(SwingConstants.LEFT);
        lblTotal.setForeground(new Color(255, 192, 48)); // Set color to RGB (255, 192, 48)
        lblTotal.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 35));
        lblTotal.setBounds(746, 535, 138, 41);
        frame.getContentPane().add(lblTotal);
        
        JLabel lblProduct_1 = new JLabel("Product");
        lblProduct_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblProduct_1.setForeground(new Color(255, 192, 48)); // Set color to RGB (255, 192, 48)
        lblProduct_1.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 30));
        lblProduct_1.setBounds(121, 182, 172, 59);
        frame.getContentPane().add(lblProduct_1);
        
        JLabel lblProduct_1_1 = new JLabel("Price");
        lblProduct_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblProduct_1_1.setForeground(new Color(255, 192, 48)); // Set color to RGB (255, 192, 48)
        lblProduct_1_1.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 30));
        lblProduct_1_1.setBounds(403, 182, 172, 59);
        frame.getContentPane().add(lblProduct_1_1);
        
        JLabel lblProduct_1_1_1 = new JLabel("Quantity");
        lblProduct_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblProduct_1_1_1.setForeground(new Color(255, 192, 48)); // Set color to RGB (255, 192, 48)
        lblProduct_1_1_1.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 30));
        lblProduct_1_1_1.setBounds(642, 182, 172, 59);
        frame.getContentPane().add(lblProduct_1_1_1);
        
     // Custom JPanel for background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/bgmenuu.png"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setBounds(0, 0, 1114, 700);
        frame.getContentPane().add(backgroundPanel);
        backgroundPanel.setLayout(null);
        
        CustomButton btnReturn = new CustomButton("ADD");
        btnReturn.setText("RETURN");
        btnReturn.setBounds(933, 40, 124, 42);
        frame.getContentPane().add(btnReturn);
        
        // Add action listener for the "Return" button
        btnReturn.addActionListener(e -> returnToMenu());
    }
    
    /**
     * Method to handle the return to the main menu
     */
    private void returnToMenu() {
        frame.dispose(); // Close the current frame
        main mainMenu = new main(); // Create a new instance of the menu class
        mainMenu.main(null); // Show the main menu
    }

    private void handleCancelAction() {
        int selectedRow = productTable.getSelectedRow();

        if (selectedRow == -1) {
            // No row selected, confirm if the user wants to clear all
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to cancel all?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                clearList(); // Clear the productTable
                txtTotal.setText("0.00"); // Reset the total field
            }
        } else {
            // Row selected, confirm if the user wants to delete the selected row
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the selected item?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow); // Remove the selected row
                updateTotal(); // Update the total
            }
        }
    }

    // Method to load products from database into cbProduct
    private Map<String, Integer> productMap = new HashMap<>();

    // Modify loadProducts method to populate cbProduct with product names
    private void loadProducts() {
        try {
            URL url = new URL("http://localhost/rbsystem/api.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject product = jsonArray.getJSONObject(i);
                    String productName = product.getString("product_name");
                    int productId = product.getInt("product_id");
                    String status = product.getString("status");

                    // Only add products with status "active"
                    if ("active".equalsIgnoreCase(status)) {
                        cbProduct.addItem(productName);
                        productMap.put(productName, productId); // Store in map for lookup
                    }
                }
            } else {
                System.out.println("Failed to fetch products: HTTP error code " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Modify updatePrice method to fetch price using product_id
    private void updatePrice() {
        String selectedProduct = (String) cbProduct.getSelectedItem();

        if (selectedProduct == null) {
            // Handle case where no product is selected, perhaps show a message
            return;
        }

        try {
            URL url = new URL("http://localhost/rbsystem/api.php/" + productMap.get(selectedProduct));  // Adjust URL as per your environment
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject product = new JSONObject(response.toString());
                
                // Check if the "price" field exists in the JSON response
                if (product.has("price")) {
                    double price = product.getDouble("price");
                    SwingUtilities.invokeLater(() -> {
                        txtPrice.setText(String.format("%.2f", price));
                    });
                } else {
                    // Handle case where "price" field is missing in the JSON response
                    System.out.println("Price not found for product: " + selectedProduct);
                    // Optionally set txtPrice to a default value or show an error message
                }
            } else {
                System.out.println("Failed to fetch product price: HTTP error code " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Method to add selected product to productTable
    private void addToList() {
        String product = (String) cbProduct.getSelectedItem();
        double price = Double.parseDouble(txtPrice.getText());
        int quantity = (int) cbQuantity.getSelectedItem();
        double sum = price * quantity;

        String[] row = {product, String.format("%.2f", price), String.valueOf(quantity), String.format("%.2f", sum)};
        tableModel.addRow(row);

        updateTotal();
    }

    // Method to update txtTotal based on productTable
    private void updateTotal() {
        double total = 0;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            total += Double.parseDouble((String) tableModel.getValueAt(row, 3));
        }
        txtTotal.setText(String.format("%.2f", total));
    }

    private void goToBilling() {
        try {
            JSONArray orders = new JSONArray();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                JSONObject order = new JSONObject();
                order.put("product_name", tableModel.getValueAt(i, 0).toString());
                order.put("price", Double.parseDouble(tableModel.getValueAt(i, 1).toString()));
                order.put("quantity", Integer.parseInt(tableModel.getValueAt(i, 2).toString()));
                orders.put(order);
            }
            double total = Double.parseDouble(txtTotal.getText());

            frame.dispose(); // Close current window
            new Billing(orders, total); // Open Billing window
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to open Billing window.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to clear productTable and reset txtTotal
    private void clearList() {
        tableModel.setRowCount(0); // Clear table
        txtTotal.setText(""); // Reset total
    }
}
