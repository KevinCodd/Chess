package chess;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;


/*
 * Original class for when piece was tied directly into GUI - prob convert into wrapper class for Piece
 */
public class PieceDisp extends ImageView {
	
	private final String color; //"b" or "w"
	private final String type; //k,q,b,n,r,p
	private final String id; //color, type, ordinal number from lowest file to highest file on board ex. bp1
	private int[]mouseCoors; // position of mouse dragging piece
	private int [] coors; //position in game of piece (updated when legal move has been executed)

	public PieceDisp(String color, String type, String id, int i, int j) {
		this.color =color;
		this.type = type;
		this.id = id;
		this.mouseCoors = new int [2];
		mouseCoors[0]=i; //vertical coordinate to 0
		mouseCoors[1]=j;	//horizontal coordinate to 1
		this.coors = new int [2];
		coors[0]=i; //vertical coordinate to 0
		coors[1]=j;	//horizontal coordinate to 1
		
		}
	
	public void resetLocation() {
		this.relocate((this.coors[1]*BoardControllerMain.TILE_SIZE), (this.coors[0]*BoardControllerMain.TILE_SIZE));
	}
	
	public int[] getMouseCoors() {
    	return this.mouseCoors;
    }
	
	public void setMouseCoors(int i, int j) {
		mouseCoors[0] = i;
		mouseCoors [1] = j;
	}
	
	public int[] getCoors() {
    	return this.coors;
    }
	
	public void setCoors(int i, int j) {
		coors[0] = i;
		coors [1] = j;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public String getType() {
		return this.type;
	}
	
	public ArrayList<Move> getMoves(Game g){
		ArrayList<Move> moves = new ArrayList<Move>();
		MoveScanner m = new MoveScanner(this, g);
		moves = m.createMoves();
		return moves;
			
	}
	
	public boolean hasBeenMoved(PieceDisp p) {
		//TODO iterate through history and determine if a piece has been moved
		return false;
	}
	
	

}

