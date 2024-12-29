package com.mybackend.service;

import com.mybackend.repository.SecretRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private SecretRepository secretRepository;

    public byte[] generateReport() throws JRException {
        // Load the Jasper template
        InputStream reportStream = getClass().getResourceAsStream("/reports/secret_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Fetch data
       // long totalSecrets = secretRepository.countTotalSecrets();
        long totalEncrypted = secretRepository.countEncryptedSecrets();
       // long totalUnencrypted = secretRepository.countUnencryptedSecrets();
       // long totalReads = secretRepository.countTotalReads();
       // long totalUpdates = secretRepository.countTotalUpdates();
        //List<DeletedSecret> deletedSecrets = secretRepository.findAllDeletedSecrets();

        // Prepare parameters
        Map<String, Object> parameters = new HashMap<>();
        //parameters.put("totalSecrets", totalSecrets);
        parameters.put("totalEncrypted", totalEncrypted);
        //parameters.put("totalUnencrypted", totalUnencrypted);
        //parameters.put("totalReads", totalReads);
        //parameters.put("totalUpdates", totalUpdates);
        //parameters.put("deletedSecretsDataSource", new JRBeanCollectionDataSource(deletedSecrets));

        // Generate the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
