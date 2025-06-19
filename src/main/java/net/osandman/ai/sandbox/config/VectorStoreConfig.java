package net.osandman.ai.sandbox.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@Slf4j
public class VectorStoreConfig {

    @Value("${simple-vector-store.path}")
    private String vectorStorePath;

    @Bean
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
        return new File("src/main/resources", vectorStorePath);
    }
}
