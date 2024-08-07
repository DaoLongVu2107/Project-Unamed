package com.itboy.DACNPM.DTO;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentDTO {
    private String symbolNumber;
    private String describeOfDoc;
    private String issuingAuthority;
    private String field;
    private Long  createdBy;// id user
    private List<DocumentVersionDTO> versions;
}