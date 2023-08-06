/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crudclientes;

import datos.UsuarioDao;
import dominio.Usuario;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import tabla.TablaClientes;

/**
 *
 * @author Chayan
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtSaldo;
    @FXML
    private TextField txtId;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<Usuario> tblUsuario;
    @FXML
    private TableColumn<Usuario, Integer> colId;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, String> colApellido;
    @FXML
    private TableColumn<Usuario, String> colEmail;
    @FXML
    private TableColumn<Usuario, String> colTelefono;
    @FXML
    private TableColumn<Usuario, Double> colSueldo;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnNuevo;

    void buscar() {
        if (validarFormularioBuscar()) {
            ArrayList lis = new ArrayList();
            int id = 0;
            double saldo = -1;
            String id1 = txtId.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String email = txtEmail.getText();
            String telefono = txtTelefono.getText();
            String saldo1 = txtSaldo.getText();

            System.out.println(id1);
            if (!id1.equals("")) {
                id = Integer.parseInt(id1);
            }
            if (!saldo1.equals("")) {
                saldo = Double.parseDouble(saldo1);
            }
            Usuario usuario1 = new Usuario(id, nombre, apellido, email, telefono, saldo);
            System.out.println(usuario1);
            ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
            UsuarioDao usuarioDao = new UsuarioDao();
            List<Usuario> usuarios = usuarioDao.Buscar(usuario1);
            for (int i = 0; i < usuarios.size(); i++) {
                listaUsuarios.add(usuarios.get(i));
            }
            List<TableColumn<Usuario, ?>> lista = TablaClientes.armarLista(colId, colNombre, colApellido, colEmail, colTelefono, colSueldo);
            TablaClientes.crear(tblUsuario, listaUsuarios, lista);
        }
    }

    void listar() {
        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
        UsuarioDao usuarioDao = new UsuarioDao();
        ArrayList<Usuario> usuarios = (ArrayList<Usuario>) usuarioDao.listar();
        for (int i = 0; i < usuarios.size(); i++) {
            listaUsuarios.add(usuarios.get(i));
        }
        List<TableColumn<Usuario, ?>> lista = TablaClientes.armarLista(colId, colNombre, colApellido, colEmail, colTelefono, colSueldo);
        TablaClientes.crear(tblUsuario, listaUsuarios, lista);

    }

    void seleccionar() {
        tblUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue == newValue) {
                // oldValue y newValue son iguales, no se seleccionó ningún registro o es la selección inicial
                return; // Salimos del Listener para evitar ejecutar el bloque innecesariamente
            }
            if (newValue != null) {
                txtId.setDisable(true);
                txtId.setText(newValue.getIdCliente() + "");
                txtNombre.setText(newValue.getNombre());
                txtApellido.setText(newValue.getApellido());
                txtEmail.setText(newValue.getEmail());
                txtTelefono.setText(newValue.getTelefono());
                txtSaldo.setText(newValue.getSueldo() + "");
            } else {
                txtId.setDisable(true);
                txtId.clear();
                txtNombre.clear();
                txtApellido.clear();
                txtEmail.clear();
                txtTelefono.clear();
                txtSaldo.clear();
            }

        });
    }

    int actualizar() {
        int res = 0;
        if (validarFormulario()) {
             opcion = JOptionPane.showOptionDialog(
                    null,
                    "¿Deseas continuar guardar Cambios?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Sí", "No"},
                    "Sí"
            );
            if(opcion == JOptionPane.YES_OPTION){
              int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String email = txtEmail.getText();
            String telefono = txtTelefono.getText();
            Double saldo = Double.parseDouble(txtSaldo.getText());

            if (nombre == null || apellido == null || email == null || telefono == null) {
                return 0;
            }
            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario usuario = new Usuario(id, nombre, apellido, email, telefono, saldo);
            res = usuarioDao.actualizar(usuario);
            return res;
            }else{
                return res;
            }
          
        }
        return res;
    }

    int btnRegistrar() {
        int res = 0;
        if (validarFormulario()) {
            System.out.println("Cick Registrar");
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String email = txtEmail.getText();
            String telefono = txtTelefono.getText();
            Double saldo = Double.parseDouble(txtSaldo.getText());

            if (nombre == null || apellido == null || email == null || telefono == null) {
                return 0;
            }
            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario usuario = new Usuario(nombre, apellido, email, telefono, saldo);
            res = usuarioDao.insertar(usuario);
            return res;
        }
        return res;
    }

    int eliminar() {
        int res = 0;
        if (validarFormulario()) {
            opcion = JOptionPane.showOptionDialog(
                    null,
                    "¿Deseas continuar guardar Cambios?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Sí", "No"},
                    "Sí"
            );
            
            if(opcion == JOptionPane.YES_OPTION){
             int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String email = txtEmail.getText();
            String telefono = txtTelefono.getText();
            Double saldo = Double.parseDouble(txtSaldo.getText());

            if (nombre == null || apellido == null || email == null || telefono == null) {
                return 0;
            }
            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario usuario = new Usuario(id, nombre, apellido, email, telefono, saldo);
            res = usuarioDao.eliminar(usuario);
            return res;
            }else{
                return res;
            }
        }
        return res;
    }

    void ReiniciarFormulario() {
        txtNombre.setStyle("-fx-background-color: white");
        txtApellido.setStyle("-fx-background-color: white");
        txtEmail.setStyle("-fx-background-color: white");
        txtTelefono.setStyle("-fx-background-color: white");
        txtSaldo.setStyle("-fx-background-color: white");

        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtSaldo.setText("");
    }

    void color() {
        txtNombre.setStyle("-fx-background-color: white");
        txtApellido.setStyle("-fx-background-color: white");
        txtEmail.setStyle("-fx-background-color: white");
        txtTelefono.setStyle("-fx-background-color: white");
        txtSaldo.setStyle("-fx-background-color: white");
    }

    boolean validarFormularioBuscar() {
        String nombreApellidoRegex = "^[a-zA-Z]*(?:\\s[a-zA-Z]*){0,2}$";
        String correoRegex = "^(?:[A-Za-z0-9._%+-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,})?$";
        String sueldoRegex = "^(?:\\d{1,6}(\\.\\d{1,2})?)?$";
        String telefonoRegex = "^(\\d{2}-\\d{8}|\\d{10})?$";
        String idRegex = "^(?:[1-9]\\d{0,3})?$";

        boolean nombreValido = Pattern.matches(nombreApellidoRegex, txtNombre.getText());
        boolean apellidoValido = Pattern.matches(nombreApellidoRegex, txtApellido.getText());
        boolean correoValido = Pattern.matches(correoRegex, txtEmail.getText());
        boolean sueldoValido = Pattern.matches(sueldoRegex, txtSaldo.getText());
        boolean telefonoValido = Pattern.matches(telefonoRegex, txtTelefono.getText());
        boolean idValido = Pattern.matches(idRegex, txtId.getText());

        if (!idValido) {
            txtId.setStyle("-fx-background-color: red");
        } else {
            txtId.setStyle("-fx-background-color: white");
        }
        if (!nombreValido) {
            txtNombre.setStyle("-fx-background-color: red");
        } else {
            txtNombre.setStyle("-fx-background-color: white");
        }
        if (!apellidoValido) {
            txtApellido.setStyle("-fx-background-color: red");
        } else {
            txtApellido.setStyle("-fx-background-color: white");
        }
        if (!correoValido) {
            txtEmail.setStyle("-fx-background-color: red");
        } else {
            txtEmail.setStyle("-fx-background-color: white");
        }
        if (!telefonoValido) {
            txtTelefono.setStyle("-fx-background-color: red");
        } else {
            txtTelefono.setStyle("-fx-background-color: white");
        }
        if (!sueldoValido) {
            txtSaldo.setStyle("-fx-background-color: red");
        } else {
            txtSaldo.setStyle("-fx-background-color: white");
        }

        return nombreValido && apellidoValido && correoValido && telefonoValido && sueldoValido && idValido;
    }

    boolean validarFormulario() {
        String nombreApellidoRegex = "^[a-zA-Z]+(?:\\s[a-zA-Z]+){0,2}$";
        String correoRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        String sueldoRegex = "^(?!0\\d)(?:\\d{1,6}(\\.\\d{1,2})?)$";
        String telefonoRegex = "^(\\d{2}-\\d{8}|\\d{10})$";

        boolean nombreValido = Pattern.matches(nombreApellidoRegex, txtNombre.getText());
        boolean apellidoValido = Pattern.matches(nombreApellidoRegex, txtApellido.getText());
        boolean correoValido = Pattern.matches(correoRegex, txtEmail.getText());
        boolean sueldoValido = Pattern.matches(sueldoRegex, txtSaldo.getText());
        boolean telefonoValido = Pattern.matches(telefonoRegex, txtTelefono.getText());

        if (!nombreValido) {
            txtNombre.setStyle("-fx-background-color: red");
        } else {
            txtNombre.setStyle("-fx-background-color: white");
        }
        if (!apellidoValido) {
            txtApellido.setStyle("-fx-background-color: red");
        } else {
            txtApellido.setStyle("-fx-background-color: white");
        }
        if (!correoValido) {
            txtEmail.setStyle("-fx-background-color: red");
        } else {
            txtEmail.setStyle("-fx-background-color: white");
        }
        if (!telefonoValido) {
            txtTelefono.setStyle("-fx-background-color: red");
        } else {
            txtTelefono.setStyle("-fx-background-color: white");
        }
        if (!sueldoValido) {
            txtSaldo.setStyle("-fx-background-color: red");
        } else {
            txtSaldo.setStyle("-fx-background-color: white");
        }

        return nombreValido && apellidoValido && correoValido && telefonoValido && sueldoValido;
    }
    int opcion = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblUsuario.getSelectionModel().setCellSelectionEnabled(true);
        btnBuscar.setDisable(false);
        btnRegistrar.setDisable(false);
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
        listar();

        btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                buscar();
            }
        });

        tblUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtId.setDisable(true);
                txtId.setText(newValue.getIdCliente() + "");
                txtNombre.setText(newValue.getNombre());
                txtApellido.setText(newValue.getApellido());
                txtEmail.setText(newValue.getEmail());
                txtTelefono.setText(newValue.getTelefono());
                txtSaldo.setText(newValue.getSueldo() + "");

                btnBuscar.setDisable(true);
                btnRegistrar.setDisable(true);
                btnEliminar.setDisable(false);
                btnActualizar.setDisable(false);
            } else {
                txtId.setDisable(true);
                txtId.clear();
                txtNombre.clear();
                txtApellido.clear();
                txtEmail.clear();
                txtTelefono.clear();
                txtSaldo.clear();

                btnBuscar.setDisable(false);
                btnRegistrar.setDisable(false);
                btnEliminar.setDisable(true);
                btnActualizar.setDisable(true);
            }
        });

        btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (eliminar() == 1) {
                    ReiniciarFormulario();
                    initialize(url, rb);
                }
                
            }
        });

        btnActualizar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (actualizar() == 1) {
                    ReiniciarFormulario();
                    initialize(url, rb);
                }
                
            }
        });
        btnRegistrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (btnRegistrar() == 1) {
                    ReiniciarFormulario();
                }
                initialize(url, rb);
            }
        });

        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        btnNuevo.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                color();
                initialize(url, rb);
            }
           
        });
    }

}
