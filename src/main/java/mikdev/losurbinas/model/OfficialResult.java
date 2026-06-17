package mikdev.losurbinas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "official_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficialResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "groupLetter", unique = true, nullable = false, length = 1)
    private String groupLetter; // "A", "B", "C", etc.

    private String firstPlace;
    private String secondPlace;
}