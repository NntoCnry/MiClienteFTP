module dad.recup.miclienteftp {
    requires transitive javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;

    opens dad.recup.miclienteftp to javafx.fxml;
    exports dad.recup.miclienteftp;
}
