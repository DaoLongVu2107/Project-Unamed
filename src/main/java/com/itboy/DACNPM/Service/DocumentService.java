package com.itboy.DACNPM.Service;

import com.itboy.DACNPM.Enity.Document;
import com.itboy.DACNPM.Repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    public List<Document> getALlDocument(){
        return documentRepository.findAll();
    }
    public Optional<Document> findById(int id){
        return documentRepository.findById(id);
    }

    public Document createDocument(Document document){
       return documentRepository.save(document);
    }
    public List<Document> findDocbySymbolNumber(String symbolNumber){
        return  documentRepository.findBySymbolNumber(symbolNumber);
    }
    public List<Document> findDocumentsByYear(int year) {
        List<Document> documents = documentRepository.findDocumentsByYear(year);
        if (documents.isEmpty()) {
            return Collections.emptyList();
        }
        return documents;
    }
    public List<Document> findDocumentsByField(String field) {
        List<Document> documents = documentRepository.findByField(field);
        if (documents.isEmpty()) {
            return Collections.emptyList();
        }
        return documents;
    }
}
