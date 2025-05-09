package com.anderson.aplicacion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/descargar")//creamos la llave para el servidor
public class Aplicacion extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) //utilizamos el metodo doGet para que maneje las solicitudes
            throws ServletException, IOException {
        //Manejamos el parametro tipo para la url ya esa para el excel o pdf
        String tipo = request.getParameter("tipo");
        //Hacemos una verificacion si el parametro existe
        if (tipo != null) {
            switch (tipo) {
                case "excel":
                    descargarExcel(response);
                    break;
                case "pdf":
                    descargarPDF(response);
                    break;
            }
        }
    }
    //Este es un metodo que nos permite generar la descarga del archivo excel
    private void descargarExcel(HttpServletResponse response) throws IOException {
        // Datos de ejemplo para el Excel
        String excelData = "Nombre,Edad,Email\nJuan Perez,30,juan@example.com\nMaria Lopez,25,maria@example.com";
        //Hacemos el tipo de contenido y de como se va a llamar el archivo
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=reporte.xls");

        try (OutputStream out = response.getOutputStream()) {
            out.write(excelData.getBytes());
        }
    }
    //Metodo para generar y descargar el archivo pdf
    private void descargarPDF(HttpServletResponse response) throws IOException {
        //Hacemos el tipo de contenido y de como se va a llamar el archivo
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=documento.pdf");

        try (OutputStream out = response.getOutputStream()) {
            //Creamos el documento
            Document doc = new Document();
            PdfWriter.getInstance(doc, out);
            //Abrimos el documento para editar
            doc.open();
            // Agregar contenido al PDF
            doc.add(new Paragraph("Reporte de Ejemplo"));
            doc.add(new Paragraph("Este es un PDF generado profesionalmente usando iText"));
            doc.add(new Paragraph("\n")); // Salto de línea
            // Podemos agregar más elementos aquí:
            doc.close();
        } catch (DocumentException e) {
            throw new IOException("Error al generar el PDF", e);
        }
    }
}