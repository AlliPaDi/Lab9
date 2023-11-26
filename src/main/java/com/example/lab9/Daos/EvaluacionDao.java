package com.example.lab9.Daos;

import com.example.lab9.Beans.*;

import java.sql.*;
import java.util.ArrayList;

public class EvaluacionDao extends DaoBase{
    public ArrayList<Evaluaciones> listarEvaluaciones(Usuario usuarioLogueado){
        ArrayList<Evaluaciones> listaEvaluaciones = new ArrayList<>();

        String sql = "SELECT * FROM evaluaciones e\n" +
                "LEFT JOIN curso c ON c.idcurso = e.idcurso\n" +
                "LEFT JOIN curso_has_docente cd ON cd.idcurso = c.idcurso\n" +
                "WHERE cd.iddocente = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioLogueado.getUsuarioId());

            try(ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Evaluaciones evaluaciones = new Evaluaciones();


                    evaluaciones.setNombreEstudiante(rs.getString("nombre_estudiantes"));
                    evaluaciones.setCodigoEstudiante(rs.getString("codigo_estudiantes"));
                    evaluaciones.setCorreoEstudiante(rs.getString("correo_estudiantes"));
                    evaluaciones.setNota(rs.getInt("nota"));
                    evaluaciones.setFechaRegistro(rs.getString("e.fecha_registro"));
                    evaluaciones.setFechaEdicion(rs.getString("e.fecha_edicion"));

                    Curso curso = new Curso();
                    curso.setCursoId(rs.getInt("c.idcurso"));
                    curso.setNombreCurso(rs.getString("c.nombre"));

                    evaluaciones.setCurso(curso);

                    listaEvaluaciones.add(evaluaciones);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaEvaluaciones;
    }

    public int obtenerProxIdEva(){
        String sql = "SELECT MAX(idevaluaciones) + 1 AS idProxEva FROM evaluaciones";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("idProxEva");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void guardarEva(Evaluaciones evaluaciones){
        String sql = "INSERT INTO evaluaciones(idevaluaciones, nombre_estudiantes, codigo_estudiantes, correo_estudiantes, nota, idcurso, idsemestre, fecha_registro, fecha_edicion)\n" +
                "VALUES (?,?,?,?,?,?,1, NOW(), NOW())";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,evaluaciones.getEvaluacionesId());
            pstmt.setString(2,evaluaciones.getNombreEstudiante());
            pstmt.setString(3,evaluaciones.getCodigoEstudiante());
            pstmt.setString(4,evaluaciones.getCorreoEstudiante());
            pstmt.setInt(5,evaluaciones.getNota());
            pstmt.setInt(6,evaluaciones.getCurso().getCursoId());

            pstmt.executeUpdate();



        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public int obtenerCursoUsuarioLogueado(Usuario usuarioLogueado){
        String sql = "SELECT * FROM usuario u\n" +
                "LEFT JOIN curso_has_docente cd ON (cd.iddocente = u.idusuario) \n" +
                "WHERE idusuario = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,usuarioLogueado.getUsuarioId());

            try(ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    return rs.getInt("idcurso");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public Evaluaciones obtenerEva(int  evaId){
        Evaluaciones evaluaciones = null;

        String sql = "SELECT * FROM evaluaciones WHERE idevaluaciones= ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, evaId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    evaluaciones = new Evaluaciones();
                    evaluaciones.setEvaluacionesId(Integer.parseInt("idevaluaciones"));
                    evaluaciones.setNombreEstudiante("nombre_estudiantes");

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return evaluaciones;
    }

    public void actualizarEva(Evaluaciones evaluaciones){
        String sql = "UPDATE evaluaciones SET nombre_alumnos = ?,codigo_alumnos = ?,correo_alumnos = ?,nota = ?, fecha_edicion = NOW() WHERE idevaluaciones = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,evaluaciones.getNombreEstudiante());
            pstmt.setString(2,evaluaciones.getCodigoEstudiante());
            pstmt.setString(3,evaluaciones.getCorreoEstudiante());
            pstmt.setInt(4,evaluaciones.getNota());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void borrarEva(int evaId) throws SQLException{
        String sql = "DELETE FROM evaluaciones WHERE idevaluaciones = ? ";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, evaId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
