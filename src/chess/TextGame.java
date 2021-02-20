package chess;
import java.util.Scanner;

public class TextGame {

	
	public static void main(String [] args) {
		
		Game g = new Game();
		
		Piece [][]test = g.getBoard();
		
		for(int i = 0; i<test.length; i++) {
			for(int j = 0; j<test.length; j++) {
				System.out.print(test[i][j]+" ");
			}
			System.out.println();
		}
	
		Scanner reader = new Scanner(System.in);
		
		while(g.endChecker()==0) {
			int fi = 0;
			int ri = 0;
			int ff = 0;
			int rf = 0;
			
			if(g.getCurPlayer().getColor().equals("w")){
	            System.out.println("White's move.\n");
	          }
	          else {
	              System.out.println("Black's move.\n");
	          }
			
			 System.out.println("Enter current file:");
		      while (!reader.hasNext()){
		        reader.next();}
		      fi = letterConverter(reader.next());
		      
		      System.out.println("Enter current rank:");
		      while (!reader.hasNextInt()){
		        reader.next();}
		      ri = numberConverter(reader.nextInt());
		      
		      System.out.println("Enter destination file:");
		      while (!reader.hasNext()){
		        reader.next();}
		      ff = letterConverter(reader.next());
		      
		      System.out.println("Enter destination rank:");
		      while (!reader.hasNextInt()){
		        reader.next();}
		      rf = numberConverter(reader.nextInt());
		      
		      System.out.println();
		      for(int i = 0; i<test.length; i++) {
					for(int j = 0; j<test.length; j++) {
						System.out.print(test[i][j]+" ");
					}
					System.out.println();
				}
			
			
		      Move m = new Move(g.getBoard()[ri][fi], rf, ff);
		
		      if (g.tryMove(m)) {
		    	 g.executeMove(m); 
		    	 printBoard(g.getBoard());
		      }
		    
		}
		
	}
	
	
	
	
	//converts chess coordinate letters into board coordinates
	   public static int letterConverter(String file) {
	     int coor = 0;
	              switch (file) {
	                case "A": coor=7; break;
	                case "B": coor=6; break;
	                case "C": coor=5; break;
	                case "D": coor=4; break;
	                case "E": coor=3; break;
	                case "F": coor=2; break;
	                case "G": coor=1; break;
	                case "H": coor=0; break;
	                case "a": coor=7; break;
	                case "b": coor=6; break;
	                case "c": coor=5; break;
	                case "d": coor=4; break;
	                case "e": coor=3; break;
	                case "f": coor=2; break;
	                case "g": coor=1; break;
	                case "h": coor=0; break;
	                default: coor=0; break;
	                }
	              return coor;
	         }


	//converts chess coordinate numbers into board coordinates
	 public static int numberConverter(int rank){
	   int coor = 0;
	     switch (rank) {
	       case 1: coor = 0; break;
	       case 2: coor = 1; break;
	       case 3: coor = 2; break;
	       case 4: coor = 3; break;
	       case 5: coor = 4; break;
	       case 6: coor = 5; break;
	       case 7: coor = 6; break;
	       case 8: coor = 7; break;
	       default: coor = 0; break;
	     }
	     return coor;
	 }

	 
	 public static void printBoard(Piece [][] board) {
		   for (int r = 7; r>-1; r--){
		     System.out.print((r+1)+" ");
		     for(int f = 0; f<board.length; f++){
		       System.out.print((board[r][f].getColor()+board[r][f].getType())+" ");
		     }
		     System.out.println();
		   }
		   System.out.print("  A  B  C  D  E  F  G  H\n\n");
		 }


	

}
