module dam.dad.recuperacion.ftpCliente {
    requires transitive javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires org.apache.commons.net;
	requires itextpdf;


    opens dam.dad.recuperacion.ftpCliente to javafx.fxml;
    exports dam.dad.recuperacion.ftpCliente;
}
