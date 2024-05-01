package org.laptops.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producers")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Producer {

    @Id
    String name;
}
