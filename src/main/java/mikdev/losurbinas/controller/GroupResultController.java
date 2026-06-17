package mikdev.losurbinas.controller;

import mikdev.losurbinas.model.OfficialResult;
import mikdev.losurbinas.repository.OfficialResultRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GroupResultController {

    private final OfficialResultRepository officialResultRepository;

    // Inyección por constructor (Buena práctica para el portafolio)
    public GroupResultController(OfficialResultRepository officialResultRepository) {
        this.officialResultRepository = officialResultRepository;
    }

    // GET: Transforma las entidades de la BD al formato JSON que el HTML espera leer
    @GetMapping
    public ResponseEntity<Map<String, Map<String, String>>> getResults() {
        List<OfficialResult> dbResults = officialResultRepository.findAll();
        Map<String, Map<String, String>> frontendResponse = new HashMap<>();

        for (OfficialResult res : dbResults) {
            Map<String, String> places = new HashMap<>();
            places.put("first", res.getFirstPlace());
            places.put("second", res.getSecondPlace());
            frontendResponse.put(res.getGroupLetter(), places);
        }

        return ResponseEntity.ok(frontendResponse);
    }

    // POST: Recibe el JSON del HTML, limpia registros viejos y persiste los nuevos resultados
    @PostMapping
    @Transactional
    public ResponseEntity<String> saveResults(@RequestBody Map<String, Map<String, String>> results) {
        try {
            // Limpiamos los resultados anteriores para sobreescribir limpiamente en lote
            officialResultRepository.deleteAllInBatch();

            // Mapeamos el JSON entrante a nuestra entidad JPA
            for (Map.Entry<String, Map<String, String>> entry : results.entrySet()) {
                String groupLetter = entry.getKey();
                String first = entry.getValue().get("first");
                String second = entry.getValue().get("second");

                OfficialResult officialResult = new OfficialResult();
                officialResult.setGroupLetter(groupLetter);
                officialResult.setFirstPlace(first);
                officialResult.setSecondPlace(second);

                officialResultRepository.save(officialResult);
            }

            return ResponseEntity.ok("Resultados oficiales guardados en la base de datos con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar resultados oficiales: " + e.getMessage());
        }
    }
}
