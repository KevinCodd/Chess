package chess;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Tile extends Rectangle {
	
private int [] coors;

   

    public Tile(boolean color, int i, int j) {
    	this.coors = new int [2];
    	coors[0]=i; //vertical coordinate to 0
    	coors[1]=j; //horizontal coordinate to 1
        setWidth(BoardControllerMain.TILE_SIZE);
        setHeight(BoardControllerMain.TILE_SIZE);
        //relocate method takes horizontal axis first, then vertical
        relocate(j * BoardControllerMain.TILE_SIZE, i * BoardControllerMain.TILE_SIZE);

        setFill(color ? Color.WHITE : Color.BLACK);
    }
    
    public int[] getCoors() {
    	return this.coors;
    }
}
