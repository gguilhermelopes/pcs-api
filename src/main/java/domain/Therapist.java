package domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "therapist")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Therapist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String cellphone;
    private String expertise;
    @OneToMany(mappedBy = "therapist")
    private Set<Patient> patients = new HashSet<>();
    @OneToMany(mappedBy = "therapist")
    private Set<Session> sessions = new HashSet<>();
}
