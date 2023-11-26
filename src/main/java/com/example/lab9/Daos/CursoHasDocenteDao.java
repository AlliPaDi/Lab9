package com.example.lab9.Daos;

import com.example.lab9.Beans.Curso;
import com.example.lab9.Beans.CursoHasDocente;
import com.example.lab9.Beans.Facultad;
import com.example.lab9.Beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CursoHasDocenteDao extends DaoBase{
    public ArrayList<CursoHasDocente> listarDocentes(Usuario usuarioLogueado){
        ArrayList<CursoHasDocente> listaDocentes = new ArrayList<>();

        String sql = "SELECT * FROM usuario u\n" +
                "LEFT JOIN curso_has_docente cd ON u.idusuario = cd.iddocente\n" +
                "LEFT JOIN curso c ON c.idcurso = cd.idcurso\n" +
                "LEFT JOIN facultad_has_decano fd ON  fd.idfacultad = c.idfacultad \n" +
                "WHERE u.idrol = 4 \n" +
                "AND (cd.idcurso IS NULL OR fd.iddecano = ?);";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioLogueado.getUsuarioId());

            try(ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    CursoHasDocente cursoDoc = new CursoHasDocente();

                    Usuario docente = new Usuario();
                    docente.setNombre(rs.getString("u.nombre"));
                    docente.setCorreo(rs.getString("u.correo"));
                    docente.setFechaRegistro(rs.getString("u.fecha_registro"));
                    docente.setFechaEdicion(rs.getString("u.fecha_edicion"));
                    cursoDoc.setDocente(docente);

                    Curso curso = new Curso();
                    curso.setNombreCurso(rs.getString("c.nombre"));
                    cursoDoc.setCurso(curso);



                    listaDocentes.add(cursoDoc);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaDocentes;
    }
}
