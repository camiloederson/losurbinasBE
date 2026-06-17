package mikdev.losurbinas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "predictions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"player_id", "groupLetter"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(name = "groupLetter", nullable = false, length = 1)
    private String groupLetter;

    private String firstPlace;
    private String secondPlace;
}