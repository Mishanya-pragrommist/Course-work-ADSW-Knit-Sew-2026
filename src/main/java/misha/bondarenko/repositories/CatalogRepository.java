package misha.bondarenko.repositories;

import misha.bondarenko.entities.products.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long>, JpaSpecificationExecutor<Catalog> {
    List<Catalog> findByName(String name);
}
