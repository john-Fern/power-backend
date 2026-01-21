package com.power.backend.modules.orden.model;

import com.power.backend.modules.usuario.model.Usuario;
import com.power.backend.modules.direccion.model.Direccion;
import com.power.backend.modules.producto.model.Producto;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_direccion_envio")
    private Direccion direccionEnvio;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "fecha_orden")
    private LocalDateTime fechaOrden;

    @PrePersist
    public void prePersist() {
        if (fechaOrden == null)
            fechaOrden = LocalDateTime.now();
        if (estado == null)
            estado = "PENDIENTE";
    }
}
