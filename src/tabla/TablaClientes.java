package tabla;

import dominio.Usuario;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TablaClientes {
    
    @FXML
    private static TableColumn<Usuario, Integer> colId;
    @FXML
    private static TableColumn<Usuario, String> colNombre;
    @FXML
    private static TableColumn<Usuario, String> colApellido;
    @FXML
    private static TableColumn<Usuario, String> colEmail;
    @FXML
    private static TableColumn<Usuario, String> colTelefono;
    @FXML
    private static TableColumn<Usuario, Double> colSueldo;
    
    public static void reiniciarTabla(TableView<Usuario> tblUsuario, List<TableColumn<Usuario,?>> col){
          colId = (TableColumn<Usuario, Integer>) col.get(0);
        colNombre = (TableColumn<Usuario, String>) col.get(1);
        colApellido = (TableColumn<Usuario, String>) col.get(2);
        colEmail = (TableColumn<Usuario, String>) col.get(3);
        colTelefono = (TableColumn<Usuario, String>) col.get(4);
        colSueldo = (TableColumn<Usuario, Double>) col.get(5);
    }
    
    public static void crear(TableView<Usuario> tblUsuario,  ObservableList<Usuario> listaUsuarios,List<TableColumn<Usuario,?>> col){
        colId = (TableColumn<Usuario, Integer>) col.get(0);
        colNombre = (TableColumn<Usuario, String>) col.get(1);
        colApellido = (TableColumn<Usuario, String>) col.get(2);
        colEmail = (TableColumn<Usuario, String>) col.get(3);
        colTelefono = (TableColumn<Usuario, String>) col.get(4);
        colSueldo = (TableColumn<Usuario, Double>) col.get(5);
        tblUsuario.setItems(listaUsuarios);
        for(Usuario us: listaUsuarios){
            colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIdCliente()).asObject());
            colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
            colApellido.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getApellido()));
            colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
            colTelefono.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefono()));
            colSueldo.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSueldo()).asObject());
        }
    }
    
    public static List<TableColumn<Usuario,?>> armarLista(TableColumn<Usuario,?> ... lis){
        List<TableColumn<Usuario,?>> col = new ArrayList<>();
        col.add((TableColumn<Usuario, ?>) lis[0]);
        col.add((TableColumn<Usuario, ?>) lis[1]);
        col.add((TableColumn<Usuario, ?>) lis[2]);
        col.add((TableColumn<Usuario, ?>) lis[3]);
        col.add((TableColumn<Usuario, ?>) lis[4]);
        col.add((TableColumn<Usuario, ?>) lis[5]);
        
        return col;
    }
    
   
}
