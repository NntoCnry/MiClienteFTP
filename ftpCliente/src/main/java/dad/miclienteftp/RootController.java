package dad.miclienteftp;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RootController implements Initializable {

	// controllers

	private LoginController loginController = new LoginController();

	// model

	private ObjectProperty<FTPClient> cliente = new SimpleObjectProperty<>();

	private BooleanProperty conectado = new SimpleBooleanProperty();

	// view

	@FXML
	private VBox view;

	@FXML
	private Label infoLabel;

	@FXML
	private Button conectarButton, desconectarButton;

	public RootController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings

		infoLabel.textProperty()
				.bind(Bindings.when(conectado).then("Conectado al servidor FTP").otherwise("Desconectado"));

		conectarButton.disableProperty().bind(conectado);
		desconectarButton.disableProperty().bind(conectado.not());

		cliente.bindBidirectional(loginController.clienteProperty());

		// listeners

		cliente.addListener((o, ov, nv) -> onClienteChanged(o, ov, nv));

		conectado.addListener((o, ov, nv) -> onConectadoChanged(o, ov, nv));

	}

	private void onConectadoChanged(ObservableValue<? extends Boolean> o, Boolean ov, Boolean nv) {

		if (nv) {

			// recuperar un listado de los ficheros y directorios del directorio actual del
			// servidor

			try {
				FTPFile[] ficheros = cliente.get().listFiles();

				// recorrer el listado de archivos recuperados
				System.out.format("+------------------------+%n");
				System.out.format("| Archivos del servidor: |%n");
				System.out.format("+------------------------+-----------------+----------------+-----------------+%n");
				System.out.format("| Nombre                                   | Tamaño (bytes) | Tipo            |%n");
				System.out.format("+------------------------------------------+----------------+-----------------+%n");
				Arrays.stream(ficheros).forEach(fichero -> {
					System.out.format("| %-40s | %-14d | %-15s |%n", fichero.getName(), fichero.getSize(),
							fichero.getType());
				});
				System.out.format("+------------------------------------------+----------------+-----------------+%n");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void onClienteChanged(ObservableValue<? extends FTPClient> o, FTPClient ov, FTPClient nv) {

		if (nv != null && nv.isConnected()) {

			conectado.set(true);

		} else {

			conectado.set(false);

		}

	}

	public VBox getView() {
		return view;
	}

	@FXML
	void onConectarButton(ActionEvent event) {

		loginController.show();

	}

	@FXML
	void onDesconectarButton(ActionEvent event) {

		try {
			cliente.get().disconnect();
			cliente.set(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
