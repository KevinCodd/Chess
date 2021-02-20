package chess;
import java.lang.Math;
import java.util.Scanner;
import java.util.ArrayList;

/*
 * Rough initial incomplete text-based sketch of the game 
 */
public class RoughDraft {
int curplayer;
int[][]board;
ArrayList<int[][]> gamelog;
int coorPayload[];

public RoughDraft(){
this.board = new int[][]{{3,4,5,2,1,5,4,3},{6,6,6,6,6,6,6,6},{0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{12,12,12,12,12,12,12,12},
	{9,10,11,8,7,11,10,9}};

/*{{3,4,5,2,1,5,4,3},{6,6,6,6,6,6,6,6},{0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{12,12,12,12,12,12,12,12},
{9,10,11,8,7,11,10,9}};*/
		/*{{9,10,11,8,7,11,10,9},{12,12,12,12,12,12,12,12},{0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{6,6,6,6,6,6,6,6},
	{3,4,5,2,1,5,4,3}};*/
this.curplayer = 0;
this.gamelog = new ArrayList<int[][]>();
this.coorPayload = new int [5];
}


  /*public static void main (String[]args) {

    game g = new game();

    while (checkMate(g.getBoard(), g.getCurPlayer(), getKingPos(g.getCurPlayer(), g.getBoard())[0], getKingPos(g.getCurPlayer(), g.getBoard())[1])==false){
        if(g.getCurPlayer()==0){
            g.setCurPlayer(1);
          }
          else {
            g.setCurPlayer(0);
          }
          int sx = 0; //starting row index in board array
          int sy = 0; //starting column index in board array
          int fx = 0; //final (after move) row index in board array
          int fy = 0; // final column index in board array

          //finds king to determine if in check
          int [] coors = getKingPos(g.getCurPlayer(), g.getBoard());
          if(inCheck(g.getBoard(), g.getCurPlayer(), coors[0], coors[1])) {
            System.out.println("check!");
          }

          if(g.getCurPlayer()==0){
            System.out.println("White's move.\n");
          }
          else {
              System.out.println("Black's move.\n");
          }
            printBoard(g.getBoard());
while ((legalCheck(g.getBoard(), g.getCurPlayer(), g.getBoard()[sx][sy], sx, sy, fx, fy)==false)){
      Scanner reader = new Scanner(System.in);
      System.out.println("Enter current column:");
      while (!reader.hasNext()){
        reader.next();}
     sy = letterConverter(reader.next());
      System.out.println("Enter current row:");
      while (!reader.hasNextInt()){
        reader.next();}
     sx = numberConverter(reader.nextInt());
     System.out.println("Enter destination column:");
     while (!reader.hasNext()){
       reader.next();}
    fy = letterConverter(reader.next());
      System.out.println("Enter destination row:");
      while (!reader.hasNextInt()){
        reader.next();}
     fx = numberConverter(reader.nextInt());
      }
      g.makeMove(sx,sy,fx,fy);
      printBoard(g.getBoard());
      g.addToLog(g.getBoard());
    }
  }*/

public void togglePlayer() {
	if (this.curplayer == 1) {
		this.curplayer = 0;
	}
	else {
		this.curplayer = 1;
	}
}

  public boolean makeMove(int sx, int sy, int fx, int fy ) {
    
    if(!legalCheck(this.board, this.curplayer, this.board[sx][sy], sx, sy, fx, fy)) {
    	return false;
    }
    this.board[fx][fy] = this.board[sx][sy];
    this.board[sx][sy] = 0;
    if(this.curplayer==0){
        this.curplayer=1;
      }
      else {
       this.curplayer=0;
      }
    	return true;
  }


  public static boolean legalCheck(int[][]board, int curplayer,
   int piece, int sx, int sy, int fx, int fy){
          /*0 = empty space
          1-6 = white, 7-12 = black
          1,7 = king
          2,8 = queen
          3,9 = rook
          4,10 = knight
          5,11 = bishop
          6,12 = pawn
          curplayer: 0=white, 1 = black
          /* Pass in piece, player, and start and end coordinates.  Check all these
          to ensure validity.  Return true if move is valid.  */
     //general rules ***********************************************************
     if ((sx<0)||(sx>7)||(sy<0)||(sy>7)||(fx<0)||(fx>7)||(fy<0)||(fy>7)){
       return false; //all coordinates must be in range of board;
     }
     if (piece ==0){
       return false; //can't move empty space
     }
     if(piece<7 && curplayer == 1){
       return false; //black player can't move white piece
     }
     if(piece>6 && curplayer == 0) {
       return false; //white player can't move black piece
     }
     if(curplayer == 0 && ((board[fx][fy]>0)&&(board[fx][fy]<7))) {
       return false; //white player can't move to space occupied by white
     }
     if(curplayer == 1 && board[fx][fy]>6) {
       return false; //black player can't move to space occupied by black
     }
     if(((fx-sx)==0)&&((fy-sy)==0)){
       return false; //can't make a move of nothing
     }
     //bishop rules ************************************************************
     if(((piece==5)||(piece==11))&&(Math.abs(fy-sy)!=Math.abs(fx-sx))){
       return false; //bishop move must be diagonal
     }
     if((piece==5)||(piece==11)){ //bishops cannot hop over other pieces
       if(((fy-sy)>0)&&((fx-sx)>0)){
         for(int i=sx+1, j= sy+1; i<fx && j<fy; i++, j++){
           if (board [i][j]!=0){
             return false;}}}
       if(((fy-sy)<0)&&((fx-sx)>0)){
         for(int i=sx+1, j= sy-1; i<fx && j>fy; i++, j--){
           if (board [i][j]!=0){
             return false;}}}
       if(((fy-sy)>0)&&((fx-sx)<0)){
         for(int i=sx-1, j= sy+1; i>fx && j<fy; i--, j++){
           if (board [i][j]!=0){
             return false;}}}
       if(((fy-sy)<0)&&((fx-sx)<0)){
         for(int i=sx-1, j= sy-1; i>fx && j>fy; i--, j--){
           if (board [i][j]!=0){
             return false;}}}
     }
     //knight rules ************************************************************
     if ((piece==4)||(piece==10)) {
       if (!(((Math.abs(fx-sx)==2)&&(Math.abs(fy-sy)==1))||((Math.abs(fx-sx)==1)&&(Math.abs(fy-sy)==2)))) {
         return false; //knight must either move horiz. 2 and vert. 1 or horiz. 1 and vert. 2
       }}
     //rook rules **************************************************************
     if ((piece==3)||(piece==9)) {
       if(!(((fy-sy)==0)||((fx-sx)==0))){
         return false; //rook must move in straight line in single direction
       }}
     if ((piece==3)||(piece==9)) { //rooks cannot hop over other pieces
       if ((fy-sy)<0){
         for(int i =sy-1; i>fy; i--){
           if(board[sx][i]!=0){
             return false; }}}
       if ((fy-sy)>0){
         for(int i =sy+1; i<fy; i++){
           if(board[sx][i]!=0){
             return false; }}}
       if ((fx-sx)<0){
         for(int i =sx-1; i>fx; i--){
           if(board[i][sy]!=0){
             return false; }}}
       if ((fx-sx)>0){
         for(int i =sx+1; i<fx; i++){
           if(board[i][sy]!=0){
             return false; }}}
     }
     //queen rules *************************************************************
     if ((piece==2)||(piece==8)) {
       if(!((((fy-sy)==0)||((fx-sx)==0))||(Math.abs(fy-sy)!=Math.abs(fx-sx)))){
         return false; //queen must move straight or diagonal
       }
     if(((fy-sy)==0)||((fx-sx)==0)){ //queen can't hop over other pieces in horiz. moves
       if ((fy-sy)<0){
         for(int i =sy-1; i>fy; i--){
           if(board[sx][i]!=0){
             return false; }}}
       if ((fy-sy)>0){
         for(int i =sy+1; i<fy; i++){
           if(board[sx][i]!=0){
             return false; }}}
       if ((fx-sx)<0){
         for(int i =sx-1; i>fx; i--){
           if(board[i][sy]!=0){
             return false; }}}
       if ((fx-sx)>0){
         for(int i =sx+1; i<fx; i++){
           if(board[i][sy]!=0){
             return false; }}}
           }
if (Math.abs(fy-sy)==Math.abs(fx-sx)){ //queen can't hop over other pieces in diag. moves
  if(((fy-sy)>0)&&((fx-sx)>0)){
    for(int i=sx+1, j= sy+1; i<fx && j<fy; i++, j++){
      if (board [i][j]!=0){
        return false;}}}
  if(((fy-sy)<0)&&((fx-sx)>0)){
    for(int i=sx+1, j= sy-1; i<fx && j>fy; i++, j--){
      if (board [i][j]!=0){
        return false;}}}
  if(((fy-sy)>0)&&((fx-sx)<0)){
    for(int i=sx-1, j= sy+1; i>fx && j<fy; i--, j++){
      if (board [i][j]!=0){
        return false;}}}
  if(((fy-sy)<0)&&((fx-sx)<0)){
    for(int i=sx-1, j= sy-1; i>fx && j>fy; i--, j--){
      if (board [i][j]!=0){
        return false;}}}
}
     }
     //pawn rules **************************************************************
    if ((piece==6)||(piece==12)) { //white can't move backwards or sideways
      if((curplayer==0)&&(fx<=sx)){
        return false;
      }
      if((curplayer==1)&&(fx>=sx)){ //black can't move backwards or sideways
        return false;
      }
      if((curplayer==0)&&(sx!=fx)&&(board[fx][fy]>6)&&(sy==fy)){ //white can't attack forward
        return false;
      }
      if((curplayer==1)&&(sx!=fx)&&((board[fx][fy]<7)&&(board[fx][fy]!=0)&&(sy==fy))){ //black can't attack forward
          return false;
        }
      
      if((curplayer==0)&&(sx!=1)){
      if((Math.abs(fy-sy)>1)||(Math.abs(fx-sx)>1)){ //white can't move more than one space unless first move
        return false;}
      }
      if((curplayer==1)&&(sx!=6)){
      if((Math.abs(fy-sy)>1)||(Math.abs(fx-sx)>1)){ //black can't move more than one space unless first move
        return false;}
      }
    if((curplayer==0)&&(sx==1)){ //white can't move more than two spaces on first move
      if(Math.abs(fx-sx)>2){
        return false;
      }}
      if((curplayer==1)&&(sx==6)){ //white can't move more than two spaces on first move
        if(Math.abs(fx-sx)>2){
          return false;
        }}
  }
     //king rules **************************************************************
    if((piece==1)||(piece==7)) {
      if(((fy-sy)>1)||((fx-sx)>1)){ //can't move more than one space in any direction
        return false;
      }
    }

//Makes copy of board with move to send "hypothetical" move to inCheck
    int[][]copy = copyBoard(board);
    copy[fx][fy]=copy[sx][sy];
    copy[sx][sy]=0;

int [] coors = getKingPos(curplayer, board);

    if(inCheck(copy, curplayer, coors[0], coors[1])){
      return false;
    }
    return true;
     }


   public static boolean inCheck(int[][]board, int curplayer, int fx, int fy) {
     /***check from diag. queens and bisops***/

     /*Goes each direction from king diagonally, accounting for boundary conditions
     and checks each space, breaking if piece of own color is encountered first
     and returning false if enemy bishop or queen is encountered first*/

     if(curplayer == 0) { //white king can't be in line of fire of black bishop/queen
             if(fx!=7&&fy!=7) { //prevents out of bounds error
       for(int i=fx+1, j= fy+1; i<board.length && j<board.length; i++, j++){
         if((board [i][j]<7)&&(board[i][j]!=0)){
           break;}
         if ((board [i][j]==8)||(board[i][j]==11)){
           return true;}}}
             if(fx!=7&&fy!=0) {
       for(int i=fx+1, j= fy-1; i<board.length && j>-1; i++, j--){
         if((board [i][j]<7)&&(board[i][j]!=0)){
           break;}
         if ((board [i][j]==8)||(board[i][j]==11)){
           return true;}}}
           if(fx!=0&&fy!=7) {
       for(int i=fx-1, j= fy+1; i>-1 && j<board.length; i--, j++){
         if((board [i][j]<7)&&(board[i][j]!=0)){
           break;}
         if ((board [i][j]==8)||(board[i][j]==11)){
           return true;}}}
             if(fx!=0&&fy!=0) {
       for(int i=fx-1, j= fy-1; i>-1 && j>-1; i--, j--){
         if((board [i][j]<7)&&(board[i][j]!=0)){
           break;}
         if ((board [i][j]==8)||(board[i][j]==11)){
           return true;}}}
         }

         if(curplayer == 1) { //black king can't be in line of fire of white bishop/queen
                 if(fx!=7&&fy!=7) { //prevents out of bounds error
           for(int i=fx+1, j= fy+1; i<board.length && j<board.length; i++, j++){
             if((board [i][j]>6)&&(board[i][j]!=0)){
               break;}
             if ((board [i][j]==2)||(board[i][j]==5)){
               return true;}}}
                 if(fx!=7&&fy!=0) {
           for(int i=fx+1, j= fy-1; i<board.length && j>-1; i++, j--){
             if((board [i][j]>6)&&(board[i][j]!=0)){
               break;}
             if ((board [i][j]==2)||(board[i][j]==5)){
               return true;}}}
               if(fx!=0&&fy!=7) {
           for(int i=fx-1, j= fy+1; i>-1 && j<board.length; i--, j++){
             if((board [i][j]>6)&&(board[i][j]!=0)){
               break;}
             if ((board [i][j]==2)||(board[i][j]==5)){
               return true;}}}
                 if(fx!=0&&fy!=0) {
           for(int i=fx-1, j= fy-1; i>-1 && j>-1; i--, j--){
             if((board [i][j]>67)&&(board[i][j]!=0)){
               break;}
             if ((board [i][j]==2)||(board[i][j]==5)){
               return true;}}}
             }

System.out.println("diag");
         /***check from straight queens and rooks***/

         if (curplayer==0){ //white king can't be in line of fire of black queen/rook
           if(fy!=0){ //prevents out of bounds error
           for(int i =fy-1; i>-1; i--){
             if((board [fx][i]<7)&&(board[fx][i]!=0)){
               break;}
             if((board [fx][i]==8)||(board[fx][i]==9)){
               return true; }}}
           if(fy!=7){
           for(int i =fy+1; i<board.length; i++){
             if((board [fx][i]<7)&&(board[fx][i]!=0)){
               break;}
             if((board [fx][i]==8)||(board[fx][i]==9)){
               return true; }}}
           if(fx!=0){
           for(int i =fx-1; i>-1; i--){
             if((board [i][fy]<7)&&(board[i][fy]!=0)){
               break;}
             if((board [i][fy]==8)||(board[i][fy]==9)){
               return true; }}}
           if(fx!=7){
           for(int i =fx+1; i<board.length; i++){
             if((board [i][fy]<7)&&(board[i][fy]!=0)){
               break;}
             if((board [i][fy]==8)||(board[i][fy]==9)){
               return true; }}}
             }

             if (curplayer==1){ //black king can't be in line of fire of white queen/rook
               if(fy!=0){ //prevents out of bounds error
               for(int i =fy-1; i>-1; i--){
                 if(board [fx][i]>6){
                   break;}
                 if((board [fx][i]==2)||(board[fx][i]==3)){
                   return true; }}}
               if(fy!=7){
               for(int i =fy+1; i<board.length; i++){
               if(board [fx][i]>6){
                   break;}
                 if((board [fx][i]==2)||(board[fx][i]==3)){
                   return true; }}}
               if(fx!=0){
               for(int i =fx-1; i>-1; i--){
               if(board [i][fy]>6){
                   break;}
                 if((board [i][fy]==2)||(board[i][fy]==3)){
                   return true; }}}
               if(fx!=7){
               for(int i =fx+1; i<board.length; i++){
                 if(board [i][fy]>6){
                   break;}
                 if((board [i][fy]==2)||(board[i][fy]==3)){
                   return true; }}}
                 }
                 System.out.println("straight");

  /***check from knights***/
        if (curplayer == 0) { //goes through whole board and sees if pieces a knight's move away are black knights
         for(int i = 0; i < board.length; i++) {
           for(int j =0; j <board.length; j++) {
             if((Math.pow((i-fx),2)+Math.pow((j-fy),2)==Math.sqrt(5))&&(board[i][j]==10)){
               return true;}}}
       }

       if (curplayer == 1) { //goes through whole board and sees if pieces a knight's move away are white knights
        for(int i = 0; i < board.length; i++) {
          for(int j =0; j <board.length; j++) {
            if((Math.sqrt(Math.pow((i-fx),2)+Math.pow((j-fy),2))==Math.sqrt(5))&&(board[i][j]==4)){
              return true;}}}
       }

       System.out.println("knights");

  /***check from pawns***/
if(curplayer == 0) { //white king
  if(fx!=7) { //boundary condition
    if((fy!=0)&&(fy!=7)) {
      if((board[fx+1][fy-1]==12)||(board[fx+1][fy+1]==12)){
        return true;}}
    if((fy!=0)) {
      if((board[fx+1][fy-1]==12)){
        return true;}}
    if((fy!=7)) {
      if(board[fx+1][fy+1]==12){
        return true;}}}
      }

if(curplayer == 1) { //black king
  if(fx!=7) { //boundary condition
    if((fy!=0)&&(fy!=7)) {
      if((board[fx-1][fy-1]==6)||(board[fx+1][fy+1]==6)){
        return true;}}
    if((fy!=0)) {
      if((board[fx-1][fy-1]==6)){
        return true;}}
    if((fy!=7)) {
      if(board[fx-1][fy+1]==6){
        return true;}}}
      }
      System.out.println("pawns");

    /***check from kings***/
    if(curplayer==0){
    for(int i = 0; i < board.length; i++) {
      for(int j =0; j <board.length; j++) {
        if((Math.abs(fx-i)<=1)&&(Math.abs(fy-j)<=1)&&(board[i][j]==7)){
          return true;}}}}

    if(curplayer==1){
    for(int i = 0; i < board.length; i++) {
      for(int j =0; j <board.length; j++) {
        if((Math.abs(fx-i)<=1)&&(Math.abs(fy-j)<=1)&&(board[i][j]==1)){
          return true;}}}}
          System.out.println("kings");

       return false;
     }


     public static boolean checkMate(int[][]board, int curplayer, int fx, int fy){
    	 //TODO
       int [][] copy = copyBoard(board);
       if(inCheck(board, curplayer, fx, fy)){
         if(curplayer==0){
           //use searchBoard method to get locations of each type of piece
           //attempt all possible moves with each of those pieces and check InCheck for each

           return false; //just a placeholder for now
         }

       }
       return false;
     }


//NOTE: prints rows in reverse order of internal array
 public static void printBoard(int board[][]) {
   for (int i = 7; i>-1; i--){
     System.out.print((i+1)+" ");
     for(int j = 0; j<board.length; j++){
       System.out.print(pieceConverter(board[i][j])+" ");
     }
     System.out.println();
   }
   System.out.print("  A  B  C  D  E  F  G  H\n\n");
 }


 //converts chess coordinate letters into board coordinates
   public static int letterConverter(String co) {
     int coor = 0;
              switch (co) {
                case "A": coor=0; break;
                case "B": coor=1; break;
                case "C": coor=2; break;
                case "D": coor=3; break;
                case "E": coor=4; break;
                case "F": coor=5; break;
                case "G": coor=6; break;
                case "H": coor=7; break;
                case "a": coor=0; break;
                case "b": coor=1; break;
                case "c": coor=2; break;
                case "d": coor=3; break;
                case "e": coor=4; break;
                case "f": coor=5; break;
                case "g": coor=6; break;
                case "h": coor=7; break;
                default: coor=0; break;
                }
              return coor;
         }


//converts chess coordinate numbers into board coordinates
 public static int numberConverter(int co){
   int coor = 0;
     switch (co) {
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


//converts board array elements to letters representing pieces
 public static String pieceConverter(int co) {
   String letter ="  ";
            switch (co) {
             case 0: letter="  "; break;
             case 1: letter="wK"; break;
             case 2: letter="wq"; break;
             case 3: letter="wr"; break;
             case 4: letter="wk"; break;
             case 5: letter="wb"; break;
             case 6: letter="wp"; break;
             case 7: letter="bK"; break;
             case 8: letter="bq"; break;
             case 9: letter="br"; break;
             case 10: letter="bk"; break;
             case 11: letter="bb"; break;
             case 12: letter="bp"; break;
              }
            return letter;
       }


public static int pieceEnum(String piece) {
  int no = 0;
      switch (piece) {
        case "wking": no = 1; break;
        case "wqueen": no = 2; break;
        case "wrook": no = 3; break;
        case "wknight": no = 4; break;
        case "wbishop": no = 5; break;
        case "wpawn": no = 6; break;
        case "bking": no = 7; break;
        case "bqueen": no = 8; break;
        case "brook": no = 9; break;
        case "bknight": no = 10; break;
        case "bbishop": no = 11; break;
        case "bpawn": no = 12; break;
      }
      return no;
}

 public int[][] getBoard(){
   return this.board;
 }


 public int getCurPlayer(){
   return this.curplayer;
 }


 public void setCurPlayer(int curplayer){
   this.curplayer = curplayer;
 }


public void addToLog(int[][]board){
  this.gamelog.add(board);
}

public void setCoorPayload(int index, int coor) {
	this.coorPayload[index]=coor;
}


//returns array of length 2 with kx, ky
public static int[] getKingPos(int curplayer, int[][]board){
  int[] coors = new int[2];
  if(curplayer==0){
  for(int i = 0; i<board.length; i++){
    for(int j = 0; j<board.length; j++) {
      if(board[i][j]==1){
        coors[0]=i;
        coors[1]=j;}}}}

  if(curplayer==1){
  for(int i = 0; i<board.length; i++){
    for(int j = 0; j<board.length; j++) {
      if(board[i][j]==7){
        coors[0]=i;
        coors[1]=j;}}}}
        return coors;
}


//returns copy of board
public static int [][] copyBoard(int[][]board){
  int[][]copy = new int [8][8];
  for(int i = 0; i<copy.length; i++){
    for(int j = 0; j<copy[i].length; j++){
      copy[i][j]=board[i][j];
    }
  }
  return copy;
}


public static ArrayList<int[]> searchBoard(int[][]board, int piece){
  ArrayList piecelocs = new ArrayList<int[]>();
  for(int i = 0; i<board.length; i++){
    for(int j = 0; j<board[i].length; j++){
      if(board[i][j]==piece){
        int[]coors = {i, j};
        piecelocs.add(coors);
      }}}return piecelocs;
    }

//8 is key for fifth array elements to confirm readiness to send
public boolean handlePayload() {
	if (this.coorPayload[4]==8) {
		if(makeMove(this.coorPayload[0], this.coorPayload[1], this.coorPayload[2], this.coorPayload[3])) {
			return true;
		}
	}
	this.coorPayload[4]=0; //resets to 0 so key isn't still there next time it is called
	return false;
}


}
