package net.osandman.ai.sandbox.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {

    @Value("${resources-location.documents}")
    private Resource[] resources;

    public List<Document> getDocuments() {
        List<Document> documentList = new ArrayList<>();
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                TikaDocumentReader documentReader = new TikaDocumentReader(resource);
                documentReader.get().forEach(document -> {
                    document.getMetadata().put("source", resource.getFilename());
                    documentList.add(document);
                });
            }
        }
        return documentList;
    }
}
