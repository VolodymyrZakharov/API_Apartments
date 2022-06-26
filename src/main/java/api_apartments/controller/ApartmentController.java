package api_apartments.controller;

import api_apartments.dto.ApartmentResponseDTO;
import api_apartments.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping("/api/buildings/{id}/apartments")
    public List<ApartmentResponseDTO> getAllApartmentsByBuildingId(@PathVariable("id") Long id,
                                                                   @RequestParam(name = "hasOwners", required = false) boolean hasOwners) {
       return apartmentService.getAllApartmentsByBuildingId(id, hasOwners);

    }


}
