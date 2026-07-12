package com.example.rag.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;

import java.util.List;

@Service
public class ReaderService {
    Logger logger = LoggerFactory.getLogger(ReaderService.class);

    private final VectorStore vectorStore;
    private final Resource resource;

    ReaderService(VectorStore vectorStore, @Value("classpath:/PDF_DIRECTORY/fifa.pdf") Resource resource ){
        this.vectorStore = vectorStore;
        this.resource = resource;
    }

    public int coordinator(){
        List<Document> readDocuments = readFile();
        List<Document> splittedDocs = splitterFunction(readDocuments);
        logger.info("Insertando");
        int inserted =  insertDocuments(splittedDocs);
        logger.info("Fin de Insertando");
        return inserted;
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
        List<Document> documents =  pdfReader.read();
        List<Document> splittedDocs = splitterFunction(documents);
        logger.info("readFile documents: " + documents.size());
        logger.info("splitted Docs documents: " + splittedDocs.size());
        return documents;
    }

    public List<Document> splitterFunction(List<Document> documents){
        TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder()
                .withChunkSize(500)
                .build();
        List<Document> result = tokenTextSplitter.apply(documents);
        return tokenTextSplitter.apply(documents);
    }

    public int insertDocuments(List<Document> documents){
        for (int i = 0; i < documents.size(); i++) {
            Document doc = documents.get(i);
            logger.info("Insertando documentos no. " + i + " de " + documents.size());
            vectorStore.add(List.of(doc));
        }
        return documents.size();
    }

}
