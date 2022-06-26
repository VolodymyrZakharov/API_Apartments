package api_apartments.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apartment")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "apartmentNumber")
    private Integer apartmentNumber;

    @Column(name = "hasBalcony")
    private Boolean hasBalcony;

    @JoinColumn(name = "building_id")
    @ManyToOne
    private Building building;
}
