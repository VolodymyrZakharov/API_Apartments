package api_apartments.service.impl;

import api_apartments.dto.*;
import api_apartments.entity.Apartment;
import api_apartments.entity.Building;
import api_apartments.entity.Owner;
import api_apartments.repository.ApartmentRepository;
import api_apartments.repository.BuildingRepository;
import api_apartments.repository.OwnerRepository;
import api_apartments.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Transactional
@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private OwnerRepository ownerRepository;

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
        List<Apartment> apartments = apartmentRepository.findAllByBuilding_Id(id);
        List<Owner> owners = ownerRepository.findAllByApartmentIn(apartments);

        owners.stream()
                .forEach(o -> o.setApartment(null));

        apartments.stream()
                .forEach(a -> apartmentRepository.delete(a));

        buildingRepository.deleteById(id);
    }

    @Override
    public void deployTheCity(List<BuildingDeployRequestDTO> request) {

        request.stream()
                .filter(x -> buildingRepository.findByStreetAndHouse(x.getStreet(), x.getHouse()) == null)
                .forEach(this::deploy);
    }

    public void deploy(BuildingDeployRequestDTO request) {
        Building building = Building.builder()
                .street(request.getStreet())
                .house(request.getHouse())
                .build();
        buildingRepository.save(building);

        request.getApartments().stream()
                .forEach(x -> {
                            AtomicInteger number = new AtomicInteger(0);
                            Apartment apartment = Apartment.builder()
                                    .apartmentNumber(number.incrementAndGet())
                                    .hasBalcony(x.getHasBalcony())
                                    .building(building)
                                    .build();
                            apartmentRepository.save(apartment);

                            x.getOwners().stream()
                                    .forEach(y -> DTOToOwner(y, apartment));
                        }
                );
    }

    public void DTOToOwner(OwnerRequestDTO request, Apartment apartment) {
        Owner owner = Owner.builder()
                .name(request.getName())
                .apartment(apartment)
                .build();
        try {
            ownerRepository.save(owner);
        } catch (Exception e) {
            return;
        }

    }
}

