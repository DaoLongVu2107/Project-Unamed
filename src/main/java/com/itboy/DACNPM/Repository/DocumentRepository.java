package com.itboy.DACNPM.Repository;

import com.itboy.DACNPM.Enity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findBySymbolNumber(String symbolNumber);
    @Query("SELECT d FROM Document d WHERE YEAR(d.date) = :year")
    List<Document> findDocumentsByYear(@Param("year") int year);
    List<Document> findByField(String field);

}
