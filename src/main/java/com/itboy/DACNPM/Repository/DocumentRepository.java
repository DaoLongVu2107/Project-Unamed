package com.itboy.DACNPM.Repository;

import com.itboy.DACNPM.Enity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  DocumentRepository extends JpaRepository<Document, Integer> {
}
