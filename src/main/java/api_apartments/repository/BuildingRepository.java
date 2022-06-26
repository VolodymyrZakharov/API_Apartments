package api_apartments.repository;

import api_apartments.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    List<Building> findAllByStreet(String street);

    Building findByStreetAndHouse(String street, String house);
}
