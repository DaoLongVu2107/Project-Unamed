package com.itboy.DACNPM.Repository;


import com.itboy.DACNPM.Enity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {
}
