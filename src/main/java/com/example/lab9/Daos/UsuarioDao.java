package com.example.lab9.Daos;

import com.example.lab9.Beans.Curso;
import com.example.lab9.Beans.CursoHasDocente;
import com.example.lab9.Beans.Rol;
import com.example.lab9.Beans.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDao extends DaoBase{
    public boolean validarUsuarioPasswordHashed(String email, String password){
        String sql = "SELECT * FROM usuario WHERE correo = ? and password = sha2(?,256)";
        boolean exito = false;

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    exito = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exito;
    }

    public Usuario obtenerUsuario(String email){
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario u LEFT JOIN rol r ON (r.idrol = u.idrol) WHERE correo = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    usuario = new Usuario();
                    fetchUsuarioData(usuario, rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usuario;
    }

    private void fetchUsuarioData(Usuario usuario, ResultSet rs) throws SQLException {
        usuario.setUsuarioId(rs.getInt(1));
        usuario.setNombre(rs.getString(2));
        usuario.setCorreo(rs.getString(3));
        /*usuario.setPassword(rs.getString(4));*/

        Rol rol = new Rol();
        rol.setRolId(rs.getInt(10));
        rol.setNombreRol(rs.getString(11));
        usuario.setRol(rol);

    }

    public ArrayList<Usuario> listarDocentes(){
        ArrayList<Usuario> listaDocentes = new ArrayList<>();

        String sql = "SELECT * FROM usuario u \n" +
                "WHERE idrol = 4\n" +
                "AND NOT EXISTS (SELECT 1 FROM curso_has_docente WHERE curso_has_docente.iddocente = u.idusuario);";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario docente = new Usuario();
                docente.setUsuarioId(rs.getInt(1));
                docente.setNombre(rs.getString(2));
                listaDocentes.add(docente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaDocentes;
    }

    public void guardarDocente(Usuario docente){
        String sql = "INSERT INTO usuario (idusuario, nombre, correo, password, idrol, cantidad_ingresos, fecha_registro, fecha_edicion)\n" +
                "VALUES (?,?,?, sha2(?,256), 4, 1, NOW(), NOW())";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,docente.getUsuarioId());
            pstmt.setString(2,docente.getNombre());
            pstmt.setString(3, docente.getCorreo());
            pstmt.setString(4, docente.getPassword());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    public int obtenerProxIdDocente(){
        String sql = "SELECT MAX(idusuario) + 1 AS idProxDocente FROM usuario";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("idProxDocente");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public Usuario obtenerDocente(int docId){
        Usuario docente = null;
        String sql = "SELECT * FROM usuario WHERE idusuario = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, docId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    docente = new Usuario();
                    docente.setUsuarioId(rs.getInt("idusuario"));
                    docente.setNombre(rs.getString("nombre"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return docente;
    }

    public void actualizarDocente(Usuario docente){
        String sql = "UPDATE usuario SET nombre = ?, fecha_edicion = NOW() WHERE idusuario = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,docente.getNombre());
            pstmt.setInt(2,docente.getUsuarioId());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void borrarDocente(int docId) throws SQLException {
        String sql = "DELETE FROM usuario WHERE idusuario= ? ";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, docId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
