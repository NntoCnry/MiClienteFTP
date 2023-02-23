package dam.dad.recuperacion.ftpCliente;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase donde se lanza la interfaz
 * @author Francisco Yeray Gomez Carrion
 *
 */
public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;
/**
 * Metodo de inicio de la aplicacion que carga la escena principal y la muestra en la ventana.
 * @param stage La ventana principal de la aplicacion.
 * @throws IOException Si hay algun error al cargar el archivo FXML.
 */
	@Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.setTitle("MiClienteFTP");
        stage.show();
    }
	
	/**
	 * Cambia la vista mostrada en la ventana a la especificada por el archivo FXML.
	 * @param fxml El nombre del archivo FXML que define la vista.
	 * @throws IOException Si hay algun error al cargar el archivo FXML.
	 */
	  static void setRoot(String fxml) throws IOException {
	        scene.setRoot(loadFXML(fxml));
			Stage stage = new Stage();
		    stage.setScene(scene);
		    stage.initModality(Modality.APPLICATION_MODAL);
		    stage.showAndWait();
		    
		    stage.close();
	    }
	  
	  /**
	   * Carga y devuelve la vista definida en el archivo FXML especificado.
	   * @param fxml El nombre del archivo FXML que define la vista.
	   * @return La vista cargada desde el archivo FXML.
	   * @throws IOException Si hay algun error al cargar el archivo FXML.
	   */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    /**
     * Devuelve la ventana principal de la aplicacion.
     * @return La ventana principal de la aplicacion.
     */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	/**
	 * Muestra una ventana de error con el mensaje especificado.
	 * @param header El mensaje de error a mostrar en la ventana.
	 */
	public static void error(String header) {
		Alert error = new Alert(AlertType.ERROR);
		error.initOwner(getPrimaryStage());
		error.setTitle("Error");
		error.setHeaderText(header);
		error.showAndWait();
	}
	
	/**
	 * Muestra una ventana de informacion con el titulo y mensaje especificados.
	 * @param title El título de la ventana de informacion.
	 * @param header El mensaje a mostrar en la ventana de informacion.
	 */
	public static void info(String title, String header) {
		Alert info = new Alert(AlertType.INFORMATION);
		info.initOwner(getPrimaryStage());
		info.setTitle(title);
		info.setHeaderText(header);
		info.showAndWait();
	}
	/**
	 * Metodo principal que lanza la aplicacion.
	 * @param args Los argumentos de linea de comandos (no se utilizan).
	 */
    public static void main(String[] args) {
        launch();
    }

}