package com.dagy.cafemania.report.controller;

import com.dagy.cafemania.report.FileDTO;
import com.dagy.cafemania.report.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.dagy.cafemania.shared.constant.AppConstant.INTERNAL_SERVER_ERROR_RESPONSE;

@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
@RestController
public class ReportController implements ReportApi {
    private final ReportService reportService;
    @Override
    public ResponseEntity<InputStreamResource> generateCategoriesExcelReport() throws JRException {
        try {
            System.out.println("-------------- IN CONTROLLER ---------------");
            FileDTO report = reportService.generateCategoryExcelReport();

            byte[] file = Base64.decodeBase64(report.getFileContent());
            InputStream targetStream = new ByteArrayInputStream(file);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Disposition", "attachment; filename=".concat(report.getFileName()));

            return ResponseEntity
                    .ok()
                    .headers(httpHeaders)
                    .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                    .contentLength(file.length)
                    .body(new InputStreamResource(targetStream));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return ResponseEntity.internalServerError().body(new InputStreamResource(new ByteArrayInputStream(Base64.decodeBase64("file.xt"))));

    }

    @Override
    public ResponseEntity<InputStreamResource> generateProductsExcelReport() throws JRException {
        FileDTO report = reportService.generateProductsExcelReport();

        byte[] file = Base64.decodeBase64(report.getFileContent());
        InputStream targetStream = new ByteArrayInputStream(file);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment; filename=".concat(report.getFileName()));

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .contentLength(file.length)
                .body(new InputStreamResource(targetStream));
    }

    @Override
    public ResponseEntity<InputStreamResource> generatePdfFullReport() throws JRException {
        FileDTO report = reportService.generatePdfFullReport();

        byte[] file = Base64.decodeBase64(report.getFileContent());
        InputStream targetStream = new ByteArrayInputStream(file);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment; filename=".concat(report.getFileName()));

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .contentLength(file.length)
                .body(new InputStreamResource(targetStream));
    }

    @Override
    public ResponseEntity<InputStreamResource> generateAndZipReports() throws JRException, IOException {
        FileDTO report = reportService.generateAndZipReports();

        byte[] file = Base64.decodeBase64(report.getFileContent());
        InputStream targetStream = new ByteArrayInputStream(file);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment; filename=".concat(report.getFileName()));

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .contentLength(file.length)
                .body(new InputStreamResource(targetStream));
    }

    @Override
    public ResponseEntity<InputStreamResource> generateMultiSheetExcelReport() throws JRException {
        FileDTO report = reportService.generateMultiSheetExcelReport();

        byte[] file = Base64.decodeBase64(report.getFileContent());
        InputStream targetStream = new ByteArrayInputStream(file);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment; filename=".concat(report.getFileName()));

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .contentLength(file.length)
                .body(new InputStreamResource(targetStream));
    }
}
