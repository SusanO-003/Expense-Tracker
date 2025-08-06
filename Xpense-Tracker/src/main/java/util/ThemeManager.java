package util;

import javax.swing.*;
import java.awt.*;

public class ThemeManager {
    private static boolean darkMode = false;

    public static void applyTheme(Component comp, boolean isDark) {
        darkMode = isDark;
        applyTheme(comp);
    }

    public static void applyTheme(Component comp) {
        Color bg = darkMode ? Color.DARK_GRAY : Color.WHITE;
        Color fg = darkMode ? Color.WHITE : Color.BLACK;

        if (comp instanceof JPanel || comp instanceof JFrame || comp instanceof JDialog) {
            comp.setBackground(bg);
        }

        if (comp instanceof JLabel || comp instanceof JButton || comp instanceof JTextField || comp instanceof JTable) {
            comp.setForeground(fg);
            comp.setBackground(bg);

            if (comp instanceof JButton btn) {
                btn.setOpaque(true);
                btn.setContentAreaFilled(true);
                btn.setBorderPainted(false); // optional for flat look
            }
        }

        if (comp instanceof Container container) {
            for (Component child : container.getComponents()) {
                applyTheme(child);
            }
        }

        comp.repaint();
    }

    public static boolean isDarkMode() {
        return darkMode;
    }
}
