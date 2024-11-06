module com.jjla2425.da.practica {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;


    opens com.jjla2425.da.practica to javafx.fxml;
    exports com.jjla2425.da.practica;
}