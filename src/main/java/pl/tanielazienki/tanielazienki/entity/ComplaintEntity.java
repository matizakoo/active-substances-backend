package pl.tanielazienki.tanielazienki.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "complaint")
public class ComplaintEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String productCode;
    private LocalDate dataOfReport;
    private LocalDate dataOfReportAnswer;
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private ProviderEntity providerEntity;
    @ManyToOne
    @JoinColumn(name = "contractor_id", nullable = false)
    private ContractorEntity contractorEntity;
    private LocalDate dateOfPurchase;
    private String factureId;

    //client info
    private String username;
    private String surname;
    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "complaintEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dateTime DESC")
    private List<NoteEntity> notes = new ArrayList<>();
}
