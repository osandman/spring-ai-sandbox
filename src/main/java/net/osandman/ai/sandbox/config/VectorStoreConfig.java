package net.osandman.ai.sandbox.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

@Configuration
@Slf4j
public class VectorStoreConfig {

    @Value("${resources-location.simple-vector-store}")
    private Resource simpleVectorStoreLocation;

    @Bean
    @ConditionalOnMissingBean(PgVectorStore.class)
    public VectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        File vectorStoreFile = getSimpleVectorStoreFile();
        if (vectorStoreFile.exists()) {
            log.info("Loading simple vector store from file: {}", vectorStoreFile.getAbsolutePath());
            simpleVectorStore.load(vectorStoreFile);
        }
        return simpleVectorStore;
    }

    public File getSimpleVectorStoreFile() {
        try {
            return simpleVectorStoreLocation.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
