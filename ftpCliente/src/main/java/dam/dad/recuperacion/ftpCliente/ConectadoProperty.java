package dam.dad.recuperacion.ftpCliente;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Esta clase representa una propiedad de conexión, que indica si la conexión
 * está activa o no.
 * 
 * @author Francisco Yeray Gómez Carrión
 */
public class ConectadoProperty {

	private BooleanProperty conectado = new SimpleBooleanProperty();

	/**
	 * Devuelve la propiedad booleana que indica si la conexión está activa o no.
	 * 
	 * @return La propiedad booleana que indica si la conexión está activa o no.
	 */
	public final BooleanProperty conectadoProperty() {
		return this.conectado;
	}

	/**
	 * Devuelve un valor booleano que indica si la conexión está activa o no.
	 * 
	 * @return Un valor booleano que indica si la conexión está activa o no.
	 */
	public final boolean isConectado() {
		return this.conectadoProperty().get();
	}

	/**
	 * Establece un valor booleano que indica si la conexión está activa o no.
	 * 
	 * @param conectado Un valor booleano que indica si la conexión está activa o
	 *                  no.
	 */
	public final void setConectado(final boolean conectado) {
		this.conectadoProperty().set(conectado);
	}

}
