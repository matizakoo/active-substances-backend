package pl.engineer.active.substances.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.engineer.active.substances.dto.ActiveSubstanceDTO;
import pl.engineer.active.substances.dto.DiseaseDTO;
import pl.engineer.active.substances.dto.SearchEngineModel;
import pl.engineer.active.substances.dto.SearchEngineDTO;
import pl.engineer.active.substances.service.SearchEngineService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.engineer.active.substances.controller.advice.Endpoint.*;

@RestController
@RequestMapping(value = DOCTOR + SEARCH_ENGINE)
@AllArgsConstructor
public class SearchEngineController {
    private final SearchEngineService searchEngineService;

    /**
     * Returns list of SearchEngineModel which represents all available active substances for disease
     * based on current diesases and active substances
     * @param searchEngineDTO patient
     * */
    @PostMapping
    public ResponseEntity<List<SearchEngineModel>> getActiveSubstancesForChoosenDiseases(@RequestBody SearchEngineDTO searchEngineDTO) {
        List<SearchEngineModel> resultList = new ArrayList<>();
        for(DiseaseDTO diseaseDTO: searchEngineService.searchEngine(searchEngineDTO)) {
            SearchEngineModel searchEngineModel = new SearchEngineModel(diseaseDTO, diseaseDTO.getActiveSubstancesDTOList());
            resultList.add(searchEngineModel);
        }

        return ResponseEntity.ok(resultList);
    }
}
