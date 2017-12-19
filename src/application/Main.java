package application;
	
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;




public class Main extends Application {
	//MediaPlayer mediaplayer;
	@Override
	public void start(Stage primaryStage) {
		try {
			/*Media musicFile = new Media("file:///C://Users//haggag//Desktop//networks-project//Sm3na_com_15988.mp3");
			mediaplayer = new MediaPlayer(musicFile);
			mediaplayer.setAutoPlay(true);
			mediaplayer.setVolume(100);*/
		
			Parent root = FXMLLoader.load(getClass().getResource("splashScreen.fxml"));	
			
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(6), root);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			
			
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(6), root);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);
			
			fadeIn.play();
			
			fadeIn.setOnFinished((e)-> {
				fadeOut.play();
			});
			
			fadeOut.setOnFinished((e)-> {
				try {
					//mediaplayer.stop();
					Parent root1 = FXMLLoader.load(getClass().getResource("initialScreen.fxml"));
					//root.getChildrenUnmodifiable().setAll(parentContent);
					Scene scene = new Scene(root1,901,526);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			
			Scene scene = new Scene(root,901,526);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
