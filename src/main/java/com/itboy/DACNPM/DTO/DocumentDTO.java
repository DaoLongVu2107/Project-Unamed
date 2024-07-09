package com.itboy.DACNPM.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO {
    private String symbolNumber;
    private String describeOfDoc;
    private String issuingAuthority;
    private String field;
    private Long  createdBy;// id user
    private List<DocumentVersionDTO> versions;
}