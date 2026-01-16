package com.power.backend.dto;

public class OrdenCreateDTO {
    private Integer idUsuario;
    private Integer idProducto;
    private Integer cantidad;
    private Integer idDireccionEnvio; // puede ser null

    public OrdenCreateDTO() {}

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Integer getIdDireccionEnvio() { return idDireccionEnvio; }
    public void setIdDireccionEnvio(Integer idDireccionEnvio) { this.idDireccionEnvio = idDireccionEnvio; }
}
