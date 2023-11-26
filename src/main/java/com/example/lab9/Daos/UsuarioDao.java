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


    public Usuario obtenerDocente(int docId){
        Usuario docente = null;
        String sql = "SELECT * FROM usuario u LEFT JOIN rol r ON (r.idrol = u.idrol) WHERE correo = ?"; // REVISAR EL QUERY
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, docId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    docente = new Usuario();
                    fetchUsuarioData(docente, rs); //EVALUAR ESTA PARTE
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return docente;
    }
}
