package org.laptops.repositories;

import org.laptops.entities.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<Producer, String> {
}
