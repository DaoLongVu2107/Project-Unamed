package com.itboy.DACNPM.Enity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "document_version")
@Entity
public class DocumentVersion extends BasicEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id")
    @JsonBackReference
    private Document document;
    private int versionNumber;
    private String filePath;
    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonManagedReference
    private User createdBy;

}
