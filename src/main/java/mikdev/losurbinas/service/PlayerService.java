package mikdev.losurbinas.service;
import mikdev.losurbinas.model.Player;
import mikdev.losurbinas.model.Prediction;
import mikdev.losurbinas.repository.PlayerRepository;
import mikdev.losurbinas.repository.PredictionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PredictionRepository predictionRepository;

    public PlayerService(PlayerRepository playerRepository, PredictionRepository predictionRepository) {
        this.playerRepository = playerRepository;
        this.predictionRepository = predictionRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Transactional
    public Player getOrCreatePlayer(String name) {
        return playerRepository.findByName(name)
                .orElseGet(() -> {
                    Player newPlayer = new Player();
                    newPlayer.setName(name);
                    return playerRepository.save(newPlayer);
                });
    }

    @Transactional
    public void savePredictions(String playerName, Map<String, Map<String, String>> predictionsData) {
        Player player = getOrCreatePlayer(playerName);

        // 1. Si el jugador ya tiene predicciones, las borramos físicamente de MySQL inmediatamente
        if (player.getPredictions() != null && !player.getPredictions().isEmpty()) {
            predictionRepository.deleteAllInBatch(player.getPredictions());
            predictionRepository.flush(); // <-- OBLIGATORIO: Fuerza a MySQL a aplicar el DELETE ya mismo
            player.getPredictions().clear();
        }

        // 2. Insertamos las nuevas predicciones limpiamente sin violar el UniqueConstraint
        for (Map.Entry<String, Map<String, String>> entry : predictionsData.entrySet()) {
            String groupLetter = entry.getKey();
            String first = entry.getValue().get("first");
            String second = entry.getValue().get("second");

            // Si no ha elegido nada, evitamos guardar filas vacías innecesarias
            if ((first == null || first.isEmpty()) && (second == null || second.isEmpty())) {
                continue;
            }

            Prediction prediction = new Prediction();
            prediction.setPlayer(player);
            prediction.setGroupLetter(groupLetter);
            prediction.setFirstPlace(first);
            prediction.setSecondPlace(second);

            predictionRepository.save(prediction);
        }
    }
}
