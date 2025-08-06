
import view.LoginFrame;

public class Main {
    public static void main(String[] args) {
        // Set UI Look and Feel
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch Login
        new LoginFrame();
    }
}
