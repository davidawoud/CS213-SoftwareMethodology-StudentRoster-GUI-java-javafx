package student;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
Main class that starts the GUI program.
@author David Halim, Stephen Juan
*/
public class Main extends Application {
	/**
    Starts the GUI and initializes the scene
    @param primaryStage of the GUI
    */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("TuitionManager.fxml"));  
			Scene scene = new Scene(root); 
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Tuition Manager"); 
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
    This is a main method that calls launch method.
    @param args - user input - this is not used
    */
	public static void main(String[] args) {
		launch(args);
	}
}
