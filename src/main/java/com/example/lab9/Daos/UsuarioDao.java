package com.example.lab9.Daos;

import com.example.lab9.Beans.Rol;
import com.example.lab9.Beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao extends DaoBase{
    public boolean validarUsuarioPassword(String email, String password){
        String sql = "SELECT * FROM usuario WHERE correo = ? and password = ?";
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
        String sql = "SELECT * FROM usuario WHERE correo = ?";
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
        usuario.setPassword(rs.getString(4));

        Rol rol = new Rol();
        rol.setRolId(rs.getInt(10));
        rol.setNombreRol(rs.getString(11));
        usuario.setRol(rol);

    }
}
