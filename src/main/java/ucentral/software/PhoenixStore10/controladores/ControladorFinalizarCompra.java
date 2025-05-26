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
import ucentral.software.PhoenixStore10.entidades.Usuario;
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

        // Agregar usuario autenticado al modelo
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

        double total = carrito.stream()
                .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                .sum();

        try {
            // Crear la factura base
            Factura factura = Factura.builder()
                    .cliente(nombreCompleto)
                    .fecha(LocalDateTime.now())
                    .total(total)
                    .build();

            // Construir detalles
            List<DetalleFactura> detalles = carrito.stream().map(item -> {
                Productos producto = repoProducto.findByPronombre(item.getNombre());
                return DetalleFactura.builder()
                        .factura(factura)
                        .producto(producto)
                        .cantidad(item.getCantidad())
                        .precioUnitario(item.getPrecio())
                        .subtotal(item.getCantidad() * item.getPrecio())
                        .build();
            }).toList();

            factura.setDetalles(detalles);
            repoFactura.save(factura);

            // Generar PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);
            doc.open();

            doc.add(new Paragraph("Phoenix Store - Factura"));
            doc.add(new Paragraph("Cliente: " + nombreCompleto));
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

            // Enviar correo
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(correo);
            helper.setSubject("Factura - Phoenix Store");
            helper.setText("Hola " + nombreCompleto + ",\n\nGracias por tu compra. Adjuntamos la factura en PDF.\n\nPhoenix Store");
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