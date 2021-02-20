package chess;
import java.util.ArrayList;
import java.util.Iterator;


/*
 * This class receives a Piece and a Game object and houses methods that
 * scan for and return the piece's legal moves in the current game state
 */
public class MoveScanner {
	
private Piece p;
private Game g;
private Piece [][] board;
//initial rank index
private int ri; 
//initial file index
private int fi; 
	

	public MoveScanner(Piece p, Game g) {
		this.p = p;
		this.g = g;
		board = g.getBoard();
		this.ri = p.getCoors()[0];
		this.fi = p.getCoors()[1];
	}
	
	
	public ArrayList<Move> createMoves(){
		ArrayList<Move> moves = new ArrayList<Move>();
		switch (p.getType()) {
		case "k": moves = this.getKingMoves(); break;
		case "q": moves = this.getQueenMoves(); break;
		case "b": moves = this.getBishopMoves(); break;
		case "n": moves = this.getKnightMoves(); break;
		case "r": moves = this.getRookMoves(); break;
		case "p": moves = this.getPawnMoves(); break;
		}
		Iterator<Move> iter = moves.iterator();
		while(iter.hasNext()) {
			Move m = iter.next();
			if(inCheck(makeTestMove(m))) moves.remove(m);
		}
		return moves;
	}
	
	
	//returns valid king moves not accounting for checks
	public ArrayList<Move> getKingMoves(){ 
	ArrayList<Move> moves = new ArrayList<Move>();
	moves.add(new Move(board[ri][fi], ri+1, fi));
	moves.add(new Move(board[ri][fi], ri-1, fi));
	moves.add(new Move(board[ri][fi], ri, fi+1));
	moves.add(new Move(board[ri][fi], ri, fi-1));
	moves.add(new Move(board[ri][fi], ri+1, fi+1));
	moves.add(new Move(board[ri][fi], ri-1, fi-1));
	moves.add(new Move(board[ri][fi], ri+1, fi-1));
	moves.add(new Move(board[ri][fi], ri-1, fi+1));
	
	//prunes moves that are out of bounds or on own pieces
	Iterator<Move> iter = moves.iterator();
	//list of moves to be removed from moves list
		ArrayList<Move> remove = new ArrayList<Move>();
		
	while(iter.hasNext()) {
		Move m = iter.next();
		if(m.getFcoors()[0]<0 || m.getFcoors()[0]>7 || m.getFcoors()[1]<0 || m.getFcoors()[1]>7) {
			remove.add(m);}
		else if(this.board[m.getFcoors()[0]][m.getFcoors()[1]].getColor().equals(m.getPiece().getColor())) {
			remove.add(m);
		}
	}
	
	Iterator<Move> removeiter = remove.iterator();
	while(removeiter.hasNext()) {
		moves.remove(removeiter.next());
	}
	
	//castling moves
 if (canCastle()[0]) moves.add(new Move(board[ri][fi], ri, fi-2));
 if (canCastle()[1]) moves.add(new Move(board[ri][fi], ri, fi+2));
		return moves;
	}
	
	
	//returns valid Queen moves not accounting for checks
	public ArrayList<Move> getQueenMoves(){
	ArrayList<Move> moves = new ArrayList<Move>();
	moves.addAll(scanHorizVert());
	moves.addAll(scanDiag());
	return moves;
	}

	
	//returns valid Bishop moves not accounting for checks
	public ArrayList<Move> getBishopMoves(){
	return this.scanDiag();
}
	
	
	//returns valid Knight moves not accounting for checks
	public ArrayList<Move> getKnightMoves(){ 
	ArrayList<Move> moves = new ArrayList<Move>();
	System.out.println(ri);
	System.out.println(fi);
	moves.add(new Move(board[ri][fi], ri+2, fi-1));
	moves.add(new Move(board[ri][fi], ri+2, fi+1));
	moves.add(new Move(board[ri][fi], ri+1, fi-2));
	moves.add(new Move(board[ri][fi], ri+1, fi+2));
	moves.add(new Move(board[ri][fi], ri-2, fi-1));
	moves.add(new Move(board[ri][fi], ri-2, fi+1));
	moves.add(new Move(board[ri][fi], ri-1, fi-2));
	moves.add(new Move(board[ri][fi], ri-1, fi+2));
	
	//prunes moves that are out of bounds or on own pieces
	Iterator<Move> iter = moves.iterator();
	//list of moves to be removed from moves list
	ArrayList<Move> remove = new ArrayList<Move>();
	
	while(iter.hasNext()) {
		Move m = iter.next();
		if(m.getFcoors()[0]<0 || m.getFcoors()[0]>7 || m.getFcoors()[1]<0 || m.getFcoors()[1]>7) {
			remove.add(m);
			}
		else if(board[m.getFcoors()[0]][m.getFcoors()[1]]!=null&&board[m.getFcoors()[0]][m.getFcoors()[1]].getColor().equals(m.getPiece().getColor())) {
			remove.add(m);
		}
	}
	
	Iterator<Move> removeiter = remove.iterator();
	while(removeiter.hasNext()) {
		moves.remove(removeiter.next());
	}
	
	return moves;
}
	
	
	//returns valid Rook moves not accounting for checks
	public ArrayList<Move> getRookMoves(){
	return this.scanHorizVert();
}
	
	
	//returns valid Pawn moves not accounting for checks
	public ArrayList<Move> getPawnMoves(){
		ArrayList<Move> moves = new ArrayList<Move>(); 
		if (this.p.getColor().equals("w")){ 
			//regular push
			if(board[ri+1][fi]==null &&ri!=7) {
				moves.add(new Move(board[ri][fi], ri+1, fi));} 
			//diagonal attacks
			if(fi!=7) {
			if(board[ri+1][fi+1]!=null&&board[ri+1][fi+1].getColor().equals("b")) {
				moves.add(new Move(board[ri][fi], ri+1, fi+1));}}
			if(fi!=0) {
			if(board[ri+1][fi-1]!=null&&board[ri+1][fi-1].getColor().equals("b")) {
				moves.add(new Move(board[ri][fi], ri+1, fi-1));}}
			//2-space initial push
			if(ri==1&&board[ri+1][fi]==null&&board[ri+2][fi]==null) { 
				moves.add(new Move(board[ri][fi], ri+2, fi));}
			
			//en passant
			if(!g.getMoveHistory().isEmpty()) { 
				Move last = g.getMoveHistory().get(g.getMoveHistory().size()-1);
			
			if (last.getPiece().getType().equals("p")) {
				if (last.getFcoors()[0]==4) {
					if(last.getFcoors()[1]!=7) {
					if (p.getCoors()[0]==4&&p.getCoors()[1]==last.getFcoors()[1]+1) {
						moves.add(new Move(board[ri][fi], ri+1, fi-1));
					}}
					if(last.getFcoors()[1]!=0) {
					if (p.getCoors()[0]==4&&p.getCoors()[1]==last.getFcoors()[1]-1) {
						moves.add(new Move(board[ri][fi], ri+1, fi+1));
					}}
				}
			}
			}
		}
		if (this.p.getColor().equals("b")){ 
			if(board[ri-1][fi]==null&&ri!= 0) {
				moves.add(new Move(board[ri][fi], ri-1, fi));} 
			if(fi!=7) {
			if(board[ri-1][fi+1]!=null&&board[ri-1][fi+1].getColor().equals("w")) {
				moves.add(new Move(board[ri][fi], ri-1, fi+1));}} 
			if(fi!=0) {
			if(board[ri-1][fi-1]!=null&&board[ri-1][fi-1].getColor().equals("w")) {
				moves.add(new Move(board[ri][fi], ri-1, fi-1));}}	
			if(ri==6&&board[ri-1][fi]==null&&board[ri-2][fi]==null) { 
				moves.add(new Move(board[ri][fi], ri-2, fi));}
			
			if(!g.getMoveHistory().isEmpty()) {
			Move last = g.getMoveHistory().get(g.getMoveHistory().size()-1);
			if (last.getPiece().getType().equals("p")) {
				if (last.getFcoors()[0]==3) {
					if(last.getFcoors()[1]!=7) {
					if (p.getCoors()[0]==3&&p.getCoors()[1]==last.getFcoors()[1]+1) {
						moves.add(new Move(board[ri][fi], ri+1, fi-1));
					}}
					if(last.getFcoors()[1]!=0) {
					if (p.getCoors()[0]==3&&p.getCoors()[1]==last.getFcoors()[1]-1) {
						moves.add(new Move(board[ri][fi], ri+1, fi+1));
					}}
				}
			}
			}
		}
		return moves;
	
}
	
	
	//Checks if any enemy pieces are attacking the king
	public boolean inCheck(Piece[][]boardArr) {
		//get the coordinates of the king of the Piece of this MoveScanner object
		//king rank
		int kr = g.getPlayer(p.getColor()).getPiece(p.getColor()+"k").getCoors()[0];
		//king file
		int kf = g.getPlayer(p.getColor()).getPiece(p.getColor()+"k").getCoors()[1];
		
		return underAttack(kr, kf, boardArr);
	}
	
	
	/*
	 * Scans the board horizontally and vertically for moves starting from the piece's current coordinates
	 * Scan directions are named relative to the array coordinates of the board in the game object i.e progressing
	 * from row/rank index 0 to 7 is down, 7 to 0 is up; column/file index 0-7 is right, 7-0 is left.
	 * only first block is commented b/c logic is same in all.
	 */
	public ArrayList<Move> scanHorizVert(){
		ArrayList<Move> moves = new ArrayList<Move>();
		//downward scan
		if (ri!=7) {	
		for (int r = ri+1; r<board.length; r++) { 
			//add empty square to move list
			if (board[r][fi]== null) {
				moves.add(new Move(board[ri][fi], r, fi));
			}
			else if (board[r][fi]!=null) {
				//if opposite color piece occupies this square, add move and end scan
				if (!board[r][fi].getColor().equals(p.getColor())) {
					moves.add(new Move(board[ri][fi], r, fi));
					break;
					}
				//if same color piece occupies this square, end scan
				else if(board[r][fi].getColor().equals(p.getColor())) {
					break;
				}}}}
		//upward scan
		if(ri!=0) {
		for (int r = ri-1; r>-1; r--) { 
			if (board[r][fi]== null) {
				moves.add(new Move(board[ri][fi], r, fi));
			}
			else if (board[r][fi]!=null) {
				if (!board[r][fi].getColor().equals(p.getColor())) {
					moves.add(new Move(board[ri][fi], r, fi));
					break;
					}
				else if(board[r][fi].getColor().equals(p.getColor())) {
					break;
				}}}}
		//rightward scan
		if(fi!=7) {
		for (int f = fi+1; f<board.length; f++) { 
			if (board[ri][f]== null) {
				moves.add(new Move(board[ri][fi], ri, f));
			}
			else if (board[ri][f]!=null) {
				if (!board[ri][f].getColor().equals(p.getColor())) {
					moves.add(new Move(board[ri][fi], ri, f));
					break;
					}
				else if(board[ri][f].getColor().equals(p.getColor())) {
					break;
				}}}}
		//leftward scan
		if(fi!=0) {
		for (int f = fi-1; f>-1; f--) { 
			if (board[ri][f]== null) {
				moves.add(new Move(board[ri][fi], ri, f));
			}
			else if (board[ri][f]!=null) {
				if (!board[ri][f].getColor().equals(p.getColor())) {
					moves.add(new Move(board[ri][fi], ri, f));
				break;
				}
				else if(board[ri][f].getColor().equals(p.getColor())) {
					break;
				}}}}
		return moves; 	
	}
	
	
	/*
	 * Scans diagonally for moves starting from the piece's current coordinates
	 * Scan directions are named relative to the array coordinates of the board in the game object
	 * i.e. east-west = 0-7, north-south = 0-7.
	 * Only first block is commented because logic is same in all
	 */
	public ArrayList<Move> scanDiag() { 
		ArrayList<Move> moves = new ArrayList<Move>();
		//south-east scan
		if(ri!=7&&fi!=7) {
		//add empty squares to move list
		for(int r=ri+1, f= fi+1; r<board.length && f<board.length; r++, f++){ 
			if (board[r][f]== null) {
				moves.add(new Move(board[ri][fi], r, f));
			}
			else if (board[r][f]!=null) {
				//if opposite color piece occupies this square, add move and end scan
				if (!board[r][f].getColor().equals(p.getColor())) {
					moves.add(new Move(board[ri][fi], r, f));
					break;
					}
				//if same color piece occupies this square, end scan
				else if(board[r][f].getColor().equals(p.getColor())) {
					break;}}}}
		//north-west scan
		if(ri!=0&&fi!=0) {
		for(int r=ri-1, f= fi-1; r>-1 && f>-1; r--, f--){ 
			if (board[r][f]== null) {
				moves.add(new Move(board[ri][fi], r, f));
			}
			else if (board[r][f]!=null) {
				if (!board[r][f].getColor().equals(p.getColor())) {
					moves.add(new Move(board[ri][fi], r, f));}
				else if(board[r][f].getColor().equals(p.getColor())) {
					break;}}}}
		//south-west scan
		if(ri!=7&&fi!=0) {
		for(int r=ri+1, f= fi-1; r<board.length && f>-1; r++, f--){ 
			if (board[r][f]== null) {
				moves.add(new Move(board[ri][fi], r, f));
			}
			else if (board[r][f]!=null) {
				if (!board[r][f].getColor().equals(p.getColor())) {
					moves.add(new Move(board[ri][fi], r, f));}
				else if(board[r][f].getColor().equals(p.getColor())) {
					break;}}}}
		//north-east scan
		if(ri!=0&&fi!=7) {
		for(int r=ri-1, f= fi+1; r>-1 && f<board.length; r--, f++){ 
			if (board[r][f]== null) {
				moves.add(new Move(board[ri][fi], r, f));
			}
			else if (board[r][f]!=null) {
				if (!board[r][f].getColor().equals(p.getColor())) {
					moves.add(new Move(board[ri][fi], r, f));}
				else if(board[r][f].getColor().equals(p.getColor())) {
					break;}}}}
		return moves;
	}
	
	
	/*
	 * receives coordinates of a square and returns true
	 * if that square is under attack by opposite color
	 * Uses passed in board (manipulated by makeTestMove) rather than 
	 * main game board to accommodate testing hypothetical moves for check
	 * without altering main game board
	 * 
	 */
	public boolean underAttack(int sr, int sf, Piece [][] boardArr) {
		
		if (sr!=7) {	
			for (int r = sr+1; r<boardArr.length; r++) { 
					if (boardArr[r][sf]!=null) {
					//if opposite color piece occupies this square, return true
						if (!boardArr[r][sf].getColor().equals(p.getColor())) {
							if(boardArr[r][sf].getType().equals("r")|| boardArr[r][sf].getType().equals("q")) {
							return true;
							}
							else {
								break;
							}}
						//if same color piece occupies this square, end scan
						else if(boardArr[r][sf].getColor().equals(p.getColor())) {
							break;
						}}}}
			//upward scan
			if(sr!=0) {
			for (int r = sr-1; r>-1; r--) { 
					if (boardArr[r][sf]!=null) {
						if (!boardArr[r][sf].getColor().equals(p.getColor())) {
							if(boardArr[r][sf].getType().equals("r")|| boardArr[r][sf].getType().equals("q")) {
							return true;
							}
							else {
								break;
							}}
					else if(boardArr[r][sf].getColor().equals(p.getColor())) {
						break;
					}}}}
			//rightward scan
			if(sf!=7) {
			for (int f = sf+1; f<boardArr.length; f++) { 
				if (boardArr[sr][f]!=null) {
					if (!boardArr[sr][f].getColor().equals(p.getColor())) {
						if(boardArr[sr][f].getType().equals("r")|| boardArr[sr][f].getType().equals("q")) {
						return true;
						}
						else {
							break;
						}}
				else if(boardArr[sr][f].getColor().equals(p.getColor())) {
					break;
				}}}}
			//leftward scan
			if(sf!=0) {
			for (int f = sf-1; f>-1; f--) { 
				if (boardArr[sr][f]!=null) {
					if (!boardArr[sr][f].getColor().equals(p.getColor())) {
						if(boardArr[sr][f].getType().equals("r")|| boardArr[sr][f].getType().equals("q")) {
						return true;
						}
						else {
							break;
						}}
				else if(boardArr[sr][f].getColor().equals(p.getColor())) {
					break;
				}}}}
			
			/******************diagonal scan***********************/
			//south-east scan
			if(sr!=7&&sf!=7) {
			//add empty squares to move list
			for(int r=sr+1, f= sf+1; r<boardArr.length && f<boardArr.length; r++, f++){ 
				if (boardArr[r][f]!=null) {
					//if opposite color piece occupies this square, return true
					if (!boardArr[r][f].getColor().equals(p.getColor())) {
						if(boardArr[r][f].getType().equals("b")|| boardArr[r][f].getType().equals("q")) {
						return true;
						}}
					//if same color piece occupies this square, end scan
					else if(boardArr[r][f].getColor().equals(p.getColor())) {
						break;}}}}
			//north-west scan
			if(sr!=0&&sf!=0) {
			for(int r=sr-1, f= sf-1; r>-1 && f>-1; r--, f--){ 
				if (boardArr[r][f]!=null) {
					if (!boardArr[r][f].getColor().equals(p.getColor())) {
						if(boardArr[r][f].getType().equals("b")|| boardArr[r][f].getType().equals("q")) {
							return true;
							}}
					else if(boardArr[r][f].getColor().equals(p.getColor())) {
						break;}}}}
			//south-west scan
			if(sr!=7&&sf!=0) {
			for(int r=sr+1, f= sf-1; r<boardArr.length && f>-1; r++, f--){ 
				if (boardArr[r][f]!=null) {
					if (!boardArr[r][f].getColor().equals(p.getColor())) {
						if(boardArr[r][f].getType().equals("b")|| boardArr[r][f].getType().equals("q")) {
							return true;
							}}
					else if(boardArr[r][f].getColor().equals(p.getColor())) {
						break;}}}}
			//north-east scan
			if(sr!=0&&sf!=7) {
			for(int r=sr-1, f= sf+1; r>-1 && f<boardArr.length; r--, f++){ 
				if (boardArr[r][f]!=null) {
					if (!boardArr[r][f].getColor().equals(p.getColor())) {
						if(boardArr[r][f].getType().equals("b")|| boardArr[r][f].getType().equals("q")) {
							return true;
							}}
					else if(boardArr[r][f].getColor().equals(p.getColor())) {
						break;}}}}

	/*************************knight scan***************************/
			//list of knight moves from king, borrowed from knight move scanner
			ArrayList<Move> moves = new ArrayList<Move>(); 
			moves.add(new Move(boardArr[sr][sf], sr+2, sf-1));
			moves.add(new Move(boardArr[sr][sf], sr+2, sf+1));
			moves.add(new Move(boardArr[sr][sf], sr+1, sf-2));
			moves.add(new Move(boardArr[sr][sf], sr+1, sf+2));
			moves.add(new Move(boardArr[sr][sf], sr-2, sf-1));
			moves.add(new Move(boardArr[sr][sf], sr-2, sf+1));
			moves.add(new Move(boardArr[sr][sf], sr-1, sf-2));
			moves.add(new Move(boardArr[sr][sf], sr-1, sf+2));
			//prunes moves that are from out of bounds
			Iterator<Move> moveiter = moves.iterator();
			//list of moves to be removed from moves list
			ArrayList<Move> remove = new ArrayList<Move>();
			
			while(moveiter.hasNext()) {
				Move m = moveiter.next();
				if(m.getFcoors()[0]<0 || m.getFcoors()[0]>7 || m.getFcoors()[1]<0 || m.getFcoors()[1]>7) {
					remove.add(m);}}
			
			Iterator<Move> removeiter = remove.iterator();
			while(removeiter.hasNext()) {
				moves.remove(removeiter.next());
			}
				//iterates through final list of valid knight moves
			Iterator<Move>moveiter2 = moves.iterator();
				while(moveiter2.hasNext()) {
				Move m = moveiter2.next();
				if(boardArr[m.getFcoors()[0]][m.getFcoors()[1]]!=null&&boardArr[m.getFcoors()[0]][m.getFcoors()[1]].getType().equals("n")) {
					return true;
			}}
			
	/**********************pawn scan*******************************/
			if (p.getColor().equals("w")) {
				if(sr!=7) {
					if(sf!=7) {
					if(boardArr[sr+1][sf+1]!=null&&boardArr[sr+1][sf+1].getColor().equals("b")&&boardArr[sr+1][sf+1].getType().equals("p")) {
					return true;
					}}
					if(sf!=0) {
					if(boardArr[sr+1][sf-1]!=null&&boardArr[sr+1][sf-1].getColor().equals("b")&&boardArr[sr+1][sf-1].getType().equals("p")) {
					return true;
					}}
				}}
			
			if (p.getColor().equals("b")) {
				if(sr!=0) {
					if(sf!=7) {
					if(boardArr[sr+1][sf+1]!=null&&boardArr[sr+1][sf+1].getColor().equals("w")&&boardArr[sr-1][sf+1].getType().equals("p")) {
					return true;
					}}
					if(sf!=0) {
					if(boardArr[sr+1][sf-1]!=null&&boardArr[sr+1][sf-1].getColor().equals("w")&&boardArr[sr-1][sf-1].getType().equals("p")) {
					return true;
					}}
				}}	
			
			
	/********************king scan********************************/
			ArrayList<Move> kmoves = new ArrayList<Move>();
			kmoves.add(new Move(boardArr[sr][sf], sr+1, sf));
			kmoves.add(new Move(boardArr[sr][sf], sr-1, sf));
			kmoves.add(new Move(boardArr[sr][sf], sr, sf+1));
			kmoves.add(new Move(boardArr[sr][sf], sr, sf-1));
			kmoves.add(new Move(boardArr[sr][sf], sr+1, sf+1));
			kmoves.add(new Move(boardArr[sr][sf], sr-1, sf-1));
			kmoves.add(new Move(boardArr[sr][sf], sr+1, sf-1));
			kmoves.add(new Move(boardArr[sr][sf], sr-1, sf+1));
			
			//prunes moves that are from out of bounds
			Iterator<Move> kiter = moves.iterator();
			//list of moves to be removed from moves list
			ArrayList<Move> remove1 = new ArrayList<Move>();
			
			while(kiter.hasNext()) {
				Move m = kiter.next();
				if(m.getFcoors()[0]<0 || m.getFcoors()[0]>7 || m.getFcoors()[1]<0 || m.getFcoors()[1]>7) {
					remove1.add(m);}}
			
			Iterator<Move> remove1iter = remove.iterator();
			while(remove1iter.hasNext()) {
				moves.remove(remove1iter.next());
			}
			//iterates through final list of valid king moves
			Iterator<Move> kiter1 = moves.iterator();
			while(kiter1.hasNext()) {
				Move m = kiter1.next();
				if(boardArr[m.getFcoors()[0]][m.getFcoors()[1]]!=null&&boardArr[m.getFcoors()[0]][m.getFcoors()[1]].getType().equals("k")) {
					return true;
				}}		
	return false;	
	}

	
/*
 * returns array of length 2
 * index 0 indicates whether player can castle kingside,
 * index 1 queenside	
 */
public boolean [] canCastle() {
	boolean [] privs = new boolean [2];
	privs[0]=false;
	privs[1]=false;
	if (g.getCastlingPrivileges(p.getColor())[0] == false && g.getCastlingPrivileges(p.getColor())[1] == false) {
		return privs;
	}
	else if(g.getCastlingPrivileges(p.getColor())[0] == true) { 
		int kr = g.getPlayer(p.getColor()).getPiece(p.getColor()+"k").getCoors()[0];
		if(board[kr][1]==null&&board[kr][2]==null&&!underAttack(kr, 1, board)&&!underAttack(kr,2, board)) {
			privs[0] = true;
		}
	}
	else if(g.getCastlingPrivileges(p.getColor())[1] == true) { 
		int kr = g.getPlayer(p.getColor()).getPiece(p.getColor()+"k").getCoors()[0];
		if(board[kr][4]==null&&board[kr][5]==null&&board[kr][6]==null&&!underAttack(kr, 4, board)&&
				!underAttack(kr,5, board)&&!underAttack(kr, 6, board)) {
			
			privs[1] = true;
		}
	}
	
	
	return privs;
}
	
/*
 * Returns copy of game board with move made
 * Don't have to update all piece/player data in here b/c state is not used after this
 */
public Piece [][] makeTestMove(Move m) {
	Piece [][] boardArr = board.clone();
	int rf = m.getFcoors()[0];
	int ff = m.getFcoors()[1];
	int ri = m.getIcoors()[0];
	int fi = m.getIcoors()[1];
	
	if (m.getPiece().getType().equals("k")&&(ff-fi==-2)) {
		boardArr[rf][ff] = m.getPiece();
		boardArr[rf][ff+1] = boardArr[rf][0];
		boardArr[ri][fi]=null;
		boardArr[rf][0]=null;
		return boardArr;
	}
	else if (m.getPiece().getType().equals("k")&&(ff-fi==2)) {
		boardArr[rf][ff] = m.getPiece();
		boardArr[rf][ff-1] = boardArr[rf][7];
		boardArr[ri][fi]=null;
		boardArr[rf][7]=null;
		return boardArr;
	}
	boardArr[rf][ff] = m.getPiece();
	boardArr[ri][fi]=null;
	
	return boardArr;
}

}


