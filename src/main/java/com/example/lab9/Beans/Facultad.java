package com.example.lab9.Beans;

public class Facultad{
    private int facultadId;
    private String nombreFacu;
    private Universidad universidad;
    private String fechaRegistro;
    private String fechaEdicion;

    public int getFacultadId() {
        return facultadId;
    }

    public void setFacultadId(int facultadId) {
        this.facultadId = facultadId;
    }

    public String getNombreFacu() {
        return nombreFacu;
    }

    public void setNombreFacu(String nombreFacu) {
        this.nombreFacu = nombreFacu;
    }

    public Universidad getUniversidad() {
        return universidad;
    }

    public void setUniversidad(Universidad universidad) {
        this.universidad = universidad;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(String fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }
}
