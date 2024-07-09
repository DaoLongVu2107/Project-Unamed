package com.itboy.DACNPM.Enity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "document")
@Entity
public class Document  extends BasicEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "symbol_number", nullable = false)
    private String symbolNumber;
    private Date date;
    private String describeOfDoc;
    @Column(name = "issuing_authority", nullable = false)
    private String issuingAuthority;
    @Column(name = "field", nullable = false)
    private String field;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonManagedReference
    private User createdBy;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    @JsonManagedReference
    private List<DocumentVersion> versions = new ArrayList<>();
}
