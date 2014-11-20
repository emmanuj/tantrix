package cs.cpsc838.project2;

import cs.cpsc838.project2.controller.GameController;
import javax.swing.SwingUtilities;
import cs.cpsc838.project2.view.MainWindow;
/**
 *
 * @author emmanuj
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final GameController controller = new GameController();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mainWindow = new MainWindow(controller);
                mainWindow.showWindow();
            }
        });
    }
    
}
