package net.osandman.ai.sandbox.api;

import lombok.RequiredArgsConstructor;
import net.osandman.ai.sandbox.service.DocumentService;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/read")
    List<Document> getDocument() {
        return documentService.getDocuments();
    }
}
