package cs.cpsc838.project2.view;

import cs.cpsc838.project2.controller.GameController;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author emmanuj
 */
public class MainWindow extends JFrame {

    ArrayList<Tile> tiles = new ArrayList<>();
    private static final Color r = Color.red;
    private static final Color b = Color.blue;
    private static final Color g = Color.green;
    private static final Color y = Color.yellow;
    private final Canvas canvas;
    private final GameController controller;

    public MainWindow(GameController controller) {
        this.controller = controller;
        
        tiles = controller.createTiles(y, b, r);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setLayout(null);
        setSize(toolkit.getScreenSize().width - 150, toolkit.getScreenSize().width - 200);

        canvas = new Canvas(5,9);
        canvas.setBounds(50, 50, canvas.getCols() * (TileConstants.RADIUS *2), canvas.getRows() * (TileConstants.RADIUS *2));
        canvas.setBackground(Color.white);
        canvas.placeTiles(tiles, 2);
        add(canvas);
    }

    public void showWindow() {
        setTitle("Tantrix Game");
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
