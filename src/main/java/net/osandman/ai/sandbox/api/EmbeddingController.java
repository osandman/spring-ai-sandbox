package net.osandman.ai.sandbox.api;

import net.osandman.ai.sandbox.config.VectorStoreConfig;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/embeddings")
public class EmbeddingController {

    private final VectorStore vectorStore;
    private final VectorStoreConfig vectorStoreConfig;


    public EmbeddingController(VectorStore vectorStore, VectorStoreConfig vectorStoreConfig) {
        this.vectorStore = vectorStore;
        this.vectorStoreConfig = vectorStoreConfig;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody List<String> inputTexts) {
        List<Document> documents = new ArrayList<>();
        inputTexts.forEach(text -> documents.add(new Document(text)));
        vectorStore.add(documents);
        if (vectorStore instanceof SimpleVectorStore simpleVectorStore) {
            File file = vectorStoreConfig.getSimpleVectorStoreFile();
            simpleVectorStore.save(file);
        }
        return ResponseEntity.ok("Successfully added embeddings, count: " + documents.size());
    }

    @GetMapping("/search")
    public List<Document> search(
        @RequestParam String query, @RequestParam Double threshold
    ) {
        return vectorStore.similaritySearch(
            SearchRequest.builder()
                .query(query)
                .similarityThreshold(threshold)
                .build()
        );
    }
}
