package com.power.backend.modules.usuario.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 80)
    private String apellido;

    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "hash_password", nullable = false, length = 100)
    private String hashPassword;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, columnDefinition = "varchar(255) default 'USUARIO'")
    @Builder.Default
    private RolUsuario rol = RolUsuario.USUARIO;

    // public Usuario() {}
    //
    // public Boolean getActivo() {
    // return activo;
    // }
    //
    // public void setActivo(Boolean activo) {
    // this.activo = activo;
    // }
    //
    // public String getApellido() {
    // return apellido;
    // }
    //
    // public void setApellido(String apellido) {
    // this.apellido = apellido;
    // }
    //
    // public String getEmail() {
    // return email;
    // }
    //
    // public void setEmail(String email) {
    // this.email = email;
    // }
    //
    // public LocalDateTime getFechaRegistro() {
    // return fechaRegistro;
    // }
    //
    // public void setFechaRegistro(LocalDateTime fechaRegistro) {
    // this.fechaRegistro = fechaRegistro;
    // }
    //
    // public String getHashPassword() {
    // return hashPassword;
    // }
    //
    // public void setHashPassword(String hashPassword) {
    // this.hashPassword = hashPassword;
    // }
    //
    // public Integer getId() {
    // return id;
    // }
    //
    // public void setId(Integer id) {
    // this.id = id;
    // }
    //
    // public String getNombre() {
    // return nombre;
    // }
    //
    // public void setNombre(String nombre) {
    // this.nombre = nombre;
    // }

}
