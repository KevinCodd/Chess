package chess;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.util.Random;
import javafx.scene.control.Label;

public class BoardController {
	
	@FXML
	private Label myMessage;
	
	public void generateRandom(ActionEvent event) {
		Random rand = new Random();
		int myRand = rand.nextInt(100)+1;
		System.out.println(Integer.toString(myRand));
		
		
	}
	
	
	
} 

	
 

