package rbsys;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class CustomButton extends JButton {

    private Color defaultBackground;
    private Color hoverBackground;
    private Color pressedBackground;

    public CustomButton(String text) {
        super(text);
        setFont(new Font("Franklin Gothic Medium Cond", Font.BOLD, 20));
        defaultBackground = new Color(255, 192, 48);
        hoverBackground = new Color(255, 204, 102);
        pressedBackground = new Color(255, 178, 0);
        
        setBackground(defaultBackground);
        setForeground(Color.BLACK);
        setFocusPainted(false);
        setBorder(new LineBorder(Color.BLACK, 3, true));
        setContentAreaFilled(false);
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackground);
                setBorder(new LineBorder(Color.BLACK, 5, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultBackground);
                setBorder(new LineBorder(Color.BLACK, 5, true));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedBackground);
                setBorder(new LineBorder(Color.BLACK, 5, true));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (contains(e.getPoint())) {
                    setBackground(hoverBackground);
                } else {
                    setBackground(defaultBackground);
                }
                setBorder(new LineBorder(Color.BLACK, 5, true));
            }
        });
    }
}
