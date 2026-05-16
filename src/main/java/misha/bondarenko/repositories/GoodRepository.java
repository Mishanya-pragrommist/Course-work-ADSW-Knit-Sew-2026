package misha.bondarenko.repositories;

import misha.bondarenko.entities.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);
    Optional<Product> findByName(String name);
}