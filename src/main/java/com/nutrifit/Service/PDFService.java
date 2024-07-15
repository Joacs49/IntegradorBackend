package com.nutrifit.Service;

import com.nutrifit.Clases.Gastos;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PDFService {

    public void generatePDF(List<Gastos> gastosList, String filePath) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        try {
            ClassPathResource fondoResource = new ClassPathResource("fotos/fondo.png");
            PDImageXObject backgroundImage = PDImageXObject.createFromByteArray(document, fondoResource.getInputStream().readAllBytes(), "fondo");
            contentStream.drawImage(backgroundImage, 0, 0, PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight());

            ClassPathResource logoResource = new ClassPathResource("fotos/logo.png");
            PDImageXObject logoImage = PDImageXObject.createFromByteArray(document, logoResource.getInputStream().readAllBytes(), "logo");
            contentStream.drawImage(logoImage, 25, 750, logoImage.getWidth() / 4, logoImage.getHeight() / 4); // Ajustar tamaño y posición del logo según sea necesario
        } catch (IOException e) {
            e.printStackTrace();
        }

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 720);
        contentStream.showText("Reporte de Gastos del Usuario");
        contentStream.endText();

        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 695);
        contentStream.showText("Fecha de generación: " + java.time.LocalDate.now());
        contentStream.endText();

        contentStream.moveTo(25, 690);
        contentStream.lineTo(580, 690);
        contentStream.stroke();

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 670);
        contentStream.showText("Fecha");
        contentStream.newLineAtOffset(150, 0);
        contentStream.showText("Usuario");
        contentStream.newLineAtOffset(150, 0);
        contentStream.showText("Monto");
        contentStream.endText();

        double totalMonto = gastosList.stream().mapToDouble(Gastos::getMonto).sum();

        contentStream.setFont(PDType1Font.HELVETICA, 10);
        int yPosition = 650;
        for (Gastos gastos : gastosList) {
            if (yPosition < 50) {
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                PDImageXObject backgroundImage = null;
                PDImageXObject logoImage = null;
                contentStream.drawImage(backgroundImage, 0, 0, PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight());
                contentStream.drawImage(logoImage, 25, 750, logoImage.getWidth() / 4, logoImage.getHeight() / 4);
                yPosition = 750;
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(25, yPosition);
            contentStream.showText(gastos.getFecha());
            contentStream.newLineAtOffset(150, 0);
            contentStream.showText(gastos.getUsuario());
            contentStream.newLineAtOffset(150, 0);
            contentStream.showText(String.valueOf(gastos.getMonto()));
            contentStream.endText();
            yPosition -= 15;
        }

        // Línea separadora antes del total
        contentStream.moveTo(25, yPosition - 10);
        contentStream.lineTo(580, yPosition - 10);
        contentStream.stroke();

        // Texto del total
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(25, yPosition - 30);
        contentStream.showText("Total: " + totalMonto);
        contentStream.endText();

        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 30);
        contentStream.showText("Reporte generado por nutrifit");
        contentStream.endText();

        contentStream.close();
        document.save(filePath);
        document.close();
    }
}
