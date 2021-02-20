package chess;
import java.util.ArrayList;
import java.util.Iterator;

public class Player {

private final String color;
//List of this player's non-captured pieces
private ArrayList<Piece> pieces;
//index 0 stores kingside privileges, 1 stores queenside
private boolean [] castlingPrivileges;

	public Player(String color, Piece[][]board) {
		this.color = color;
		pieces = new ArrayList<Piece>();
		//assign player their set of pieces based on their color
		if (color.equals("w")) { 
			//r= rank; f= file
			for (int r = 0; r <2; r++) {
				for (int f = 0; f<board[r].length; f++) {
					if (board[r][f]!=null) pieces.add(board[r][f]);}}}
		if (color.equals("b")) {
			for (int r = 7; r >5; r--) {
				for (int f = 0; f<board[r].length; f++) {
					if (board[r][f]!=null) pieces.add(board[r][f]);}}}
		
		castlingPrivileges = new boolean [2];
}
	
	
	public ArrayList<Move> getMoves(Game g){ 
		ArrayList<Move> moves = new ArrayList<Move>();
		Iterator<Piece> iter = pieces.iterator();
		while(iter.hasNext()) {
			Piece p = iter.next();
			//Piece objects may become null when captured
			if(p==null) { 
				pieces.remove(p);	
			}
			else {
			moves.addAll(iter.next().getMoves(g));
			}
		}
		return moves;
	}
	
	
	public Piece getPiece(String id) {
	Iterator<Piece> iter = pieces.iterator();
	while(iter.hasNext()) {
		if(iter.next().getID().equals(id)) {
			return iter.next();
		}
	}
	return null;	
	}
	
	
public boolean [] getCastlingPrivileges() {
	return castlingPrivileges;
}


public void setCastlingPrivileges(boolean [] privs) {
	castlingPrivileges = privs;
}


public String getColor() {
	return this.color;
}

}



