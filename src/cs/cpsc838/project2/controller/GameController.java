package cs.cpsc838.project2.controller;

import cs.cpsc838.project2.view.Tile;
import cs.cpsc838.project2.view.TilePattern;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author emmanuj
 */
public class GameController {
    private ArrayList<Tile> tiles;
    public GameController(){
        
    }
    public  ArrayList<Tile> createTiles(Color c1,Color c2, Color c3){
        tiles = new ArrayList();
        
        tiles.add(new Tile("1", TilePattern.CORNER_TWO_BENDS, c1,c2,c3));
        tiles.add(new Tile("2", TilePattern.STRAIGHT_TWO_CORNERS, c1,c2,c3));
        tiles.add(new Tile("3", TilePattern.ALL_CORNERS, c3,c2,c1));
        tiles.add(new Tile("4", TilePattern.STRAIGHTS_TWO_BENDS, c1,c2,c3));
        tiles.add(new Tile("5", TilePattern.STRAIGHT_TWO_CORNERS, c1,c3,c2));
        tiles.add(new Tile("6", TilePattern.STRAIGHTS_TWO_BENDS, c3,c1,c2));
        tiles.add(new Tile("7", TilePattern.CORNER_TWO_BENDS, c2,c1,c3));
        tiles.add(new Tile("8", TilePattern.CORNER_TWO_BENDS, c2,c3,c1));
        tiles.add(new Tile("9", TilePattern.STRAIGHTS_TWO_BENDS, c1,c3,c2));
        tiles.add(new Tile("10", TilePattern.CORNER_TWO_BENDS, c1,c3,c2));
        tiles.add(new Tile("11", TilePattern.CORNER_TWO_BENDS, c3,c2,c1));
        tiles.add(new Tile("12", TilePattern.CORNER_TWO_BENDS, c3,c1,c2));
        tiles.add(new Tile("13", TilePattern.STRAIGHT_TWO_CORNERS, c3,c1,c2));
        tiles.add(new Tile("14", TilePattern.ALL_CORNERS, c1,c2,c3));
        
        return tiles;
    }
}
