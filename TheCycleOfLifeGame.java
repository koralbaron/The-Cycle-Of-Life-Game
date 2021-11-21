import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*
* Name: Koral Baron
* Date: 19.11.21
* The Game of Life by John Conway
*/
public class TheCycleOfLifeGame extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TheCycleOfLifeInterface.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("The Cycle Of Life Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
