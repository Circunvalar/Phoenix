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
import ucentral.software.PhoenixStore10.entidades.DetalleFactura;
import ucentral.software.PhoenixStore10.entidades.Factura;
import ucentral.software.PhoenixStore10.entidades.ItemCarrito;
import ucentral.software.PhoenixStore10.entidades.Productos;
import ucentral.software.PhoenixStore10.repositorios.RepoDetalleFactura;
import ucentral.software.PhoenixStore10.repositorios.RepoFactura;
import ucentral.software.PhoenixStore10.repositorios.RepoProducto;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
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
        return "finalizar-compra"; // Asegúrate de tener este HTML en /templates
    }

    @PostMapping("/confirmar")
    public String procesarCompra(
            @RequestParam("nombre") String nombre,
            @RequestParam("direccion") String direccion,
            @RequestParam("correo") String correo,
            HttpSession session,
            Model model
    ) {
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");

        if (carrito == null || carrito.isEmpty()) {
            model.addAttribute("error", "El carrito está vacío.");
            return "redirect:/carrito";
        }

        double total = carrito.stream()
                .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                .sum();

        try {
            // 1. Crear la factura base
            Factura factura = Factura.builder()
                    .cliente(nombre)
                    .fecha(LocalDateTime.now())
                    .total(total)
                    .build();

// 2. Construir detalles
            List<DetalleFactura> detalles = carrito.stream().map(item -> {
                Productos producto = repoProducto.findByPronombre(item.getNombre()); // o por ID si lo tienes

                return DetalleFactura.builder()
                        .factura(factura)
                        .producto(producto)
                        .cantidad(item.getCantidad())
                        .precioUnitario(item.getPrecio())
                        .subtotal(item.getCantidad() * item.getPrecio())
                        .build();
            }).toList();

// 3. Asociar los detalles a la factura
            factura.setDetalles(detalles);

// 4. Guardar todo
            repoFactura.save(factura);

            // 1. Generar PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);
            doc.open();

            doc.add(new Paragraph("Phoenix Store - Factura"));
            doc.add(new Paragraph("Cliente: " + nombre));
            doc.add(new Paragraph("Dirección: " + direccion));
            doc.add(new Paragraph("Correo: " + correo));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Detalle de productos:"));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.addCell("Producto");
            table.addCell("Cantidad");
            table.addCell("Subtotal");

            for (ItemCarrito item : carrito) {
                table.addCell(item.getNombre());
                table.addCell(String.valueOf(item.getCantidad()));
                double subtotal = item.getPrecio() * item.getCantidad();
                table.addCell(String.format("$ %.2f", subtotal));
            }

            doc.add(table);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Total: $" + String.format("%.2f", total)));
            doc.close();

            byte[] pdfBytes = baos.toByteArray();

            // 2. Enviar correo
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(correo);
            helper.setSubject("Factura - Phoenix Store");
            helper.setText("Hola " + nombre + ",\n\nGracias por tu compra. Adjuntamos la factura en PDF.\n\nPhoenix Store");

            helper.addAttachment("Factura-PhoenixStore.pdf", new ByteArrayResource(pdfBytes));
            mailSender.send(message);

            // 3. Limpiar carrito
            session.removeAttribute("carrito");

            // 4. Redirigir a página de éxito
            model.addAttribute("nombre", nombre);

            return "compra-exitosa";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Hubo un problema al procesar tu compra: " + e.getMessage());
            return "finalizar-compra";
        }
    }
}
