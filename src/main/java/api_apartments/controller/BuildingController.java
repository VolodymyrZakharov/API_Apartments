package api_apartments.controller;

import api_apartments.dto.*;
import api_apartments.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/buildings")
    public void createBuilding(@RequestParam(name = "apartmentsCount") int apartmentCount,
                               @RequestBody BuildingRequestDTO request) {
        buildingService.createBuilding(request, apartmentCount);
    }

    @GetMapping("/api/buildings")
    public List<BuildingResponseDTO> getAllBuildingsOnTheStreet(@RequestParam(name = "street") String street) {
        return buildingService.getAllBuildingsByStreet(street);
    }

    @DeleteMapping("/api/buildings/{id}/demolish")
    public void deleteBuilding(@PathVariable("id") Long id) {
        buildingService.destroyBuildingById(id);
    }
}
