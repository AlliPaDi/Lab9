package com.example.lab9.Beans;

public class Universidad{
    private int universidadId;
    private String nombreUniv;
    private String logoUrl;
    private Usuario administrador;
    private String fechaRegistro;
    private String fechaEdicion;

    public int getUniversidadId() {
        return universidadId;
    }

    public void setUniversidadId(int universidadId) {
        this.universidadId = universidadId;
    }

    public String getNombreUniv() {
        return nombreUniv;
    }

    public void setNombreUniv(String nombreUniv) {
        this.nombreUniv = nombreUniv;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Usuario getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Usuario administrador) {
        this.administrador = administrador;
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
