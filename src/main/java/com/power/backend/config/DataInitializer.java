package com.power.backend.config;

import com.power.backend.modules.categoria.model.Categoria;
import com.power.backend.modules.categoria.repository.CategoriaRepository;
import com.power.backend.modules.producto.model.Producto;
import com.power.backend.modules.producto.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("🚀 Iniciando Seeder de Datos...");

        // 1. Asegurar Categorías
        crearCategoriaSiNoExiste("Patines", "Patines de todo tipo");
        crearCategoriaSiNoExiste("Ruedas", "Ruedas de repuesto y upgrade");
        crearCategoriaSiNoExiste("Protecciones", "Cascos, rodilleras y más");
        crearCategoriaSiNoExiste("Mochilas", "Mochilas especializadas");
        crearCategoriaSiNoExiste("Accesorios", "Herramientas y accesorios varios");
        crearCategoriaSiNoExiste("Ropa", "Ropa técnica y casual");
        crearCategoriaSiNoExiste("Repuestos", "Repuestos generales");
        crearCategoriaSiNoExiste("Giftcards", "Tarjetas de Regalo");

        // 2. Asegurar Productos (Solo si hay pocos, para evitar duplicados masivos)
        if (productoRepository.count() < 5) {
            System.out.println("📦 Sembrando productos iniciales...");

            crearProducto("Patines Urban Flow", "Patines", 89990,
                    "assets/img/urban-inline-skates-black-and-teal-professional.jpg", true, 15,
                    "Patines urbanos de alta calidad con ruedas de 80mm y rodamientos ABEC-7.");
            crearProducto("Patines Freestyle Pro", "Patines", 129990,
                    "assets/img/freestyle-roller-skates-white-professional.jpg", true, 8,
                    "Patines profesionales para freestyle con bota rígida.");
            crearProducto("Patines Fitness Speed", "Patines", 109990, "assets/img/fitness-inline-skates-blue-speed.jpg",
                    false, 20, "Patines de fitness con ruedas de 90mm.");

            crearProducto("Ruedas Street 80mm", "Ruedas", 24990,
                    "assets/img/inline-skate-wheels-80mm-set-of-4-teal.jpg", true, 50,
                    "Set de 4 ruedas de 80mm dureza 85A.");
            crearProducto("Ruedas Speed 100mm", "Ruedas", 34990,
                    "assets/img/inline-skate-wheels-100mm-professional-white.jpg", false, 30,
                    "Set de 4 ruedas de 100mm dureza 88A.");

            crearProducto("Kit Protecciones Completo", "Protecciones", 39990,
                    "assets/img/skating-protection-gear-set-knee-pads-elbow-pads-w.jpg", true, 25,
                    "Kit completo con rodilleras, coderas y muñequeras.");
            crearProducto("Casco Urban Style", "Protecciones", 44990,
                    "assets/img/skating-helmet-urban-style-matte-black.jpg", true, 18,
                    "Casco certificado con diseño urbano.");

            crearProducto("Mochila Skate Pack", "Mochilas", 54990,
                    "assets/img/skating-backpack-with-skate-holder-teal-black.jpg", true, 12,
                    "Mochila diseñada para patinadores con porta-patines.");
            crearProducto("Bolso Porta Patines", "Mochilas", 29990, "assets/img/skate-bag-carrier-black-teal.jpg",
                    false, 22, "Bolso especializado para transportar patines.");

            crearProducto("Polera Rocket Team", "Ropa", 19990, "assets/img/sports-tshirt-teal-with-skating-logo.jpg",
                    false, 40, "Polera técnica con tejido transpirable.");
            crearProducto("Hoodie Urban Skate", "Ropa", 39990,
                    "assets/img/urban-hoodie-black-with-teal-accents-skating-style.jpg", true, 15,
                    "Hoodie premium con capucha ajustable.");

            crearProducto("Rodamientos ABEC-9", "Repuestos", 19990, "assets/img/skate-bearings-abec-9-set-of-8.jpg",
                    false, 60, "Set de 8 rodamientos ABEC-9 de alta precisión.");
            crearProducto("Frenos de Repuesto", "Repuestos", 9990,
                    "assets/img/inline-skate-brake-pad-replacement-black.jpg", false, 100,
                    "Par de frenos de repuesto universales.");

            crearProducto("Patines Niños Ajustables", "Patines", 59990,
                    "assets/img/kids-adjustable-inline-skates-colorful-blue-teal.jpg", true, 25,
                    "Patines ajustables en 4 tallas para niños.");

            crearProducto("Giftcard $50.000", "Giftcards", 50000,
                    "assets/img/gift-card-modern-design-teal-gradient-skating-them.jpg", false, 999,
                    "Tarjeta de regalo Rocket Power por $50.000.");
            crearProducto("Giftcard $100.000", "Giftcards", 100000,
                    "assets/img/gift-card-modern-design-teal-gradient-skating-them.jpg", false, 999,
                    "Tarjeta de regalo Rocket Power por $100.000.");

            System.out.println("✅ Productos iniciales cargados.");
        }
    }

    private void crearCategoriaSiNoExiste(String nombre, String descripcion) {
        if (categoriaRepository.findByNombre(nombre).isEmpty()) {
            Categoria c = Categoria.builder().nombre(nombre).descripcion(descripcion).build();
            categoriaRepository.save(c);
            System.out.println("➕ Categoría creada: " + nombre);
        }
    }

    private void crearProducto(String nombre, String categoriaNombre, int precio, String img, boolean destacado,
            int stock, String desc) {
        Optional<Categoria> catOpt = categoriaRepository.findByNombre(categoriaNombre);
        if (catOpt.isPresent()) {
            Producto p = Producto.builder()
                    .nombre(nombre)
                    .categoria(catOpt.get())
                    .precio(BigDecimal.valueOf(precio))
                    .imagenUrl(img)
                    .destacado(destacado)
                    .stock(stock)
                    .descripcion(desc)
                    .activo(true)
                    .fechaCreacion(LocalDateTime.now())
                    .build();
            productoRepository.save(p);
        } else {
            System.out.println("⚠️ No se encontró categoría: " + categoriaNombre + " para producto " + nombre);
        }
    }
}
