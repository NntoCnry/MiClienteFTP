package dam.dad.recuperacion.ftpCliente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dad.miclienteftp.FTP;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Clase del controlador de Primary.fxml
 * 
 * @author Francisco Yeray Gomez Carrion
 *
 */
public class PrimaryController implements Initializable {

	private static ListProperty<FTP> tableView = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<FTP> seleccionado = new SimpleObjectProperty<>();
	private SecondaryController sc = new SecondaryController();

	private static ConectadoProperty conectadoProperty = new ConectadoProperty();
	private BooleanProperty booleanConectado = new SimpleBooleanProperty();

	@FXML
	private Label labelDirectorio;
	@FXML
	private MenuBar menuBar;
	@FXML
	private Menu menu;
	@FXML
	private MenuItem itemConectar;
	@FXML
	private MenuItem itemDesconectar;
	@FXML
	private Button botonGenerarPDF;
	@FXML
	private TableView<FTP> tableViewFichero;
	@FXML
	private TableColumn<FTP, String> tableColumnNombre;
	@FXML
	private TableColumn<FTP, Number> tableColumnTamano;
	@FXML
	private TableColumn<FTP, String> tableColumnTipo;

	/**
	 * Inicializa la vista de la aplicación y establece las propiedades de los
	 * elementos de la tabla de archivos, menu y botones.
	 * 
	 * @param url la ubicacion inicial del recurso de la aplicacion que se carga
	 * @param rb  los recursos especificos del usuario. En general, no se utiliza
	 *            este parámetro.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableViewFichero.itemsProperty().bind(tableView);
		tableColumnNombre.setCellValueFactory(v -> v.getValue().nombreFicheroProperty());
		tableColumnTamano.setCellValueFactory(v -> v.getValue().tamanoFicheroProperty());
		tableColumnTipo.setCellValueFactory(v -> v.getValue().tipoFicheroProperty());
		itemDesconectar.setDisable(true);
		botonGenerarPDF.setDisable(true);
		seleccionado.bind(tableViewFichero.getSelectionModel().selectedItemProperty());
		booleanConectado.bindBidirectional(conectadoProperty.conectadoProperty());
//		conectadoProperty.conectadoProperty().addListener((obs, oldValue, newValue) -> {
//			//System.out.println("obs" + obs.getValue());
//			if (newValue) {
//	                System.out.println("El cliente se ha conectado");
//	            } else {
//	                System.out.println("El cliente se ha desconectado");
//	            }});
	}

	/**
	 * Este metodo es llamado cuando el usuario hace clic en el menú "Conectar".
	 * Carga una nueva ventana "secondary.fxml" y muestra su contenido en un nuevo
	 * escenario. Obtiene el controlador de la ventana cargada y establece la escena
	 * en un nuevo escenario. Si la conexión con el servidor ha fallado, se
	 * desactivan los elementos del menú y el botón "Generar PDF". Si la conexión se
	 * ha establecido correctamente, se activan los elementos del menú y el botón
	 * "Generar PDF". Obtiene la dirección del servidor y la muestra en la etiqueta
	 * correspondiente. Vincula la propiedad de la lista de archivos del servidor
	 * con la tabla de vista de archivos.
	 * 
	 * @throws IOException si ocurre un error de entrada/salida durante la carga del
	 *                     archivo "secondary.fxml".
	 */
	@FXML
	private void onMenuConectar() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
		Parent root = loader.load();
		SecondaryController controlador = loader.getController();

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Iniciar Conexión");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
		stage.close();
		if (sc.getConected() == false) {
			itemDesconectar.setDisable(true);
			itemConectar.setDisable(false);
			botonGenerarPDF.setDisable(true);
		} else {
			itemDesconectar.setDisable(false);
			itemConectar.setDisable(true);
			botonGenerarPDF.setDisable(false);
		}
		String dir = controlador.getDirec();
		labelDirectorio.setText(dir);
		System.out.println(sc.getConected());
		try {
			tableViewFichero.itemsProperty().bind(sc.lista());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Maneja el evento de clic en la tabla de archivos y directorios. Si se hace
	 * clic una vez no hace nada, pero si se hace doble clic y hay un elemento
	 * seleccionado, comprueba si es un directorio o enlace y cambia el directorio
	 * actual del cliente FTP a ese directorio. Si es un archivo, se abre un diálogo
	 * de guardado para descargarlo en el equipo local.
	 * 
	 * @param e El evento del mouse que activó el método.
	 * @throws IOException Si hay un error al descargar el archivo.
	 */
	@FXML
	private void onTablaMouseClicked(MouseEvent e) throws IOException {
		System.out.println("-------------------------");
		// si se ha pulsado dos veces y hay un elemento seleccionado en la tabla
		if (e.getClickCount() == 1) {
			System.out.println("nada");
		} else if (e.getClickCount() == 2 && tableViewFichero.getSelectionModel().getSelectedItem() != null
				&& tableViewFichero.getSelectionModel().getSelectedItem().getTipoFichero().toString() == "Directorio"
				|| tableViewFichero.getSelectionModel().getSelectedItem().getTipoFichero().toString() == "Enlace") {

			sc.cambiarDirectorio(tableViewFichero.getSelectionModel().getSelectedItem().getNombreFichero());
			System.out.println("/////////-"
					+ tableViewFichero.getSelectionModel().getSelectedItem().getNombreFichero().toString());
			System.out.println("-------------------------" + sc.getDirec());
			labelDirectorio.setText(sc.getDirec());
			tableViewFichero.getItems().clear();
			tableViewFichero.itemsProperty().bind(sc.lista());
		}
	}

	/**
	 * Desconecta el usuario de la sesión actual del servidor. Si la desconexión es
	 * exitosa, se limpia la tabla y se deshabilitan los botones correspondientes.
	 * Si no se puede desconectar, se muestra un mensaje de error en la consola.
	 * 
	 * @throws IOException si ocurre un error durante la desconexión
	 */
	@FXML
	private void onMenuDesconectar() throws IOException {
		try {
			System.out.println(sc.lista());
			sc.desconectar();
			tableViewFichero.getItems().clear();
			labelDirectorio.setText("");
			if (sc.getConected() == false) {
				itemDesconectar.setDisable(true);
				itemConectar.setDisable(false);
				botonGenerarPDF.setDisable(true);
			} else {
				itemDesconectar.setDisable(false);
				itemConectar.setDisable(true);
				botonGenerarPDF.setDisable(false);
			}
			System.out.println(sc.getConected());
			App.info("Desconexión", "Se ha cerrado conexión con éxito");

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	@FXML
	private void onClickDescargar() throws IOException {
		String descarga = tableViewFichero.getSelectionModel().getSelectedItem().getNombreFichero();
		System.out.println(descarga);
		if (descarga == null || descarga == "") {
			System.out.println("nadaaaaaaaaaaa");
		} else if (tableViewFichero.getSelectionModel().getSelectedItem().getTipoFichero() == "Fichero"){
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Guardar archivo");
			// Configurar los filtros de extensión
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Todos los archivos", "*.*"));
			File archivoSeleccionado = fileChooser.showSaveDialog(null);
			FileOutputStream flujo = new FileOutputStream(archivoSeleccionado);
			sc.descargar(descarga, flujo);
			App.info("Exito","El fichero ha sido descargado.");
		}else {
			App.error("Debes selecionar un fichero no un directorio ni enlace.");
		}
	}

	/**
	 * Genera un nuevo fichero pdf con el contenido del tableView en ese momento.
	 */
	@FXML
	private void onBotonGenerarPDF() throws IOException {
		System.out.println("generarPDF");
		Document document = new Document();
		String path = new File(".").getCanonicalPath();
		path = path + "/releases";
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy-hh-mm");
		String fechaFormateada = formato.format(fechaActual);
		try {
        	
        	String FILE_NAME = path + "/RutaActural-Generado-"+fechaFormateada+".pdf";
        	
            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
 
            document.open();
       
            Paragraph rutaText = new Paragraph();
            rutaText.add(sc.getDirec());
            rutaText.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(rutaText);
            
            PdfPTable table = new PdfPTable(3); // 3 columns.
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table
            
            //Set Column widths
            float[] columnWidths = {1f, 1f, 1f};
            table.setWidths(columnWidths);
            
            PdfPCell cell1 = new PdfPCell(new Paragraph("Nombre"));
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
     
            PdfPCell cell2 = new PdfPCell(new Paragraph("Tamaño"));
            cell2.setPaddingLeft(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
     
            PdfPCell cell3 = new PdfPCell(new Paragraph("Tipo"));
            cell3.setPaddingLeft(10);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
           
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
           
            ListProperty<FTP> a = sc.lista();
            System.out.println(sc.lista());
            PdfPCell cell = new PdfPCell();
            
            for (int i = 0; i < a.size(); i++) {
            	cell = new PdfPCell(new Paragraph(String.valueOf(a.get(i).getNombreFichero().toString())));
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(String.valueOf(a.get(i).getTamanoFichero())));
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(String.valueOf(a.get(i).getTipoFichero().toString())));
                table.addCell(cell);
            }
            
            document.add(table);
            document.close();
 
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		}

	}

}
