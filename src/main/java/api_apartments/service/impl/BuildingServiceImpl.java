package api_apartments.service.impl;

import api_apartments.dto.BuildingRequestDTO;
import api_apartments.dto.BuildingResponseDTO;
import api_apartments.entity.Apartment;
import api_apartments.entity.Building;
import api_apartments.repository.ApartmentRepository;
import api_apartments.repository.BuildingRepository;
import api_apartments.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public void createBuilding(BuildingRequestDTO request, int apartmentsCount) {

        if (buildingRepository.findByStreetAndHouse(request.getStreet(), request.getHouse()) != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        Building building = Building.builder()
                .street(request.getStreet())
                .house(request.getHouse())
                .build();
        buildingRepository.save(building);

        AtomicInteger number = new AtomicInteger(1);

        request.getApartmentsDTO().stream()
                .forEach(a -> {
                    Apartment apartment = Apartment.builder()
                            .apartmentNumber(number.getAndIncrement())
                            .hasBalcony(a.getHasBalcony())
                            .building(building)
                            .build();
                    apartmentRepository.save(apartment);
                });
    }

    @Override
    public List<BuildingResponseDTO> getAllBuildingsByStreet(String street) {
        List<Building> allBuildingsOnTheStreet = buildingRepository.findAllByStreet(street);

        return allBuildingsOnTheStreet.stream()
                .map(a -> {
                    BuildingResponseDTO buildingResponseDTO = BuildingResponseDTO.builder()
                            .id(a.getId())
                            .street(a.getStreet())
                            .house(a.getHouse())
                            .apartmentsCount(apartmentRepository.countAllByBuildingId(a.getId()))
                            .build();
                    return buildingResponseDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public void destroyBuildingById(Long id) {
        Building building = buildingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        buildingRepository.delete(building);
    }
}
