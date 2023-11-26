package com.example.lab9.Daos;

import com.example.lab9.Beans.Curso;
import com.example.lab9.Beans.CursoHasDocente;
import com.example.lab9.Beans.Facultad;
import com.example.lab9.Beans.Usuario;
import java.sql.*;
import java.util.ArrayList;

public class CursoDao extends DaoBase{

    public ArrayList<CursoHasDocente> listarCursos(Usuario usuarioLogueado){
        ArrayList<CursoHasDocente> listaCursos = new ArrayList<>();

        String sql = "SELECT * FROM curso_has_docente cd \n" +
                "LEFT JOIN curso c ON (c.idcurso = cd.idcurso)\n" +
                "LEFT JOIN usuario u ON (u.idusuario = cd.iddocente) \n" +
                "LEFT JOIN facultad f ON(f.idfacultad = c.idfacultad)\n" +
                "LEFT JOIN facultad_has_decano fd ON(fd.idfacultad = f.idfacultad)\n" +
                "WHERE fd.iddecano = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioLogueado.getUsuarioId());

            try(ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    CursoHasDocente cursoDoc = new CursoHasDocente();

                    Curso curso = new Curso();
                    curso.setCursoId(rs.getInt("c.idcurso"));
                    curso.setCodigo(rs.getString("c.codigo"));
                    curso.setNombreCurso(rs.getString("c.nombre"));
                    curso.setFechaRegistro(rs.getString("c.fecha_registro"));
                    curso.setFechaEdicion(rs.getString("c.fecha_edicion"));

                    Facultad facultad = new Facultad();
                    facultad.setFacultadId(rs.getInt("f.idfacultad"));
                    facultad.setNombreFacu(rs.getString("f.nombre"));
                    curso.setFacultad(facultad);
                    cursoDoc.setCurso(curso);

                    Usuario docente = new Usuario();
                    docente.setUsuarioId(rs.getInt("u.idusuario"));
                    docente.setNombre(rs.getString("u.nombre"));
                    cursoDoc.setDocente(docente);

                    listaCursos.add(cursoDoc);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaCursos;
    }

    public void guardarCurso(CursoHasDocente cursoHasDocente){
        String CursoSql = "INSERT INTO curso(idcurso, codigo, nombre, idfacultad, fecha_registro, fecha_edicion) " +
                "VALUES (?, ?, ?, ?, NOW(), NOW())";

        String CursoDocenteSql =  "INSERT INTO curso_has_docente(idcurso, iddocente) VALUES (?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmtCurso = conn.prepareStatement(CursoSql);
             PreparedStatement pstmtCursoDoc = conn.prepareStatement(CursoDocenteSql)) {

            pstmtCurso.setInt(1,cursoHasDocente.getCurso().getCursoId());
            pstmtCurso.setString(2,cursoHasDocente.getCurso().getCodigo());
            pstmtCurso.setString(3,cursoHasDocente.getCurso().getNombreCurso());
            pstmtCurso.setInt(4,cursoHasDocente.getCurso().getFacultad().getFacultadId());
            pstmtCurso.executeUpdate();

            pstmtCursoDoc.setInt(1,cursoHasDocente.getCurso().getCursoId());
            pstmtCursoDoc.setInt(2,cursoHasDocente.getDocente().getUsuarioId());

            pstmtCursoDoc.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public int obtenerProxIdCurso(){
        String sql = "SELECT MAX(idcurso) + 1 AS idProxCurso FROM curso";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("idProxCurso");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int obtenerFacuUsuarioLogueado(Usuario usuarioLogueado){
        String sql = "SELECT * FROM facultad_has_decano fd " +
                "LEFT JOIN usuario u ON (fd.iddecano = u.idusuario) " +
                "WHERE idusuario = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,usuarioLogueado.getUsuarioId());

            try(ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    return rs.getInt("idfacultad");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public Curso obtenerCurso(int  cursoId){
        Curso curso = null;

        String sql = "SELECT * FROM curso WHERE idcurso = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cursoId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    curso = new Curso();
                    curso.setCursoId(rs.getInt("idcurso"));
                    curso.setNombreCurso(rs.getString("nombre"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return curso;
    }

    public void actualizarCurso(Curso curso){
        String sql = "UPDATE curso SET nombre = ?, fecha_edicion = NOW() WHERE idcurso = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,curso.getNombreCurso());
            pstmt.setInt(2,curso.getCursoId());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void borrarCurso(int cursoId){
        String sqlKey = "DELETE FROM curso_has_docente WHERE idcurso = ? ";
        String sqlCurso = "DELETE FROM curso WHERE idcurso = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmtKey = conn.prepareStatement(sqlKey);
             PreparedStatement pstmtCurso = conn.prepareStatement(sqlCurso)) {

            pstmtKey.setInt(1, cursoId);
            pstmtKey.executeUpdate();

            pstmtCurso.setInt(1, cursoId);
            pstmtCurso.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
