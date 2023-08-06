package datos;

import dominio.Usuario;
import java.sql.*;
import java.util.*;

public class UsuarioDao {

    private final String sql_insertar = "INSERT INTO CLIENTE(nombre,apellido,email,telefono,saldo) VALUES (?,?,?,?,?)";
    private final String sql_listar = "SELECT * FROM CLIENTE ";
    private final String sql_actualizar = "UPDATE cliente SET nombre = ? , apellido = ?, email = ?, telefono = ?, saldo = ? where id_cliente = ?";
    private final String sql_eliminar = "DELETE FROM cliente where id_cliente = ?";
    private String mensajeError = "";

    public String getMensajeError() {
        return this.mensajeError;
    }

    public int insertar(Usuario usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        int exito = 0;
        try {
            conn = Conexion.getConnection();
            System.out.println(sql_insertar);
            ps = conn.prepareStatement(sql_insertar);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getTelefono());
            ps.setDouble(5, usuario.getSueldo());
            exito = ps.executeUpdate();
        } catch (SQLException ex) {
            this.mensajeError = "Error Insersion de datos";
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return exito;
    }

    public List<Usuario> listar() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = null;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql_listar);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_Cliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                double sueldo = rs.getDouble("saldo");
                usuario = new Usuario(id, nombre, apellido, email, telefono, sueldo);
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            this.mensajeError = "Error listado de datos";
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return usuarios;
    }

    public List<Usuario> Buscar(Usuario usuario1) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = null;
        String buscar = sql_listar;
        int id_cliente = usuario1.getIdCliente();
        String nombre = usuario1.getNombre();
        String apellido = usuario1.getApellido();
        String email = usuario1.getEmail();
        String telefono = usuario1.getTelefono();
        Double sueldo = usuario1.getSueldo();

        int anterior = 0;
        int cont = 0;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(buscar);
            if (id_cliente > 0 || !nombre.equals("") || !apellido.equals("") || !email.equals("") || !telefono.equals("") || sueldo>-1) {
                buscar = buscar + " WHERE ";
                ArrayList lis = new ArrayList();
                if (id_cliente > 0) {
                    buscar = buscar.concat("id_cliente = ? ");
                    anterior = 1;
                    cont++;
                    lis.add(id_cliente);
                }
                if (!nombre.equals("")) {
                    if (anterior == 1) {
                        buscar = buscar.concat("and nombre = ? ");
                        cont++;
                        lis.add(nombre);
                    } else {
                        buscar = buscar.concat("nombre = ? ");
                        anterior = 1;
                        cont++;
                        lis.add(nombre);
                    }

                }
                if (!apellido.equals("")) {
                    if (anterior == 1) {
                        buscar = buscar.concat("and apellido = ? ");
                        cont++;
                        lis.add(apellido);
                    } else {
                        buscar = buscar.concat("apellido = ? ");
                        anterior = 1;
                        cont++;
                        lis.add(apellido);
                    }
                }
                if (!email.equals("")) {
                    if (anterior == 1) {
                        buscar = buscar.concat("and email = ? ");
                        cont++;
                        lis.add(email);
                    } else {
                        buscar = buscar.concat("email = ? ");
                        anterior = 1;
                        cont++;
                        lis.add(email);
                    }
                }
                if (!telefono.equals("")) {
                    if (anterior == 1) {
                        buscar = buscar.concat("and telefono = ? ");
                        cont++;
                        lis.add(telefono);
                    } else {
                        buscar = buscar.concat("telefono = ? ");
                        anterior = 1;
                        cont++;
                        lis.add(telefono);
                    }
                }
                if (sueldo>-1) {
                    if (anterior == 1) {
                        buscar = buscar.concat("and saldo = ? ");
                        cont++;
                        lis.add(sueldo);
                    } else {
                        buscar = buscar.concat("saldo = ? ");
                        cont++;
                        lis.add(sueldo);
                    }
                }
            ps = conn.prepareStatement(buscar);
                for (int i = 0; i < lis.size(); i++) {
                    if (lis.get(i) instanceof Double) {
                        ps.setDouble(i+1, (double) lis.get(i));
                    } else if (lis.get(i) instanceof String) {
                        ps.setString(i+1, (String) lis.get(i));
                    } else if (lis.get(i) instanceof Integer) {
                        ps.setInt(i+1, (int) lis.get(i));
                    }
                }
            }

            ps.executeQuery();
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_Cliente");
                String nombre1 = rs.getString("nombre");
                String apellido1 = rs.getString("apellido");
                String email1 = rs.getString("email");
                String telefono1 = rs.getString("telefono");
                double sueldo1 = rs.getDouble("saldo");
                usuario = new Usuario(id, nombre1, apellido1, email1, telefono1, sueldo1);
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            this.mensajeError = "Error Busqueda de datos de datos";
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return usuarios;
    }

    public int actualizar(Usuario usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        int exito = 0;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql_actualizar);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getTelefono());
            ps.setDouble(5, usuario.getSueldo());
            ps.setInt(6, usuario.getIdCliente());
            exito = ps.executeUpdate();
        } catch (SQLException ex) {
            this.mensajeError = "Error actualizacion de datos";
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return exito;
    }

    public int eliminar(Usuario usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        int exito = 0;
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql_eliminar);
            ps.setInt(1, usuario.getIdCliente());
            exito = ps.executeUpdate();
        } catch (SQLException ex) {
            this.mensajeError = "Error eliminar de datos";
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return exito;
    }

}
