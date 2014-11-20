package cs.cpsc838.project2.view;

import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author emmanuj
 */
public class Canvas extends JPanel {
    private int rows;
    private int cols;
    public Canvas(int rows, int cols){
        super(null);
        this.cols = cols;
        this.rows = rows;
        setBorder(new EmptyBorder(10, 10, 10, 10) );
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
    
    /**
     * 
     * @param tiles the tiles to place
     * @param maxPerRow how many tiles to place per row
     */
    public void placeTiles(ArrayList<Tile> tiles, int maxPerRow){
        //TODO: Fix bug! Tile placement location needs to match snap to grid. (Not sure if this is even-q layout)
        int width = TileConstants.CENTER_X * 2;
        System.out.println(width * 3/4);
        double height = (int) Math.ceil((Math.sqrt(3) / 2) * width);
        
        int offset = (int) height / 2;

        int startX = 10;
        int startY = offset + 10;
        int col = 1;
        int row = 1;
        
        for (int i = 0; i < 3; i++) {
            Tile t = tiles.get(i);
            if(col%2 ==0){
                if (startY >= ( maxPerRow * height)) {
                    startY = offset + 10;
                    startX = startX + (int)(3/4.0 * width);
                    col++;
                }
            }else{
                if (startY >= (maxPerRow * height)) {
                    startY = 10;
                    startX = startX + (int)(3/4.0 * width);
                    col++;
                }
            }
            t.setBounds(startX, startY, 105, 105);
            startY += height;
            add(t);
        }
    }
    public void clearTiles(){
       this.invalidate();
       this.removeAll();
       this.validate();
       repaint();
    }
    
}
