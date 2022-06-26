package api_apartments.controller;

import api_apartments.dto.OwnerRequestDTO;
import api_apartments.dto.OwnerResponseDTO;
import api_apartments.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping("/api/owners")
    public void createOwner(@RequestBody OwnerRequestDTO request) {
        ownerService.createOwner(request);
    }

    @PutMapping("/api/buildings/{buidingId}/apartments/{apartmentId}/owners/{ownerId}")
    public void moveOwnerToApartment(@PathVariable("buidingId") Long buildingId,
                                     @PathVariable("apartmentId") Long apartmentId,
                                     @PathVariable("ownerId") Long ownerId) {
        ownerService.moveOwnerToApartment(buildingId, apartmentId, ownerId);
    }

    @DeleteMapping("/api/buildings/{buidingId}/apartments/{apartmentId}/owners/{ownerId}")
    public void evictOwnerFromApartment(@PathVariable("buidingId") Long buildingId,
                                        @PathVariable("apartmentId") Long apartmentId,
                                        @PathVariable("ownerId") Long ownerId) {
        ownerService.evictOwnerFromApartment(buildingId, apartmentId, ownerId);
    }

    @GetMapping("/api/owners/{id}")
    public OwnerResponseDTO getOwnerById(@PathVariable("id") Long id) {
        return ownerService.getOwnerById(id);
    }
}
