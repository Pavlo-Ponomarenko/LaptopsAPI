package org.laptops.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producers")
@NoArgsConstructor
@Setter
@Getter
public class Producer {

    @Id
    String name;
}
