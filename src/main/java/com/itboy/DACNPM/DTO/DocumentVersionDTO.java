package com.itboy.DACNPM.DTO;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentVersionDTO {

    private int versionNumber;
    private String filePath;
    private Boolean status;
    private Long  createdBy;//id user
}