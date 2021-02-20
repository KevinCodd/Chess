package chess;
import java.util.ArrayList;
import java.util.Iterator;


/*
 * This class initializes a game and stores all the current game variables - 
 * collectively the current game state.  
 */
public class Game {
	
private Piece[][] boardArr;
private Player white;
private Player black;
private Player curplayer;
//stores all previous game boards
private ArrayList<Piece[][]> stateHistory; 
private ArrayList<Move> moveHistory;
	
public Game() {
Piece wr2 = new Piece("w", "r", "wr2", 0, 0);
Piece wn2 = new Piece("w", "n", "wn2", 0, 1);
Piece wb2 = new Piece("w", "b", "wb2", 0, 2);
Piece wk = new Piece("w", "k", "wk", 0, 3);
Piece wq = new Piece("w", "q", "wq", 0, 4 );
Piece wb1 = new Piece("w", "b", "wb1", 0, 5);
Piece wn1 = new Piece("w", "n", "wn1", 0, 6);
Piece wr1 = new Piece("w", "r", "wr1", 0, 7);
Piece wp8 = new Piece("w", "p", "wp8", 1, 0);
Piece wp7 = new Piece("w", "p", "wp7", 1, 1);
Piece wp6 = new Piece("w", "p", "wp6", 1, 2);
Piece wp5 = new Piece("w", "p", "wp5", 1, 3);
Piece wp4 = new Piece("w", "p", "wp4", 1, 4);
Piece wp3 = new Piece("w", "p", "wp3", 1, 5);
Piece wp2 = new Piece("w", "p", "wp2", 1, 6);
Piece wp1 = new Piece("w", "p", "wp1", 1, 7);

Piece bp8 = new Piece("b", "p", "bp8", 6, 0);
Piece bp7 = new Piece("b", "p", "bp7", 6, 1);
Piece bp6 = new Piece("b", "p", "bp6", 6, 2);
Piece bp5 = new Piece("b", "p", "bp5", 6, 3);
Piece bp4 = new Piece("b", "p", "bp4", 6, 4);
Piece bp3 = new Piece("b", "p", "bp3", 6, 5);
Piece bp2 = new Piece("b", "p", "bp2", 6, 6);
Piece bp1 = new Piece("b", "p", "bp1", 6, 7);
Piece br2 = new Piece("b", "r", "br2", 7, 0);
Piece bn2 = new Piece("b", "n", "bn2", 7, 1);
Piece bb2 = new Piece("b", "b", "bb2", 7, 2);
Piece bk = new Piece("b", "k", "bk", 7, 3);
Piece bq = new Piece("b", "q", "bq", 7, 4);
Piece bb1 = new Piece("b", "b", "bb1", 7, 5);
Piece bn1 = new Piece("b", "n", "bn1", 7, 6);
Piece br1 = new Piece("b", "r", "br1", 7, 7);


	boardArr = new Piece [][] {{wr2,wn2,wb2,wk,wq,wb1,wn1,wr1},{wp8,wp7,wp6,wp5,wp4,wp3,wp2,wp1},
		{null,null,null,null,null,null,null,null},{null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null},{null,null,null,null,null,null,null,null},
		{bp8,bp7,bp6,bp5,bp4,bp3,bp2,bp1},{br2,bn2,bb2,bk,bq,bb1,bn1,br1}};
	white = new Player("w", boardArr);
	black = new Player("b", boardArr);
	curplayer = white;
	
	moveHistory = new ArrayList<Move>();
	stateHistory = new ArrayList<Piece[][]>();
	boolean [] privs = {true, true};
	white.setCastlingPrivileges(privs);
	black.setCastlingPrivileges(privs);
}


public void togglePlayer() {
	if (curplayer.equals(this.white)) {
		curplayer = this.black;
	}
	else {
		curplayer = this.white;
	}
}


public ArrayList<Piece[][]> getStateHistory() {
	return stateHistory;
}


public ArrayList<Move> getMoveHistory() {	
	return moveHistory;
}


public Piece [][] getBoard() {
	//No one but Game and its methods can edit the master board
	Piece [][] copy = boardArr.clone();
	return copy;
}


public Player getPlayer(String color) {
	if(color.equals("w")) {
	return white;
}
	else if (color.equals("b")) {
		return black;
	}
	else {
		return null;
	}
}

public Player getCurPlayer() {
	return curplayer;
}

/*
 * returns permanent castling privileges i.e. only altered by player moving king or rook;
 * not affected by pieces in the way, castling path under attack, etc.
 * [0] = Kingside 
 * [1] = Queenside 
 * Can castle if true
 */
public boolean [] getCastlingPrivileges(String color) {
	boolean [] castlingPrivileges = getPlayer(color).getCastlingPrivileges();
	
	if(castlingPrivileges[0]==false&&castlingPrivileges[1]==false) {
		return castlingPrivileges;
	}
	
	if (color.equals("w")) {
		//if king or rook is not in original location return false
		if(!(boardArr[0][3]!= null && boardArr[0][3].getType().equals("k")&&boardArr[0][0]!= null && boardArr[0][0].getType().equals("r"))) {
			castlingPrivileges[0]=false;
		}
		if(!(boardArr[0][3]!= null && boardArr[0][3].getType().equals("k")&&boardArr[0][7]!= null && boardArr[0][7].getType().equals("r"))) {
			castlingPrivileges[1]=false;
		}
		if (castlingPrivileges[0]==false && castlingPrivileges[1]==false) return castlingPrivileges;
		
		//exhaustively scan move history to see if king or rook has been moved
		Iterator<Move> iter = moveHistory.iterator();
		while(iter.hasNext()) {
			Move m = iter.next();
			if(m.getPiece().getColor().equals("w")&&m.getPiece().getType().equals("k")) { 
				castlingPrivileges[0]=false;
				castlingPrivileges[1]=false;
				return castlingPrivileges;
			}
		}
		while(iter.hasNext()) {
			Move m = iter.next();
			if(m.getPiece().getColor().equals("w")&&m.getPiece().getType().equals("r")) {
				if(m.getPiece().getID().equals("wr2")) {
					castlingPrivileges[0]=false;
				}
				else {
					castlingPrivileges[1]=false;
				}
			}
	}
}
	
	if (color.equals("b")) {
		if(!(boardArr[7][3]!= null && boardArr[7][3].getType().equals("k")&&boardArr[7][0]!= null && boardArr[7][0].getType().equals("r"))) {
			castlingPrivileges[0]=false;
		}
		if(!(boardArr[7][3]!= null && boardArr[7][3].getType().equals("k")&&boardArr[7][7]!= null && boardArr[7][7].getType().equals("r"))) {
			castlingPrivileges[1]=false;
		}
		
		if (castlingPrivileges[0]==false && castlingPrivileges[1]==false) return castlingPrivileges;
		
		Iterator<Move> iter = moveHistory.iterator();
		while(iter.hasNext()) {
			Move m = iter.next();
			if(m.getPiece().getColor().equals("b")&&m.getPiece().getType().equals("k")) { 
				castlingPrivileges[0]=false;
				castlingPrivileges[1]=false;
				return castlingPrivileges;
			}
		}
		while(iter.hasNext()) {
			Move m = iter.next();
			if(m.getPiece().getColor().equals("b")&&m.getPiece().getType().equals("r")) {
				if(m.getPiece().getID().equals("br2")) {
					castlingPrivileges[0]=false;
				}
				else {
					castlingPrivileges[1]=false;
				}
			}
	}
}
	getPlayer(color).setCastlingPrivileges(castlingPrivileges);
	return castlingPrivileges;
}


//Executes a move and updates the board, variables
public void executeMove(Move m) {
	int rf = m.getFcoors()[0];
	int ff = m.getFcoors()[1];
	int ri = m.getIcoors()[0];
	int fi = m.getIcoors()[1];
	String color = m.getPiece().getColor();
	String ID = m.getPiece().getID();
	String type = m .getPiece().getType();
	
	//kingside castle
	if (type.equals("k")&&(ff-fi==-2)) {
		//set coordinates for both board and piece(s), nullify old locations
		boardArr[rf][ff] = m.getPiece();
		boardArr[rf][ff].setCoors(rf, ff);
		boardArr[rf][ff+1] = boardArr[rf][0];
		boardArr[rf][ff+1].setCoors(rf, ff+1);
		boardArr[ri][fi]=null;
		boardArr[rf][0]=null;
		getPlayer(color).setCastlingPrivileges(new boolean [] {false, false});
		togglePlayer();
		return;
	}
	//queenside castle
	else if (type.equals("k")&&(ff-fi==2)) {
		boardArr[rf][ff] = m.getPiece();
		boardArr[rf][ff].setCoors(rf, ff);
		boardArr[rf][ff-1] = boardArr[rf][7];
		boardArr[rf][ff-1].setCoors(rf, ff-1); 
		boardArr[ri][fi]=null;
		boardArr[rf][7]=null;
		getPlayer(color).setCastlingPrivileges(new boolean [] {false, false});
		togglePlayer();
		return;
	}
	//pawn promotion
	else if (m.getPiece().getType().equals("p") &&(rf==8)) {
		boardArr[rf][ff]=null;
		boardArr[rf][ff] = m.getPiece();
		boardArr[ri][fi] = null;
		m.getPiece().setCoors(rf, ff);
		//switches type and ID to reflect queen status
		m.getPiece().setType("q");
		String newid = ID.substring(0,1)+"q"+ID.substring(2,3);
		m.getPiece().setID(newid);
		togglePlayer();
		return;
	}
	//general case
	boardArr[rf][ff]=null;
	boardArr[rf][ff] = m.getPiece();
	boardArr[ri][fi] = null;
	m.getPiece().setCoors(rf, ff);
	togglePlayer();
	
}

//checks if a submitted move is valid
public boolean tryMove(Move m) {
	ArrayList<Move> moves = curplayer.getMoves(this);
	Iterator<Move> iter = moves.iterator();
	
	while(iter.hasNext()) {
		if (iter.next().equals(m)) return true;
	}
	return false;	
}

/*
 * 0 - no end condition met
 * 1 - checkmate
 * 2 - stalemate
 * 3 - TODO insufficient material
 * 4 - TODO 3-move repetition of position
 * 5 - TODO 50-move rule
 */
public int endChecker() {
ArrayList<Move> moves = curplayer.getMoves(this);
MoveScanner scanner = new MoveScanner(curplayer.getPiece(curplayer.getColor()+"k"),this);
//we don't want MoveScanner to edit the master copy of the board
Piece [][] copy = boardArr.clone();
if (moves.isEmpty()&&scanner.inCheck(copy)) {
	System.out.println("checkmate");
	return 1;
}
else if(moves.isEmpty()&&!scanner.inCheck(copy)) {
	System.out.println("stalemate");
	return 2;
}
else {
	return 0;
}
}




}

