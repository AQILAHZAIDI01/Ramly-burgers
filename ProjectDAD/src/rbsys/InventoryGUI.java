package rbsys;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.Font;
import javax.swing.SwingConstants;

public class InventoryGUI {

    private JFrame frame;
    private JTextField Product_id;
    private JTextField P_name;
    private JTextField Quantity;
    private JTextField Price;
    private JComboBox<String> Status;
    private JButton btnFind;
    private JButton btnUpdate;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;
    private JLabel lblNewLabel_4;
    private JLabel lblNewLabel_5;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane_1;
    private JButton btnReturn;
  //  private JLabel lblNewLabel_6;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InventoryGUI window = new InventoryGUI();
                    window.getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected JFrame getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}

	/**
     * Create the application.
     */
    public InventoryGUI() {
        initialize();
        loadData(); // Load data when the application starts
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
         frame.setTitle("Ramly Burger System : Inventory"); // Set the title of the window
         frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("INVENTORY");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(409, 32, 287, 92);
        lblNewLabel.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 48));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        frame.getContentPane().add(lblNewLabel);

        Product_id = new JTextField();
        Product_id.setFont(new Font("Tahoma", Font.BOLD, 12));
        Product_id.setBounds(152, 159, 117, 37);
        frame.getContentPane().add(Product_id);
        Product_id.setColumns(10);

        P_name = new JTextField();
        P_name.setBackground(new Color(254, 227, 137));
        P_name.setForeground(new Color(0, 0, 0));
        P_name.setFont(new Font("Tahoma", Font.BOLD, 12));
        P_name.setEditable(false);
        P_name.setBounds(152, 236, 183, 35);
        P_name.setColumns(10);
        frame.getContentPane().add(P_name);

        Quantity = new JTextField();
        Quantity.setBackground(new Color(254, 227, 137));
        Quantity.setFont(new Font("Tahoma", Font.BOLD, 12));
        Quantity.setBounds(359, 236, 126, 35);
        Quantity.setColumns(10);
        frame.getContentPane().add(Quantity);

        Price = new JTextField();
        Price.setBackground(new Color(254, 227, 137));
        Price.setFont(new Font("Tahoma", Font.BOLD, 12));
        Price.setBounds(508, 236, 126, 35);
        Price.setColumns(10);
        frame.getContentPane().add(Price);

        Status = new JComboBox<>(new String[]{"active", "inactive"});
        Status.setBackground(new Color(254, 227, 137));
        Status.setFont(new Font("Tahoma", Font.BOLD, 12));
        Status.setBounds(644, 232, 137, 39);
        frame.getContentPane().add(Status);

        CustomButton btnUpdate = new CustomButton("UPDATE");
        btnUpdate.setBounds(803, 232, 152, 39);
        frame.getContentPane().add(btnUpdate);
        

        CustomButton btnFind = new CustomButton("FIND");
        btnFind.setBounds(279, 159, 138, 39);
        frame.getContentPane().add(btnFind);

        lblNewLabel_1 = new JLabel("Product ID :");
        lblNewLabel_1.setForeground(new Color(0, 0, 0));
        lblNewLabel_1.setBounds(152, 140, 96, 13);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        frame.getContentPane().add(lblNewLabel_1);

        lblNewLabel_2 = new JLabel("Product Name :");
        lblNewLabel_2.setForeground(new Color(0, 0, 0));
        lblNewLabel_2.setBounds(152, 213, 183, 13);
        lblNewLabel_2.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
        frame.getContentPane().add(lblNewLabel_2);

        lblNewLabel_3 = new JLabel("Quantity :");
        lblNewLabel_3.setForeground(new Color(0, 0, 0));
        lblNewLabel_3.setBounds(359, 213, 104, 13);
        lblNewLabel_3.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
        frame.getContentPane().add(lblNewLabel_3);

        lblNewLabel_4 = new JLabel("Price (Per Unit) :");
        lblNewLabel_4.setForeground(new Color(0, 0, 0));
        lblNewLabel_4.setBounds(508, 213, 144, 13);
        lblNewLabel_4.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
        frame.getContentPane().add(lblNewLabel_4);

        lblNewLabel_5 = new JLabel("Status :");
        lblNewLabel_5.setForeground(new Color(255, 255, 255));
        lblNewLabel_5.setBounds(644, 213, 140, 13);
        lblNewLabel_5.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
        frame.getContentPane().add(lblNewLabel_5);

        String[] columnNames = {"Bil", "Product", "Quantity", "Price (Unit)", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        
        CustomButton btnReturn = new CustomButton("RETURN");
        btnReturn.setText("RETURN");
        btnReturn.setBounds(914, 46, 140, 43);
        frame.getContentPane().add(btnReturn);

        scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(155, 291, 800, 351);
        frame.getContentPane().add(scrollPane_1);

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane_1.setViewportView(scrollPane);

        // Apply custom renderer to the Status column
        TableColumn statusColumn = table.getColumnModel().getColumn(4); // Assuming "Status" is the 5th column (index 4)
        statusColumn.setCellRenderer(new CustomTableCellRenderer());
        
        ImageIcon icon = new ImageIcon("logo.png");
        JLabel lbl_pic = new JLabel(icon);
        lbl_pic.setBounds(10, 10, 222, 114);
        frame.getContentPane().add(lbl_pic);
        lbl_pic.setText("");
        
        ImageIcon icon2 = new ImageIcon("bg.jpg");
        JLabel lblNewLabel_6 = new JLabel(icon2);
        lblNewLabel_6.setBounds(-31, -32, 1161, 772);
        frame.getContentPane().add(lblNewLabel_6);
        lbl_pic.setText("");
        
//        CustomButton btnReturn = new CustomButton("RETURN");
//        btnReturn.setForeground(Color.BLACK);
//        btnReturn.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 18));
//        btnReturn.setBackground(Color.YELLOW);
//        btnReturn.setBounds(824, 77, 131, 30);
//        frame.getContentPane().add(btnReturn);
//       
      
        

        // Add action listener for the "Find" button
        btnFind.addActionListener(e -> findProductById());

        // Add action listener for the "Update" button
        btnUpdate.addActionListener(e -> updateProduct());
        
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

    /**
     * Load data from the RESTful API and populate the table.
     */
    private void loadData() {
        System.out.println("Starting loadData..."); // Debugging statement
        try {
            URL url = new URL("http://localhost/rbsystem/api.php"); // Ensure this URL is correct
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

            String jsonResponse = sb.toString();
            System.out.println("Response from API: " + jsonResponse); // Debugging statement

            // Parse JSON response
            JSONArray jsonArray = new JSONArray(jsonResponse);
            System.out.println("Parsed JSON array length: " + jsonArray.length()); // Debugging statement

            tableModel.setRowCount(0); // Clear existing rows

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int bil = jsonObject.getInt("product_id");
                String product = jsonObject.getString("product_name");
                int quantity = jsonObject.getInt("quantity");
                double price = jsonObject.getDouble("price");
                String status = jsonObject.getString("status");

                System.out.println("Adding row: " + bil + ", " + product + ", " + quantity + ", " + price + ", " + status); // Debugging statement

                tableModel.addRow(new Object[]{bil, product, quantity, price, status});
            }

            // Notify the table that the data has changed
            tableModel.fireTableDataChanged();
            System.out.println("Data loaded into table model."); // Debugging statement

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findProductById() {
        String productId = Product_id.getText().trim();
        if (productId.isEmpty()) {
            System.out.println("Product ID is empty. Loading all products.");
            loadData();
            return;
        }

        try {
            URL url = new URL("http://localhost/rbsystem/api.php/" + productId); // Update endpoint
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

            // Parse JSON response
            JSONObject jsonObject = new JSONObject(sb.toString());
            if (!jsonObject.has("product_id")) {
                System.out.println("Product not found. Loading all products.");
                loadData();
                return;
            }

            int bil = jsonObject.getInt("product_id");
            String product = jsonObject.getString("product_name");
            int quantity = jsonObject.getInt("quantity");
            double price = jsonObject.getDouble("price");
            String status = jsonObject.getString("status");

            // Clear existing rows and add the found product
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{bil, product, quantity, price, status});

            // Populate the text fields with the product data
            P_name.setText(product);
            Quantity.setText(String.valueOf(quantity));
            Price.setText(String.valueOf(price));
            Status.setSelectedItem(status);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while finding product. Loading all products.");
            loadData();
        }
    }

    private void updateProduct() {
        String productId = Product_id.getText().trim();
        String productName = P_name.getText().trim();
        String quantityStr = Quantity.getText().trim();
        String priceStr = Price.getText().trim();
        String status = (String) Status.getSelectedItem();

        if (productId.isEmpty() || productName.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty() || status.isEmpty()) {
            System.out.println("All fields must be filled out.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);

            // Update status based on quantity
            if (quantity > 0) {
                status = "active";
            } else {
                status = "inactive";
            }

            URL url = new URL("http://localhost/rbsystem/api.php/" + productId); // Update endpoint
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create JSON object for the updated product
            JSONObject productJson = new JSONObject();
            productJson.put("product_id", productId);
            productJson.put("product_name", productName);
            productJson.put("quantity", quantity);
            productJson.put("price", price);
            productJson.put("status", status);

            // Write JSON data to the request
            OutputStream os = conn.getOutputStream();
            os.write(productJson.toString().getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();
            System.out.println("Response from API: " + sb.toString()); // Debugging statement

            // Reload the data to reflect the updated product
            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
