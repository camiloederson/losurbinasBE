package mikdev.losurbinas.controller;

import mikdev.losurbinas.model.Player;
import mikdev.losurbinas.model.Prediction;
import mikdev.losurbinas.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllPlayerNames() {
        List<String> names = playerService.getAllPlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toList());

        if (names.isEmpty()) {
            List<String> defaultPlayers = List.of("Camilo", "Alejandro", "Manuel", "Diego");
            for (String name : defaultPlayers) {
                playerService.getOrCreatePlayer(name);
            }
            return ResponseEntity.ok(defaultPlayers);
        }
        return ResponseEntity.ok(names);
    }

    @GetMapping("/{name}/predictions")
    public ResponseEntity<Map<String, Map<String, String>>> getPlayerPredictions(@PathVariable String name) {
        Player player = playerService.getOrCreatePlayer(name);
        List<Prediction> predictions = player.getPredictions();

        Map<String, Map<String, String>> response = new HashMap<>();

        if (predictions != null) {
            for (Prediction pred : predictions) {
                Map<String, String> places = new HashMap<>();
                places.put("first", pred.getFirstPlace());
                places.put("second", pred.getSecondPlace());
                response.put(pred.getGroupLetter(), places);
            }
        }

        return ResponseEntity.ok(response);
    }

    // El POST que ya tenías para guardar
    @PostMapping("/{name}/predictions")
    public ResponseEntity<String> savePredictions(@PathVariable String name,
                                                  @RequestBody Map<String, Map<String, String>> predictions) {
        try {
            playerService.savePredictions(name, predictions);
            return ResponseEntity.ok("Predicciones guardadas con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar: " + e.getMessage());
        }
    }
}
