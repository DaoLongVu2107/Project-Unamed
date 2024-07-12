package com.itboy.DACNPM.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateDocumentDTO {
    @JsonProperty("symbolNumber")
    private String symbolNumber;

    @JsonProperty("describeOfDoc")
    private String describeOfDoc;

    @JsonProperty("issuingAuthority")
    private String issuingAuthority;

    @JsonProperty("field")
    private String field;

}