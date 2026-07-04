package com.example.reader.services;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {

    private final Resource resource;

    ReaderService(@Value("classpath:/PDF_FILES/test.pdf") Resource resource)
    {
        this.resource = resource;
    }

    public List<Document> readFile(){
        PagePdfDocumentReader pdfReader =  new PagePdfDocumentReader(resource,
                PdfDocumentReaderConfig.builder()
                        .withPageTopMargin(0)
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfTopTextLinesToDelete(0)
                                .build())
                        .withPagesPerDocument(1)
                .build());

        //B List<Document> documents = pdfReader.get();
        pdfReader.get().stream()
                .forEach(d -> d.getText());

        return pdfReader.read();
    }
}
