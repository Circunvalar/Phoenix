package ucentral.software.PhoenixStore10.controladores;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpSession;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ucentral.software.PhoenixStore10.entidades.*;
import ucentral.software.PhoenixStore10.repositorios.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/orden")
public class ControladorFinalizarCompra {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RepoFactura repoFactura;
    @Autowired
    private RepoProducto repoProducto;
    @Autowired
    private RepoEmisor repoEmisor; // Repositorio para el emisor

    @GetMapping("/confirmar")
    public String mostrarVistaConfirmacion(HttpSession session, Model model) {
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/carrito";
        }
        double total = carrito.stream()
                .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                .sum();
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        return "finalizar-compra";
    }

    @PostMapping("/confirmar")
    public String procesarCompra(
            @RequestParam("direccion") String direccion,
            HttpSession session,
            Model model
    ) {
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) {
            model.addAttribute("error", "El carrito está vacío.");
            return "redirect:/carrito";
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            model.addAttribute("error", "No hay usuario autenticado.");
            return "redirect:/login";
        }

        String nombreCompleto = usuario.getUsunombres() + " " + usuario.getUsuapellidos();
        String correo = usuario.getUsucorreo();

        // Obtener datos del emisor desde la base de datos
        Emisor emisor = repoEmisor.findFirstByOrderByIdAsc();
        if (emisor == null) {
            model.addAttribute("error", "No se encontró la información del emisor.");
            return "finalizar-compra";
        }

        String razonSocial = emisor.getRazonSocial();
        String rucEmisor = emisor.getRuc();
        String direccionEmisor = emisor.getDireccion();
        String condicionVenta = "Contado";
        String formaPago = "Virtual";
        String numeroFactura = "FAC-" + LocalDateTime.now().getYear() + "-" + System.currentTimeMillis();

        // Construir detalles
        List<DetalleFactura> detalles = carrito.stream().map(item -> {
            Productos producto = repoProducto.findByPronombre(item.getNombre());
            return DetalleFactura.builder()
                    .factura(null)
                    .producto(producto)
                    .cantidad(item.getCantidad())
                    .precioUnitario(item.getPrecio())
                    .subtotal(item.getCantidad() * item.getPrecio())
                    .build();
        }).toList();

        double total = detalles.stream().mapToDouble(DetalleFactura::getSubtotal).sum();
        double iva = total * 0.19; // 19% de IVA
        double subtotal = total - iva;

        try {
            // Crear la factura base con más datos
            Factura factura = Factura.builder()
                    .cliente(nombreCompleto)
                    .numero(numeroFactura)
                    .fecha(LocalDateTime.now())
                    .direccion(direccion)
                    .correo(correo)
                    .subtotal(subtotal)
                    .iva(iva)
                    .total(subtotal)
                    .formaPago(formaPago)
                    .condicionVenta(condicionVenta)
                    .build();

            detalles.forEach(det -> det.setFactura(factura));
            factura.setDetalles(detalles);
            repoFactura.save(factura);

            // Generar PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);
            doc.open();
            String telefonoEmisor = "285 7463"; // puedes obtenerlo de BD en lugar de dejarlo fijo

            // Encabezado
            doc.add(new Paragraph(razonSocial + " - RUC: " + rucEmisor));
            doc.add(new Paragraph("Dirección: " + direccionEmisor));
            doc.add(new Paragraph("Teléfono: " + telefonoEmisor));
            doc.add(new Paragraph("Factura N°: " + numeroFactura));
            doc.add(new Paragraph("Fecha: " + factura.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
            doc.add(new Paragraph(" "));

            // Cliente
            doc.add(new Paragraph("Cliente: " + nombreCompleto));
            doc.add(new Paragraph("Dirección: " + direccion));
            doc.add(new Paragraph("Correo: " + correo));
            doc.add(new Paragraph("Condición de venta: " + condicionVenta));
            doc.add(new Paragraph("Forma de pago: " + formaPago));
            doc.add(new Paragraph(" "));

            // Tabla de productos
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("Producto");
            table.addCell("Cantidad");
            table.addCell("P. Unitario");
            table.addCell("Subtotal");

            for (ItemCarrito item : carrito) {
                table.addCell(item.getNombre());
                table.addCell(String.valueOf(item.getCantidad()));
                table.addCell(String.format("$ %.2f", item.getPrecio()));
                table.addCell(String.format("$ %.2f", item.getCantidad() * item.getPrecio()));
            }

            doc.add(table);
            doc.add(new Paragraph(" "));

            // Totales
            doc.add(new Paragraph("Subtotal: $" + String.format("%.2f", subtotal)));
            doc.add(new Paragraph("IVA 19%: $" + String.format("%.2f", iva)));
            doc.add(new Paragraph("TOTAL A PAGAR: $" + String.format("%.2f", total)));

            doc.close();

            byte[] pdfBytes = baos.toByteArray();

            // Enviar correo
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(correo);
            helper.setSubject("Factura - " + razonSocial);
            helper.setText("Hola " + nombreCompleto + ",\n\nGracias por tu compra. Adjuntamos la factura en PDF.\n\n" + razonSocial);
            helper.addAttachment("Factura-PhoenixStore.pdf", new ByteArrayResource(pdfBytes));
            mailSender.send(message);

            // Limpiar carrito
            session.removeAttribute("carrito");

            model.addAttribute("nombre", nombreCompleto);
            return "compra-exitosa";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Hubo un problema al procesar tu compra: " + e.getMessage());
            return "finalizar-compra";
        }
    }
}