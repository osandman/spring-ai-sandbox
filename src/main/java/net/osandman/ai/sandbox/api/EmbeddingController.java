package net.osandman.ai.sandbox.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.osandman.ai.sandbox.config.Templates;
import net.osandman.ai.sandbox.config.VectorStoreConfig;
import net.osandman.ai.sandbox.service.DocumentService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
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
@RequiredArgsConstructor
@Slf4j
public class EmbeddingController {

    private final VectorStore vectorStore;
    private final VectorStoreConfig vectorStoreConfig;
    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final DocumentService documentService;

    @PostMapping("/add-text")
    public ResponseEntity<?> addText(@RequestBody List<String> inputTexts) {
        String dimStr = "Embedding model dimension = " + embeddingModel.dimensions();
        log.info(dimStr);
        List<Document> documents = new ArrayList<>();
        inputTexts.forEach(text -> documents.add(new Document(text)));
        vectorStore.add(documents);
        if (vectorStore instanceof SimpleVectorStore simpleVectorStore) {
            File file = vectorStoreConfig.getSimpleVectorStoreFile();
            simpleVectorStore.save(file);
        }
        String msg = "Successfully added embeddings, count: " + documents.size();
        log.info(msg);
        return ResponseEntity.ok(msg + ". " + dimStr);
    }

    @PostMapping("/add-documents")
    public ResponseEntity<?> addDocuments() {
        TextSplitter tokenTextSplitter = TokenTextSplitter.builder()
            .withChunkSize(200) // на небольших текстах очень хорошие результаты
            .build();
        List<Document> documents = documentService.getDocuments();
        for (Document document : documents) {
            List<Document> split = tokenTextSplitter.split(document);
            vectorStore.add(split);
        }
        String msg = "Successfully added documents, count: " + documents.size();
        log.info(msg);
        return ResponseEntity.ok(msg);
    }

    @PostMapping("/query")
    public String getQuery(@RequestBody String query, @RequestParam Double threshold) {
        return chatClient.prompt(query)
            .advisors(
                QuestionAnswerAdvisor.builder(vectorStore)
                    .promptTemplate(PromptTemplate.builder().template(Templates.CONTEXT_TEMPLATE).build())
                    .searchRequest(SearchRequest.builder().similarityThreshold(threshold).build())
                    .build()
            )
            .call()
            .content();
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
