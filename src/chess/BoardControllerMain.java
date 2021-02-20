package chess;
 
import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
 
public class BoardControllerMain extends Application {
	
	  public static final int TILE_SIZE = 100; //height/width of each square
	    public static final int WIDTH = 8; //number of tiles to span board horizontally
	    public static final int HEIGHT = 8; //number of tiles to span board vertically
	    private RoughDraft g;
	    private LastClicked l;
	    private Group tileGroup  = new Group();
	    private Group pieceGroup = new Group();
	    

	    //creates array of tiles and pieces to be displayed
	    private Parent board() {
	    	this.l = new LastClicked();
	    	this.g = new RoughDraft();
	        StackPane root = new StackPane();
	        root.getChildren().addAll(tileGroup, pieceGroup);
	        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
	        
	        for(int i = 0; i<HEIGHT; i++) {
	        	for(int j = 0; j<WIDTH; j++) {
	        	 Tile tile = new Tile((i + j) % 2 == 0, j, i);
	        	 
	        	/* tile.setOnMouseClicked(e->{
	        		 if(l.isPiece()) {
	        			l.getLastClicked().relocate((tile.getCoors()[1]*TILE_SIZE), (tile.getCoors()[0]*TILE_SIZE)); 
	        		 }
	        		 l.setAsTile();
	        	 });*/
	        	 
	        	tileGroup.getChildren().add(tile);
	        	if (g.getBoard()[i][j]!=0) {
	        	String filepath = " ";
	        	String type = " ";
	        	//switches piece numbers in game's internal array with filepaths to sprites
	        	switch (g.getBoard()[i][j]) {
	        	case 1: filepath = "/Users/kevincodd/Documents/Code/Chess/PNGs/Noshadow/2x/w_king_2x_ns.png"; type = "k"; break;
	        	case 2: filepath = "/Users/kevincodd/Documents/Code/Chess/PNGs/Noshadow/2x/w_queen_2x_ns.png"; type = "q"; break;
	        	case 3: filepath = "/Users/kevincodd/Documents/Code/Chess/PNGs/Noshadow/2x/w_rook_2x_ns.png"; type = "r"; break;
	        	case 4: filepath = "/Users/kevincodd/Documents/Code/Chess/PNGs/Noshadow/2x/w_knight_2x_ns.png"; type = "n"; break;
	        	case 5: filepath = "/Users/kevincodd/Documents/Code/Chess/PNGs/Noshadow/2x/w_bishop_2x_ns.png"; type = "b"; break;
	        	case 6: filepath = "/Users/kevincodd/Documents/Code/Chess/PNGs/Noshadow/2x/w_pawn_2x_ns.png"; type = "p"; break;
	        	case 7: filepath = "/Users/kevincodd/Documents/Code/Chess/PNGs/Noshadow/2x/b_king_2x_ns.png"; type = "k"; break;
	        	case 8: filepath = "/Users/kevincodd/Documents/Code/Chess/PNGs/Noshadow/2x/b_queen_2x_ns.png"; type = "q"; break;
	        	case 9: filepath = "/Users/kevincodd/Documents/Code/Chess/PNGs/Noshadow/2x/b_rook_2x_ns.png"; type = "r"; break;
	        	case 10: filepath = "/Users/kevincodd/Documents/Code/Chess/PNGs/Noshadow/2x/b_knight_2x_ns.png"; type = "n"; break;
	        	case 11: filepath = "/Users/kevincodd/Documents/Code/Chess/PNGs/Noshadow/2x/b_bishop_2x_ns.png"; type = "b"; break;
	        	case 12: filepath = "/Users/kevincodd/Documents/Code/Chess/PNGs/Noshadow/2x/b_pawn_2x_ns.png"; type = "p"; break;
	        	}
	        	File file = new File(filepath);
	        	Image image = new Image(file.toURI().toString());
	        	String color = " ";
	        	if (g.getBoard()[i][j]<7) {
	        		color = "white";
	        	}
	        	else {
	        		color = "black";
	        	}
	        	
	        	String id = "b";
	        	PieceDisp piece = new PieceDisp(color, type, id, i, j);
	        	piece.setImage(image);
	        	//Set king and queen size
	        	if((g.getBoard()[i][j]==1)||(g.getBoard()[i][j]==2)||(g.getBoard()[i][j]==7)||(g.getBoard()[i][j]==8)) {
	        		piece.setFitHeight(80);
		        	piece.setFitWidth(80);}
	        	//set pawn size
	        	else if((g.getBoard()[i][j]==6)||( g.getBoard()[i][j]==12)) {
	        		piece.setFitHeight(60);
		        	piece.setFitWidth(50);}
	        	//set size of rest of pieces
	        	else {
	        	piece.setFitHeight(75);
	        	piece.setFitWidth(65);}
	        	piece.relocate(j*TILE_SIZE, (7-i)*TILE_SIZE);
	        	
	        	
	        	
	        	/*piece.setOnMouseClicked(e->{
	        		if(l.isPiece()) {
	        			l.getLastClicked().relocate((piece.getCoors()[1]*TILE_SIZE), (piece.getCoors()[0]*TILE_SIZE));
	        		}
	        		l.setAsPiece(piece);
	        	});*/
	        	
	        	
	        	piece.setOnMouseDragged(e->{
	        		if(piece.getColor().equals("white")&& g.getCurPlayer()!=0) { //makes sure right color is moving
	        			piece.resetLocation();
	        			return;
	        		}
	        		if(piece.getColor().equals("black")&& g.getCurPlayer()!=1) {
	        			piece.resetLocation();
	        			return;
	        		}
	        		piece.relocate(e.getSceneX(), e.getSceneY());
	        		piece.setMouseCoors((int)e.getSceneY()/TILE_SIZE, (int)e.getSceneX()/TILE_SIZE);
	        		System.out.println(e.getSceneY());
	        		System.out.println(e.getSceneX());
	
	        		
	        	});
	        	
	        	piece.setOnMouseReleased(e->{
	        		piece.relocate((piece.getMouseCoors()[1]*TILE_SIZE), (piece.getMouseCoors()[0]*TILE_SIZE));
	        		System.out.println(piece.getMouseCoors()[1]);
	        		System.out.println(piece.getMouseCoors()[0]);
	        		//TODO: legal check: if legal, flip turn, if not, return piece to previous position
	        		piece.setMouseCoors((int)e.getSceneY()/TILE_SIZE, (int)e.getSceneX()/TILE_SIZE);
	        		piece.setCoors((int)e.getSceneY()/TILE_SIZE, (int)e.getSceneX()/TILE_SIZE);
	        		g.togglePlayer(); //put this in method that confirms successful move
	        	});
	
	        	 
	        	pieceGroup.getChildren().add(piece);
	        	
	       
	        	}
	        }
	        }
	        
	        return root;}
	    
	    

    
    @Override
    public void start(Stage primaryStage) throws Exception {

    	Stage window = primaryStage;
    	StackPane layout = new StackPane();
    	Scene menu = new Scene(layout, 800, 800);
    	
    	
 Scene board = new Scene(board());
 Button button = new Button("Play Chess!");
	button.setOnAction(e->{
		window.setScene(board);
	
	});
	layout.getChildren().add(button);
	window.setScene(menu);
	window.setTitle("Test");
    window.show();
        
    }
 public static void main(String[] args) {
        launch(args);
    }
}