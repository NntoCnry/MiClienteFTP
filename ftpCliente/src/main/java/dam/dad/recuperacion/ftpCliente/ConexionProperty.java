package dam.dad.recuperacion.ftpCliente;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Esta clase representa un conjunto de propiedades de conexión para un servidor
 * FTP, incluyendo el servidor, puerto, usuario y contraseña.
 * 
 * @author Francisco Yeray Gómez Carrión
 */
public class ConexionProperty {
	private StringProperty servidor = new SimpleStringProperty();
	private IntegerProperty puerto = new SimpleIntegerProperty();
	private StringProperty usuario = new SimpleStringProperty();
	private StringProperty contrasena = new SimpleStringProperty();

	/**
	 * Devuelve la propiedad servidor como una StringProperty.
	 * 
	 * @return la propiedad servidor como una StringProperty.
	 */
	public final StringProperty servidorProperty() {
		return this.servidor;
	}

	/**
	 * Devuelve el valor actual de servidor.
	 * 
	 * @return el valor actual de servidor.
	 */
	public final String getServidor() {
		return this.servidorProperty().get();
	}

	/**
	 * Establece el valor de la propiedad "servidor" con el valor especificado.
	 * 
	 * @param servidor El nuevo valor para la propiedad "servidor".
	 */
	public final void setServidor(final String servidor) {
		this.servidorProperty().set(servidor);
	}

	/**
	 * 
	 * Devuelve la propiedad puerto como una IntegerProperty.
	 * 
	 * @return la propiedad puerto como una IntegerProperty.
	 */
	public final IntegerProperty puertoProperty() {
		return this.puerto;
	}

	/**
	 * Devuelve el valor actual de puerto.
	 * 
	 * @return el valor actual de puerto
	 */
	public final int getPuerto() {
		return this.puertoProperty().get();
	}

	/**
	 * Establece el valor de la propiedad "puerto" con el valor especificado
	 * 
	 * @param puerto el nuevo valor del puerto
	 */
	public final void setPuerto(final int puerto) {
		this.puertoProperty().set(puerto);
	}

	/**
	 * Devuelve la propiedad usuario como una StringProperty.
	 * 
	 * @return la propiedad usuario como una StringProperty.
	 */
	public final StringProperty usuarioProperty() {
		return this.usuario;
	}

	/**
	 * Devuelve el valor actual de usuario.
	 * 
	 * @return el valor actual de usuario
	 */
	public final String getUsuario() {
		return this.usuarioProperty().get();
	}

	/**
	 * Establece el valor de la propiedad "usuario" con el valor especificado
	 * 
	 * @param puerto el nuevo valor del usuario
	 */
	public final void setUsuario(final String usuario) {
		this.usuarioProperty().set(usuario);
	}

	/**
	 * Devuelve la propiedad contrasena como una StringProperty.
	 * 
	 * @return la propiedad contrasena como una StringProperty.
	 */
	public final StringProperty contrasenaProperty() {
		return this.contrasena;
	}

	/**
	 * Devuelve el valor actual de contrasena.
	 * 
	 * @return el valor actual de contrasena
	 */
	public final String getContrasena() {
		return this.contrasenaProperty().get();
	}

	/**
	 * Establece el valor de la propiedad "Contrasena" con el valor especificado
	 * 
	 * @param puerto el nuevo valor del contrasena
	 */
	public final void setContrasena(final String contrasena) {
		this.contrasenaProperty().set(contrasena);
	}

}
