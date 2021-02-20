package chess;

public class Move {
	
	//initial coordinates
	private int [] icoors;
	//final rank
	private int rf; 
	//final file
	private int ff;
	private Piece piece;
	
	public Move (Piece piece, int rf, int ff) {
		this.piece = piece;
		this.icoors = piece.getCoors();
		this.rf = rf;
		this.ff = ff;
		
	}
	
	public int[] getFcoors() {
		int[]fcoors= new int [2];
		fcoors[0]=rf;
		fcoors[1]=ff;
		return fcoors;
	}
	
	public int[] getIcoors() {
		return icoors;
	}
	
	public Piece getPiece() {
		return this.piece;
	}

}
