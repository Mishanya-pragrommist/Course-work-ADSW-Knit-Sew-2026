package misha.bondarenko;

//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.File;
//
//@Configuration
//public class AiConfiguration {
//
//    // Вказуємо шлях до файлу, де будуть зберігатися вектори при вимкненні сервера
//    private final File vectorStoreFile = new File("src/main/resources/vector-store.json");
//
//    @Bean
//    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
//        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(embeddingModel);
//
//        // Якщо файл вже існує, завантажуємо вектори з нього
//        if (vectorStoreFile.exists()) {
//            simpleVectorStore.load(vectorStoreFile);
//        }
//        return simpleVectorStore;
//    }
//}
