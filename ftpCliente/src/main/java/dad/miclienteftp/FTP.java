package dad.miclienteftp;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.ftp.FTPFile;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase que representa un objeto FTP para la gestión de ficheros y directorios
 * en un servidor FTP remoto.
 * @author Francisco Yeray Gómez Carrión
 */
public class FTP {

	/**
	 * Mapa que contiene los tipos de fichero posibles y su descripción asociada.
	 */
	@SuppressWarnings("serial")
	public static final Map<Integer, String> FILE_TYPE = new HashMap<Integer, String>() {
		{
			put(FTPFile.DIRECTORY_TYPE, "Directorio");
			put(FTPFile.FILE_TYPE, "Fichero");
			put(FTPFile.SYMBOLIC_LINK_TYPE, "Enlace");
		}
	};

	private ObjectProperty<FTPFile> fichero = new SimpleObjectProperty<>();
	private StringProperty nombreFichero = new SimpleStringProperty();
	private LongProperty tamanoFichero = new SimpleLongProperty();
	private StringProperty tipoFichero = new SimpleStringProperty();

	/**
	 * Constructor de la clase FTP que inicializa los atributos a partir del objeto
	 * FTPFile proporcionado.
	 * @param file el objeto FTPFile a partir del cual se inicializan los atributos.
	 */
	public FTP(FTPFile file) {
		setNombreFichero(file.getName());
		setTamanoFichero(file.getSize());
		setTipoFichero(FILE_TYPE.get(file.getType()));

	}

	/**
	 * Devuelve la propiedad del fichero FTP.
	 * @return la propiedad del fichero FTP.
	 */
	public final ObjectProperty<FTPFile> ficheroProperty() {
		return this.fichero;
	}

	/**
	 * Devuelve el fichero FTP.
	 * @return el fichero FTP.
	 */
	public final FTPFile getFichero() {
		return this.ficheroProperty().get();
	}

	/**
	 * Establece el fichero FTP.
	 * @param fichero el fichero FTP a establecer.
	 */
	public final void setFichero(final FTPFile fichero) {
		this.ficheroProperty().set(fichero);
	}

	/**
	 * Devuelve la propiedad del nombre del fichero.
	 * @return la propiedad del nombre del fichero.
	 */
	public final StringProperty nombreFicheroProperty() {
		return this.nombreFichero;
	}

	/**
	 * Devuelve el nombre del fichero.
	 * @return el nombre del fichero.
	 */
	public final String getNombreFichero() {
		return this.nombreFicheroProperty().get();
	}

	/**
	 * Establece el nombre del fichero.
	 * @param nombreFile el nombre del fichero a establecer.
	 */
	public final void setNombreFichero(final String nombreFile) {
		this.nombreFicheroProperty().set(nombreFile);
	}

	/**
	 * Devuelve la propiedad del tamaño del fichero.
	 * @return la propiedad del tamaño del fichero.
	 */
	public final LongProperty tamanoFicheroProperty() {
		return this.tamanoFichero;
	}

	/**
	 * Devuelve el tamano del fichero.
	 * @return el tamano del fichero.
	 */
	public final long getTamanoFichero() {
		return this.tamanoFicheroProperty().get();
	}

	/**
	 * Establece el tamano del fichero.
	 * @param sizeFile el tamano del fichero a establecer.
	 */
	public final void setTamanoFichero(final long sizeFile) {
		this.tamanoFicheroProperty().set(sizeFile);
	}

	/**
	 * Devuelve la propiedad del tipo de fichero.
	 * @return la propiedad del tipo de fichero.
	 */
	public final StringProperty tipoFicheroProperty() {
		return this.tipoFichero;
	}

	/**
	 * Devuelve el tipo de fichero.
	 * @return el tipo de fichero.
	 */
	public final String getTipoFichero() {
		return this.tipoFicheroProperty().get();
	}

	/**
	 * Establece el tipo de fichero.
	 * @param tipeFile el tipo de fichero a establecer.
	 */
	public final void setTipoFichero(final String tipeFile) {
		this.tipoFicheroProperty().set(tipeFile);
	}
}
