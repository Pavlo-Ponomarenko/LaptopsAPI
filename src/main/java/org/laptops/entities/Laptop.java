package org.laptops.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "laptops")
@NoArgsConstructor
@Setter
@Getter
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Producer producer;
    private String processor;
    private String memory;
    private String optionalPorts;
}
