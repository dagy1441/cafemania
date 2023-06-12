package com.dagy.cafemania.report.service;

import com.dagy.cafemania.categories.Category;
import com.dagy.cafemania.categories.payload.CategoryMapper;
import com.dagy.cafemania.categories.payload.CategoryReportDTO;
import com.dagy.cafemania.categories.service.CategoryRepository;
import com.dagy.cafemania.product.model.Product;
import com.dagy.cafemania.product.payload.ProductMapper;
import com.dagy.cafemania.product.payload.ProductReportDTO;
import com.dagy.cafemania.product.service.ProductRepository;
import com.dagy.cafemania.report.FileDTO;
import com.dagy.cafemania.shared.utilities.annotation.ExecutionTime;
import com.dagy.cafemania.report.jasperreport.SimpleReportExporter;
import com.dagy.cafemania.report.jasperreport.SimpleReportFiller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final SimpleReportExporter reportExporter;
    private final SimpleReportFiller simpleReportFiller;

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;


    @ExecutionTime
    @Override
    public FileDTO generateCategoryExcelReport() throws JRException {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryReportDTO> reportRecords = categoryMapper.categoryListTocategoryReportDtoList(categoryList);

        String dateAsString = this.getCurrentDateAsString();
        String fileName = "Category_Report_" + dateAsString + ".xlsx";

        byte[] reportAsByteArray = reportExporter.exportReportToByteArray(
                reportRecords, fileName, "jrxml/excel/categoriesExcelReport");

        String base64String = Base64.encodeBase64String(reportAsByteArray);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileContent(base64String);
        fileDTO.setFileName(fileName);

        return fileDTO;
    }

    @ExecutionTime
    @Override
    public FileDTO generateProductsExcelReport() throws JRException {
        List<Product> productList = productRepository.findAll();
        List<ProductReportDTO> reportRecords = productMapper.mapProductListToProductReportDtoList(productList);

        String dateAsString = this.getCurrentDateAsString();
        String fileName = "Product_Report_" + dateAsString + ".xlsx";

        byte[] reportAsByteArray = reportExporter.exportReportToByteArray(
                reportRecords, fileName, "jrxml/excel/productsExcelReport");

        String base64String = Base64.encodeBase64String(reportAsByteArray);


        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileContent(base64String);
        fileDTO.setFileName(fileName);

        return fileDTO;
    }

    @Override
    @ExecutionTime
    public FileDTO generateBillsExcelReport() throws JRException {
        //TODO Implement generateBillsExcelReport
        return null;
    }

    @Override
    @ExecutionTime
    public FileDTO generatePdfFullReport() throws JRException {
        return null;
    }

    @Override
    @ExecutionTime
    public FileDTO generateAndZipReports() throws JRException, IOException {
        return null;
    }

    @Override
    @ExecutionTime
    public FileDTO generateMultiSheetExcelReport() throws JRException {
        return null;
    }

    private String getCurrentDateAsString() {

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return dateTimeFormatter.format(localDate);
    }
}
