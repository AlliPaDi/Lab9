package com.example.lab9.Daos;

import com.example.lab9.Beans.Curso;
import com.example.lab9.Beans.CursoHasDocente;
import com.example.lab9.Beans.Facultad;
import com.example.lab9.Beans.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CursoDao extends DaoBase{

    public ArrayList<CursoHasDocente> listarCursos(){
        ArrayList<CursoHasDocente> listaCursos = new ArrayList<>();

        String sql = "SELECT * FROM curso_has_docente cd LEFT JOIN curso c ON (c.idcurso = cd.idcurso) \n" +
                "LEFT JOIN usuario u ON (u.idusuario = cd.iddocente) \n" +
                "LEFT JOIN facultad f ON(f.idfacultad = c.idfacultad);";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CursoHasDocente cursoDoc= new CursoHasDocente();

                Curso curso = new Curso();
                curso.setCursoId(rs.getInt("c.idcurso"));
                curso.setCodigo(rs.getString("codigo"));
                curso.setNombreCurso(rs.getString("c.nombre"));
                curso.setFechaRegistro(rs.getString("c.fecha_registro"));
                curso.setFechaEdicion(rs.getString("c.fecha_edicion"));

                Facultad facultad = new Facultad();
                facultad.setFacultadId(rs.getInt("idfacultad"));
                facultad.setNombreFacu(rs.getString("f.nombre"));
                curso.setFacultad(facultad);
                cursoDoc.setCurso(curso);

                Usuario docente = new Usuario();
                docente.setUsuarioId(rs.getInt("u.idusuario"));
                docente.setNombre(rs.getString("u.nombre"));
                cursoDoc.setDocente(docente);

                listaCursos.add(cursoDoc);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaCursos;
    }
}
