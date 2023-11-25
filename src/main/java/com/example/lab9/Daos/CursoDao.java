package com.example.lab9.Daos;

import com.example.lab9.Beans.Curso;
import com.example.lab9.Beans.CursoHasDocente;
import com.example.lab9.Beans.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CursoDao extends DaoBase{
    public ArrayList<CursoHasDocente> listarCursos(){
        ArrayList<CursoHasDocente> listaCursos = new ArrayList<>();

        String sql = "SELECT * FROM curso_has_docente";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CursoHasDocente cursoDoc= new CursoHasDocente();

                Curso curso = new Curso();
                curso.setCursoId(rs.getInt(1));

                Usuario docente = new Usuario();
                docente.setUsuarioId(rs.getInt(2));

                listaCursos.add(cursoDoc);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        return listaCursos;

    }
}
