package com.nutrifit.Web;

import com.nutrifit.Clases.Gastos;
import com.nutrifit.Clases.Usuario;
import com.nutrifit.Dao.IGastos;
import com.nutrifit.Service.GastosRepository;
import com.nutrifit.Service.PDFService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gastos")
public class ControladorGastos {

    @Autowired
    private IGastos gastosService;
    
    @Autowired
    private GastosRepository gastosServices;
    
    @Autowired
    private PDFService pdfService;

    @GetMapping("/usuario/{id}")
    public Optional<Usuario> findById(@PathVariable Long id) {
        return gastosService.findById(id);
    }

    @PostMapping("/ingresar")
    public boolean ingresarGasto(@RequestParam Long idUsuario,
            @RequestParam double monto) {
        return gastosService.ingresarGasto(idUsuario, monto);
    }
    
    @GetMapping("/usuario/{id}/pdf")
    public ResponseEntity<InputStreamResource> generatePDF(@PathVariable Long id) {
        try {
            // obtener los gastos del usuario
            List<Gastos> gastosList = gastosServices.findGastosByUsuarioId(id);
            String filePath = "gastos_usuario_" + id + ".pdf";
            pdfService.generatePDF(gastosList, filePath);

            // preparar el archivo para la descarga
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamResource resource = new InputStreamResource(fileInputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=gastos_usuario_" + id + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(new File(filePath).length())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
