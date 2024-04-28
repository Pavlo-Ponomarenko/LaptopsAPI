package org.laptops.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Laptop {

    @Id
    private Long id;
    private String title;
    @ManyToOne
    private Producer producer;
    private String processor;
    private String memory;
    private String optionalPorts;
}
