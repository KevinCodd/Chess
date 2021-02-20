package chess;

//Keeps track of what was last clicked
public class LastClicked {
	
	private PieceDisp p;
	
	public LastClicked() {
		p = null;
	}
	
	public void setAsTile() {
		p = null;
	}
	
	public void setAsPiece(PieceDisp p) {
		this.p = p;
	}
	
	public PieceDisp getLastClicked() {
		return this.p;
	}
	
	public boolean isPiece() {
		if (this.p != null) {
			return true;
		}
		return false;
	}
	

}


