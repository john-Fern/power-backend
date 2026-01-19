package com.power.backend.modules.producto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ProductoRequest {

    @NotBlank
    private String nombre;

    private String descripcion;

    @NotNull
    @Positive
    private BigDecimal precio;

    @NotNull
    @Positive
    private Integer stock;

    @NotNull
    private Integer categoriaId;

    private boolean activo = true;
    private boolean esDestacado = false;


    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEsDestacado() {
        return esDestacado;
    }

    public void setEsDestacado(boolean esDestacado) {
        this.esDestacado = esDestacado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
