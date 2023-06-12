package com.dagy.cafemania.report.service;

import com.dagy.cafemania.report.FileDTO;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;

public interface ReportService {

    FileDTO generateCategoryExcelReport() throws JRException;

    FileDTO generateProductsExcelReport() throws JRException;
    FileDTO generateBillsExcelReport() throws JRException;

    FileDTO generatePdfFullReport() throws JRException;

    FileDTO generateAndZipReports() throws JRException, IOException;

    FileDTO generateMultiSheetExcelReport() throws JRException;
}
