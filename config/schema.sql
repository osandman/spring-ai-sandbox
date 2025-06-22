CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vector_store
(
    id        uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    content   text,
    metadata  json,
    embedding vector -- embedding vector(1536) default
);

-- HNSW (Hierarchical Navigable Small World) is an efficient algorithm for approximate nearest neighbor search, designed for processing large-scale and high-dimensional datasets.
-- CREATE INDEX IF NOT EXISTS embedding_idx ON vector_store USING HNSW (embedding vector_cosine_ops);