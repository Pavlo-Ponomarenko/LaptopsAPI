package org.laptops.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.laptops.entities.Laptop;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Specifications for building sql request with filter
 * @author Pavlo Ponomarenko
 */
@AllArgsConstructor
public class LaptopSpecification implements Specification<Laptop> {

    private final Map<String, Object> filters;
    @Override
    public Predicate toPredicate(Root<Laptop> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        filters.forEach((key, value) -> {
            Predicate predicate = criteriaBuilder.equal(root.get(key), value);
            predicates.add(predicate);
        });
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
