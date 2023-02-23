package dad.miclienteftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
/**
 * La clase Main contiene el metodo principal que se conecta a un servidor FTP, recupera una lista de archivos y directorios,
 * imprime sus nombres, tamanos y tipos, y descarga un archivo desde el servidor.
 * @author Francisco yeray gomez carrion
 */
public class Main {
	
	/**
	 * Un mapa que asocia constantes enteras que representan los tipos de archivos con sus nombres correspondientes.
	 * @author Francisco yeray gomez carrion
	 */
	@SuppressWarnings("serial")
	public static final Map<Integer, String> FILE_TYPE = new HashMap<Integer, String>() {{
		put(FTPFile.DIRECTORY_TYPE, "Directorio");
		put(FTPFile.FILE_TYPE, "Fichero");
		put(FTPFile.SYMBOLIC_LINK_TYPE, "Enlace");
	}};
	
	/**
	 * Este metodo es el punto de entrada del programa. Se conecta a un servidor FTP, accede a un directorio,
	 * lista los archivos del directorio y descarga un archivo del servidor.
	 * @param args los argumentos de la linea de comandos (no se utilizan en este programa)
	 */
	public static void main(String [] args) {
		try {
			
			// instanciar el cliente FTP
			FTPClient cliente = new FTPClient();
			
			// conectar con el servidor FTP
			cliente.connect("ftp.rediris.es", 21);
			
			// iniciar sesión anónima (login)
			cliente.login("", "");
			
			// cambiar el directorio actual en el servidor
			cliente.changeWorkingDirectory("/debian/dists");

			// recuperar el nombre del directorio actual
			String directorioActual = cliente.printWorkingDirectory();
			System.out.println("Directorio actual: " + directorioActual);

			// recuperar un listado de los ficheros y directorios del directorio actual del servidor
			FTPFile [] ficheros = cliente.listFiles();
			
			// recorrer el listado de archivos recuperados
			System.out.format("+------------------------+%n");
			System.out.format("| Archivos del servidor: |%n");
			System.out.format("+------------------------+-----------------+----------------+-----------------+%n");
			System.out.format("| Nombre                                   | Tamaño (bytes) | Tipo            |%n");
			System.out.format("+------------------------------------------+----------------+-----------------+%n");
			Arrays.stream(ficheros)
				.forEach(fichero -> {
				    System.out.format("| %-40s | %-14d | %-15s |%n", fichero.getName(), fichero.getSize(), FILE_TYPE.get(fichero.getType()));
				});
			System.out.format("+------------------------------------------+----------------+-----------------+%n");
			
			// cambiar el directorio padre en el servidor
			cliente.changeWorkingDirectory("..");

			// descargar un fichero
			File descarga = new File("welcome.msg");
			FileOutputStream flujo = new FileOutputStream(descarga);
			cliente.retrieveFile("welcome.msg", flujo);
			flujo.flush();
			flujo.close();
			
			// desconectar del servidor
			cliente.disconnect();
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}