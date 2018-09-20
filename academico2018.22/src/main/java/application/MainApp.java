package application;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Principal.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Acadêmico - 2018");
        stage.setScene(scene);
        stage.show();
        scene.getWindow().setOnCloseRequest((WindowEvent ev) -> {
        Alert alert = new Alert (Alert.AlertType.CONFIRMATION,
        "Deseja Realmente Sair do Sistema ?",
                ButtonType.YES,ButtonType.NO);
        alert.setTitle("Acadêmico - 2018");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.NO){
            ev.consume();
            alert.close();
        }
    });
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
