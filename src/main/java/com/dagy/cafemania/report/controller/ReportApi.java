package com.dagy.cafemania.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

public interface ReportApi {

    @Operation(summary = "Generate an Excel report containing all the categories")
    @GetMapping("/excel/categories")
    public ResponseEntity<InputStreamResource> generateCategoriesExcelReport() throws JRException;

    @Operation(summary = "Generate an Excel report containing all the products")
    @GetMapping("/excel/products")
    public ResponseEntity<InputStreamResource> generateProductsExcelReport() throws JRException;

    @Operation(summary = "Generate a PDF report containing all the categories along with all the products")
    @GetMapping("/pdf/full-report")
    public ResponseEntity<InputStreamResource> generatePdfFullReport() throws JRException;

    @Operation(summary = "Generate a zip file which contains two excel reports")
    @GetMapping("/zip")
    public ResponseEntity<InputStreamResource> generateAndZipReports() throws JRException, IOException;

    @Operation(summary = "Generate a multi-sheet Excel report containing categories and products")
    @GetMapping("/multi-sheet-excel")
    public ResponseEntity<InputStreamResource> generateMultiSheetExcelReport() throws JRException;


}
