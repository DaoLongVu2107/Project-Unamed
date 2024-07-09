package com.itboy.DACNPM.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentVersionDTO {
    private int versionNumber;
    private String filePath;
    private Long  createdBy;//id user
}