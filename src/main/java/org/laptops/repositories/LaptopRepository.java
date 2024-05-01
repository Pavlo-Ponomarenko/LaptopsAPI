package org.laptops.repositories;

import org.laptops.entities.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LaptopRepository extends JpaRepository<Laptop, Long>, JpaSpecificationExecutor<Laptop> {
}
