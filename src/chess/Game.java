package chess;
import java.util.ArrayList;


public class Game {

	//array representing board, piece locations
	private int [][]board;
	//current player - black or white
	private String curplayer;
	//stores every iteration of the game board
	private ArrayList<int[][]> gameHistory;
	
	public Game() {
		
		//board initialization here looks likes black's perspective e.g. files H...A, ranks (bottom to top) 8...1
		board = new int [][] {{1,2,3,4,5,3,2,1},{6,6,6,6,6,6,6,6},
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{12,12,12,12,12,12,12,12},{7,8,9,10,11,9,8,7}};
		
		curplayer = "white";
		
		gameHistory = new ArrayList<int[][]>();
		gameHistory.add(board);
	}
	
	
	/**
	 *  makes move - returns true if legal and updates game variables - returns false if illegal
	 *  @param ri - initial rank
	 *  @param fi - initial file
	 *  @param rf - final rank
	 *  @param ff - final file
	 *  
	*/
	public boolean makeMove(int ri, int fi, int rf, int ff) {
		if(isLegal(ri, fi, rf, ff)) {
			board[rf][ff] = board[ri][fi];
			board[ri][fi] = 0;
			gameHistory.add(board);
			if (curplayer.equals("white")) {
				curplayer = "black";
			}
			else {
				curplayer = "white";
			}
			return true;
		}
		return false;
	}
	
	
	//checks if moves are legal
	public boolean isLegal(int ri, int fi, int rf, int ff) {
	
		//general rules ***********************************************************
		//if any coordinates are out of bounds
		if((ri>7||ri<0)||(fi>7||fi<0)||(rf>7||rf<0)||(ff>7||ff<0)) return false;
		//if the initial square is empty
		if(board[ri][fi]==0) return false;
		//if white tries to move black
		if(curplayer.equals("white")&&board[ri][fi]>6) return false;
		//if black tries to move white
		if(curplayer.equals("black")&&board[ri][fi]<7) return false;
		//if white tries to move onto white
		if(curplayer.equals("white")&&board[rf][ff]<7&&board[rf][ff]>0) return false;
		//if black tries to move onto black
		if(curplayer.equals("black")&&board[rf][ff]>6) return false;
		// can't move to current square
		if(ri==rf&&fi==ff) return false;
		
		//bishop rules ***********************************************************
	    if(((board[ri][fi]==3)||(board[rf][ff]==9))){
	    	
	    	//if move isn't diagonal
	    	if(Math.abs(ri-rf)!=Math.abs(fi-ff)) return false;
	    	
	    	//check that bishop isn't jumping over any pieces:
	    	//southeast scan (with respect to Game's array)
	    	if(((rf-ri)>0)&&((ff-fi)>0)){
			    for(int r=ri+1, f= fi+1; r<rf && f<ff; r++, f++){
			      if (board [r][f]!=0) return false;
			    }
	    	}
	    	//northeast scan
	    	if(((rf-ri)<0)&&((ff-fi)>0)){
			    for(int r=ri-1, f= fi+1; r>rf && f<ff; r--, f++){
			      if (board [r][f]!=0) return false;
			    }
	    	}
	    	//southwest scan
	    	if(((rf-ri)>0)&&((ff-fi)<0)){
			    for(int r=ri+1, f= fi-1; r<rf && f>ff; r++, f--){
			      if (board [r][f]!=0) return false;
			    }
	    	}
	    	//northwest scan
	    	if(((rf-ri)<0)&&((ff-fi)<0)){
			    for(int r=ri-1, f= fi-1; r>rf && f>ff; r--, f--){
			      if (board [r][f]!=0) return false;
			    }
	    	}
	    	
	    	return true;
	    }
		
	    //knight rules ***********************************************************
	    if ((board[ri][fi]==2)||(board[ri][fi]==8)) {
	    	//knights must go horiz 1 vert 2 or vice versa
	    	if (!(((Math.abs(rf-ri)==2)&&(Math.abs(ff-fi)==1))||((Math.abs(rf-ri)==1)&&(Math.abs(ff-fi)==2)))) return false;
	    	return true;
	    }
	    
	    //rook rules ***********************************************************
	    if ((board[ri][fi]==1)||(board[ri][fi]==7)) {
	    	
	    	//enforce horiz/vert motion
	        if(!(((rf-ri)==0)||((ff-fi)==0))) return false;
	       
	        //prevent jumping over pieces:
	        //leftward
	        if ((ff-fi)<0){
	          for(int f =fi-1; f>ff; f--){
	            if(board[ri][f]!=0) return false;
	          }
	        }
	        //rightward
	        if ((ff-fi)>0){
	          for(int f =fi+1; f<ff; f++){
	            if(board[ri][f]!=0) return false;
	          	} 
	          }
	        //upward
	        if ((rf-ri)<0){
	          for(int r =ri-1; r>rf; r--){
	            if(board[r][fi]!=0)return false;
	          }
	        }
	        //downward
	        if ((rf-ri)>0){
	          for(int r =ri+1; r<rf; r++){
	            if(board[r][fi]!=0)return false; 
	          }
	        }
	    }
	    
	    //queen rules ***********************************************************
	    if(board[ri][rf]==5||board[ri][rf]==11) { 
		    //queen must move straight or diagonal
		    if(!((((rf-ri)==0)||((ff-fi)==0))||(Math.abs(rf-ri)==Math.abs(ff-fi)))) return false;
		    
		    //if moving horiz/vert
		    if((rf-ri)==0||(ff-fi)==0) {
		    	//enforce horiz/vert motion
		        if(!(((rf-ri)==0)||((ff-fi)==0))) return false;
		       
		        //prevent jumping over pieces:
		        //leftward
		        if ((ff-fi)<0){
		          for(int f =fi-1; f>ff; f--){
		            if(board[ri][f]!=0) return false;
		          }
		        }
		        //rightward
		        if ((ff-fi)>0){
		          for(int f =fi+1; f<ff; f++){
		            if(board[ri][f]!=0) return false;
		          	} 
		          }
		        //upward
		        if ((rf-ri)<0){
		          for(int r =ri-1; r>rf; r--){
		            if(board[r][fi]!=0)return false;
		          }
		        }
		        //downward
		        if ((rf-ri)>0){
		          for(int r =ri+1; r<rf; r++){
		            if(board[r][fi]!=0)return false; 
		          }
		        }	
		    }
		    //if moving diagonal
		    if(Math.abs(rf-ri)==Math.abs(ff-fi)){
		    	//check that queen isn't jumping over any pieces:
		    	//southeast scan (with respect to Game's array)
		    	if(((rf-ri)>0)&&((ff-fi)>0)){
				    for(int r=ri+1, f= fi+1; r<rf && f<ff; r++, f++){
				      if (board [r][f]!=0) return false;
				    }
		    	}
		    	//northeast scan
		    	if(((rf-ri)<0)&&((ff-fi)>0)){
				    for(int r=ri-1, f= fi+1; r>rf && f<ff; r--, f++){
				      if (board [r][f]!=0) return false;
				    }
		    	}
		    	//southwest scan
		    	if(((rf-ri)>0)&&((ff-fi)<0)){
				    for(int r=ri+1, f= fi-1; r<rf && f>ff; r++, f--){
				      if (board [r][f]!=0) return false;
				    }
		    	}
		    	//northwest scan
		    	if(((rf-ri)<0)&&((ff-fi)<0)){
				    for(int r=ri-1, f= fi-1; r>rf && f>ff; r--, f--){
				      if (board [r][f]!=0) return false;
				    }
		    	}
		    }
	    
	    }
	    
	    //king rules ***********************************************************
	    if((board[ri][fi]==4)||(board[ri][fi]==10)) {
	        if(((rf-ri)>1)||((ff-fi)>1)) return false;
	    }
		    
	    // white pawn rules ***********************************************************
	    if(board[ri][fi]==6) {
	    	
	    	//can only double push off starting rank - have to stay in same file, cannot jump over/onto enemy pieces
	    	if(rf-ri>1&&((ri!=1||rf!=3)||(fi!=ff)||(board[2][fi]!=0||board[3][fi]!=0))) return false;
	    	
	    	//lateral move must be one rank forward and one rank sideways
	    	if(ff!=fi) {
	    		
	    		//can only move laterally one and forward one if moving laterally
	    		if(Math.abs(ff-fi)!=1||rf-ri!=1) return false;
	    		
	    		/*en passant --on fifth rank & enemy pawn adjacent and enemy pawn starting position on ff is empty
	        	and an enemy pawn was in that starting position last turn and [ri][ff] was empty last turn*/
	        	if(ri==4&&board[ri][ff]==12&&board[rf+1][ff]==0&&gameHistory.get(gameHistory.size()-2)[rf+1][ff]==12&&
	        			gameHistory.get(gameHistory.size()-2)[ri][ff]==0) return true;
	        	
	        	//have to move onto an enemy piece when moving laterally
	        	if(board[rf][ff]==0) return false;
	    	}
	    	//single push after all other legal cases are covered
	    	if(!(ff==fi&&rf==ri+1)) return false;
	    	//can't push onto enemy piece
	    	if(board[rf][ff]!=0) return false;	
	    }
	    
	    // white pawn rules ***********************************************************
		if(board[ri][fi]==12) {
		    	
		    	//can only double push off starting rank - have to stay in same file, cannot jump over/onto enemy pieces
		    	if(rf-ri>1&&((ri!=6||rf!=4)||(fi!=ff)||(board[5][fi]!=0||board[4][fi]!=0))) return false;
		    	
		    	//lateral move must be one rank forward and one rank sideways
		    	if(ff!=fi) {
		    		
		    		//can only move laterally one and forward one if moving laterally
		    		if(Math.abs(ff-fi)!=1||rf-ri!=-1) return false;
		    		
		    		/*en passant --on fifth rank & enemy pawn adjacent and enemy pawn starting position on ff is empty
		        	and an enemy pawn was in that starting position last turn and [ri][ff] was empty last turn*/
		        	if(ri==3&&board[ri][ff]==6&&board[rf-1][ff]==0&&gameHistory.get(gameHistory.size()-2)[rf-1][ff]==6&&
		        			gameHistory.get(gameHistory.size()-2)[ri][ff]==0) return true;
		        	
		        	//have to move onto an enemy piece when moving laterally
		        	if(board[rf][ff]==0) return false;
		    	}
		    	//single push after all other legal cases are covered
		    	if(!(ff==fi&&rf==ri-1)) return false;
		    	//can't push onto enemy piece
		    	if(board[rf][ff]!=0) return false;	
		    }
		return true;	
	}
	
	public int[][] getBoard(){
	
		return board;
		
	}


}
