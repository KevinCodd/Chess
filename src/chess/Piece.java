package chess;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;


/*
 * This is a class of objects that represent the pieces.  It stores the color, type, coordinates,
 * and individual identity (i.e. light vs. dark square bishop, king's vs. queen's rook etc.) of the piece.
 */
public class Piece {
	//"b" or "w"
	private final String color; 
	//k,q,b,n,r,p
	private String type; 
	//color, type, ordinal number from lowest file to highest file on board ex. bp1
	private String id; 
	//position in game of piece (updated when legal move has been executed)
	private int [] coors; 


	public Piece(String color, String type, String id, int r, int f) {
		this.color =color;
		this.type = type;
		this.id = id;
		this.coors = new int [2];
		//file coordinate to 0
		coors[0]=r; 
		//rank coordinate to 1
		coors[1]=f;	
		}
	
	public int[] getCoors() {
    	return this.coors;
    }
	
	public void setCoors(int r, int f) {
		coors[0] = r;
		coors [1] = f;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type=type;
	}
	
	public String getID() {
		return this.id;
	}
	
	public void setID(String id) {
		this.id=id;
	}
	
	public ArrayList<Move> getMoves(Game g){
		ArrayList<Move> moves = new ArrayList<Move>();
		MoveScanner m = new MoveScanner(this, g);
		moves = m.createMoves();
		return moves;
			
	}
	
	public boolean hasBeenMoved(Piece p) {
		//TODO iterate through history and determine if a piece has been moved
		return false;
	}
	
	

}

