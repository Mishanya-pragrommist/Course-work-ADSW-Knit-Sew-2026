package misha.bondarenko.repositories;

import misha.bondarenko.entities.products.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    List<Catalog> findByParentIsNull();
    List<Catalog> findByCode(String code);
    List<Catalog> findByName(String name);
}
