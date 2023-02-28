package dam.dad.recuperacion.ftpCliente;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import dad.miclienteftp.FTP;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Clase del controlador de Secondary.fxml
 * @author Francisco Yeray Gomez Carrion
 *
 */
public class SecondaryController implements Initializable {
	private StringProperty servidor = new SimpleStringProperty();
	private ConexionProperty model = new ConexionProperty();

	private static ConectadoProperty conectadoProperty = new ConectadoProperty();
	private BooleanProperty booleanConectado = new SimpleBooleanProperty();
	static FTPClient nc = new FTPClient();

	@FXML
	private TextField textFieldServidor;
	@FXML
	private TextField textFieldPuerto;
	@FXML
	private TextField textFieldUsuario;
	@FXML
	private PasswordField PasswordFieldContrasena;
	@FXML
	private Button buttonConectar;
	@FXML
	private Button buttonCancelar;

	/**
	 * Inicializa la vista de la conexión y establece las propiedades de los
	 * elementos.
	 * 
	 * @param url la ubicación inicial del recurso de la aplicación que se carga
	 * @param rb  los recursos específicos del usuario. En general, no se utiliza
	 *            este parámetro.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textFieldServidor.textProperty().set("ftp.rediris.es");
		model.setPuerto(21);

		model.servidorProperty().bindBidirectional(textFieldServidor.textProperty());
		textFieldPuerto.textProperty().bindBidirectional(model.puertoProperty(), new NumberStringConverter());
		model.usuarioProperty().bindBidirectional(textFieldUsuario.textProperty());
		model.contrasenaProperty().bindBidirectional(PasswordFieldContrasena.textProperty());
		booleanConectado.bindBidirectional(conectadoProperty.conectadoProperty());
	}

	/**
	 * Establece una conexión con el servidor FTP especificado en el modelo,
	 * utilizando el nombre de usuario y contraseña proporcionados. Se establece el
	 * directorio de trabajo raíz "/" y se muestra una ventana de diálogo informando
	 * sobre el estado de la conexión.
	 * 
	 * @param event Evento de acción generado por la acción de conexión.
	 * @throws IOException si se produce un error de entrada / salida al intentar
	 *                     conectarse al servidor FTP.
	 */
	@FXML
	public void onConectar(ActionEvent event) throws IOException {
		try {
			nc.connect(model.getServidor(), model.getPuerto());
			nc.login(model.getUsuario(), model.getContrasena());
			nc.changeWorkingDirectory("/");
			System.out.println(nc.printWorkingDirectory());
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			App.info("Conexión", "Conexión establecida con éxito en el servidor ");
			conectadoProperty.conectadoProperty().setValue(true);
			System.out.println(conectadoProperty.isConectado());
			stage.close();
		} catch (IOException e) {
			App.error("No se pudo conectar: " + servidor);
			conectadoProperty.conectadoProperty().setValue(false);
		}
	}

	/**
	 * Manejador de eventos para cancelar una operación y cerrar la ventana
	 * 
	 * @param event evento que activa el manejador
	 * @throws IOException si hay un error de entrada o salida
	 */
	@FXML
	private void onCancelar(ActionEvent event) throws IOException {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	/**
	 * Obtiene la ruta del directorio actual
	 * 
	 * @return la ruta del directorio actual
	 * @throws IOException si hay un error de entrada o salida
	 */
	public String getDirec() throws IOException {
		String ruta = nc.printWorkingDirectory();
		return ruta;
	}

	/**
	 * Obtiene la lista de ficheros del directorio actual
	 * 
	 * @return la lista de ficheros del directorio actual
	 * @throws IOException si hay un error de entrada o salida
	 */
	public ListProperty<FTP> lista() throws IOException {
		ListProperty<FTP> listaDeFicheros = new SimpleListProperty<>(FXCollections.observableArrayList());
		FTPFile[] ficheros = nc.listFiles();
		for (FTPFile fichero : ficheros) {
			listaDeFicheros.add(new FTP(fichero));
		}
		return listaDeFicheros;
	}

	/**
	 * Obtiene el estado de conexion actual
	 * 
	 * @return true si esta conectado, false si no lo esta
	 */
	public boolean getConected() {
		return conectadoProperty.isConectado();
	}

	/**
	 * Cambia el directorio actual dentro de la conexión ftp
	 * 
	 * @param directorio el directorio al que se quiere cambiar
	 * @throws IOException si hay un error de entrada o salida
	 */
	public void cambiarDirectorio(String directorio) throws IOException {
		if (directorio.equals("..")) {
			nc.changeWorkingDirectory(getDirec() + "/" + directorio);
			nc.changeToParentDirectory();
			// System.out.println("-/_/_/"+nc.printWorkingDirectory());
		} else {
			nc.changeWorkingDirectory(getDirec() + "/" + directorio + "/");
		}
	}

	/**
	 * Descarga un fichero del servidor FTP
	 * 
	 * @param descarga el nombre del fichero que se quiere descargar
	 * @param flujo    el flujo de salida para escribir el fichero descargado
	 * @throws IOException si hay un error de entrada o salida
	 */
	public void descargar(String descarga, FileOutputStream flujo) throws IOException {
		nc.retrieveFile(descarga, flujo);
		flujo.flush();
		flujo.close();
	}

	/**
	 * Desconecta el cliente FTP del servidor.
	 * 
	 * @throws IOException Si hay un error al desconectar el cliente FTP.
	 */
	public void desconectar() throws IOException {
		nc.disconnect();
		conectadoProperty.conectadoProperty().setValue(false);
	}

}