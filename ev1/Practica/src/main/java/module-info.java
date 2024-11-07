module com.jjla2425.da.practica {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens com.jjla2425.da.practica to javafx.fxml, org.hibernate.orm.core;
    exports com.jjla2425.da.practica;
}