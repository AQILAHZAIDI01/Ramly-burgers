package rbsys;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

public class main {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    main window = new main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public main() {
        initialize();
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
         frame.setTitle("Welcome to Ramly Burger System"); // Set the title of the window
         frame.getContentPane().setLayout(null);

        // Custom JPanel for background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/bgmain.png"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setBounds(0, 0, 1114, 700);
        frame.getContentPane().add(backgroundPanel);
        backgroundPanel.setLayout(null);

        // Custom JLabel with specified color and font
        JLabel lblNewLabel = new JLabel("Welcome to \nOur System");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblNewLabel.setForeground(new Color(255, 190, 43)); // Set color to #FFC030
        lblNewLabel.setBounds(246, 135, 650, 137);
        backgroundPanel.add(lblNewLabel);
        
        JLabel lblRamlyBurger = new JLabel("Ramly Burger");
        lblRamlyBurger.setHorizontalAlignment(SwingConstants.CENTER);
        lblRamlyBurger.setForeground(new Color(255, 190, 43));
        lblRamlyBurger.setFont(new Font("Viner Hand ITC", Font.BOLD, 72));
        lblRamlyBurger.setBounds(318, 205, 522, 137);
        backgroundPanel.add(lblRamlyBurger);


        // Custom buttons
        CustomButton btnCashier = new CustomButton("CASHIER");
        btnCashier.setBounds(440, 352, 240, 46);
        btnCashier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 Menu menuWindow = new Menu();
            	 menuWindow.getFrame().setVisible(true);
                 frame.dispose();  // Close the current frame
                // Define action for CASHIER button
            }
        });
        backgroundPanel.add(btnCashier);

        CustomButton btnInventory = new CustomButton("INVENTORY");
        btnInventory.setBounds(440, 424, 240, 46);
        btnInventory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InventoryGUI inventoryWindow = new InventoryGUI();
                inventoryWindow.getFrame().setVisible(true);
                frame.dispose();  // Close the current frame
            }
        });
        backgroundPanel.add(btnInventory);
        
      
       /* btnInventory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InventoryGUI inventoryWindow = new InventoryGUI();
                inventoryWindow.getFrame().setVisible(true);
                frame.dispose();  // Close the current frame
            }
        });*/
     
    }
}
