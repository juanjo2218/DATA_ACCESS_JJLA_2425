module com.jjla2425.da.practica {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens com.jjla2425.da.practica to javafx.fxml, org.hibernate.orm.core;
    exports com.jjla2425.da.practica;
    exports com.jjla2425.da.practica.Controllers;
    opens com.jjla2425.da.practica.Controllers to javafx.fxml, org.hibernate.orm.core;
    exports com.jjla2425.da.practica.DataBaseEntities;
    opens com.jjla2425.da.practica.DataBaseEntities to javafx.fxml, org.hibernate.orm.core;
    exports com.jjla2425.da.practica.DataBase;
    opens com.jjla2425.da.practica.DataBase to javafx.fxml, org.hibernate.orm.core;
}