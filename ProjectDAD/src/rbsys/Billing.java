package rbsys;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Billing {

    private JFrame frame;
    private JTextField txtTotal;
    private JTextField txtPayment;
    private JTextField txtBalance;
    private DefaultTableModel tableModel;

    public Billing(JSONArray orders, double total) throws JSONException {
        initialize(orders, total);
    }

    public void initialize(JSONArray orders, double total) throws JSONException {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1128, 737);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Ramly Burger System : Billing");
        frame.getContentPane().setLayout(null);

        JLabel lblBilling = new JLabel("BILLING");
        lblBilling.setHorizontalAlignment(SwingConstants.CENTER);
        lblBilling.setForeground(new Color(255, 192, 48));
        lblBilling.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 48));
        lblBilling.setBounds(408, 58, 287, 92);
        frame.getContentPane().add(lblBilling);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(121, 180, 888, 300);
        frame.getContentPane().add(scrollPane);

        JTable receiptTable = new JTable();
        scrollPane.setViewportView(receiptTable);
        String[] columns = {"Product", "Price", "Quantity", "Sum"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        receiptTable.setModel(tableModel);

        for (int i = 0; i < orders.length(); i++) {
            JSONObject order = orders.getJSONObject(i);
            String product = order.getString("product_name");
            double price = order.getDouble("price");
            int quantity = order.getInt("quantity");
            double sum = price * quantity;
            String[] row = {product, String.format("%.2f", price), String.valueOf(quantity), String.format("%.2f", sum)};
            tableModel.addRow(row);
        }

        JLabel lblTotal = new JLabel("TOTAL");
        lblTotal.setHorizontalAlignment(SwingConstants.LEFT);
        lblTotal.setForeground(new Color(255, 192, 48));
        lblTotal.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 35));
        lblTotal.setBounds(681, 495, 203, 41);
        frame.getContentPane().add(lblTotal);

        txtTotal = new JTextField();
        txtTotal.setEditable(false);
        txtTotal.setColumns(10);
        txtTotal.setBounds(885, 495, 124, 41);
        txtTotal.setText(String.format("%.2f", total));
        frame.getContentPane().add(txtTotal);

        JLabel lblPayment = new JLabel("PAYMENT");
        lblPayment.setHorizontalAlignment(SwingConstants.LEFT);
        lblPayment.setForeground(new Color(255, 192, 48));
        lblPayment.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 35));
        lblPayment.setBounds(681, 545, 214, 41);
        frame.getContentPane().add(lblPayment);

        txtPayment = new JTextField();
        txtPayment.setColumns(10);
        txtPayment.setBounds(885, 545, 124, 41);
        frame.getContentPane().add(txtPayment);

        JLabel lblBalance = new JLabel("BALANCE");
        lblBalance.setHorizontalAlignment(SwingConstants.LEFT);
        lblBalance.setForeground(new Color(255, 192, 48));
        lblBalance.setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 35));
        lblBalance.setBounds(681, 595, 214, 41);
        frame.getContentPane().add(lblBalance);

        txtBalance = new JTextField();
        txtBalance.setEditable(false);
        txtBalance.setColumns(10);
        txtBalance.setBounds(885, 595, 124, 41);
        frame.getContentPane().add(txtBalance);

        CustomButton btnCalculate = new CustomButton("CALCULATE");
        btnCalculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateBalance(total);
            }
        });
        btnCalculate.setBounds(731, 646, 138, 41);
        frame.getContentPane().add(btnCalculate);

        CustomButton btnPay = new CustomButton("PAY");
        btnPay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handlePayment(orders, total); // Call handlePayment with orders and total
            }
        });
        btnPay.setBounds(885, 646, 124, 41);
        frame.getContentPane().add(btnPay);

        CustomButton btnReturn = new CustomButton("RETURN");
        btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Dispose current frame
                Menu menu = new Menu();
                menu.getFrame().setVisible(true); // Show main menu
            }
        });
        btnReturn.setBounds(594, 646, 124, 41);
        frame.getContentPane().add(btnReturn);

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

        frame.setVisible(true);
    }

    private void calculateBalance(double total) {
        try {
            double payment = Double.parseDouble(txtPayment.getText());
            double balance = payment - total;
            txtBalance.setText(String.format("%.2f", balance));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid payment amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handlePayment(JSONArray orders, double total) {
        try {
            URL url = new URL("http://localhost/rbsystem/save_data.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject data = new JSONObject();
            JSONArray mappedOrders = new JSONArray();

            // Map product names to product IDs
            for (int i = 0; i < orders.length(); i++) {
                JSONObject order = orders.getJSONObject(i);
                String productName = order.getString("product_name");
                int productId = mapProductNameToId(productName);
                order.put("product_id", productId);
                mappedOrders.put(order);
            }

            data.put("orders", mappedOrders);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = data.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println("Response from server: " + response.toString());

                JOptionPane.showMessageDialog(frame, "Payment Successful!", "Payment", JOptionPane.INFORMATION_MESSAGE);

                frame.dispose();
                Menu menu = new Menu();
                menu.getFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Payment Failed!", "Payment", JOptionPane.ERROR_MESSAGE);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while processing the payment.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int mapProductNameToId(String productName) {
        switch (productName) {
            case "Burger Ayam (Biasa)":
                return 1;
            case "Burger Ayam (Special)":
                return 2;
            case "Burger Daging (Biasa)":
                return 3;
            case "Burger Daging (Special)":
                return 4;
            case "Burger Kambing (Biasa)":
                return 5;
            case "Burger Kambing (Special)":
                return 6;
            case "Air kosong":
                return 7;
            case "Air bandung":
                return 8;
            case "Air sirap":
                return 9;
            default:
                throw new IllegalArgumentException("Unknown product name: " + productName);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JSONArray orders = new JSONArray();
                    JSONObject order = new JSONObject();
                    order.put("product_name", "Burger"); // Use product name
                    order.put("price", 5.00);
                    order.put("quantity", 2);
                    orders.put(order);

                    double total = 10.00;
                    Billing window = new Billing(orders, total);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
