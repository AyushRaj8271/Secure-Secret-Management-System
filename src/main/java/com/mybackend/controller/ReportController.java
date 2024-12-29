package com.mybackend.controller;

import com.mybackend.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/check")
    public ResponseEntity<byte[]> generateSecretReport() {
        try {
            byte[] report = reportService.generateReport();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "secret_report.pdf");
            return ResponseEntity.ok().headers(headers).body(report);
        } catch (JRException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
