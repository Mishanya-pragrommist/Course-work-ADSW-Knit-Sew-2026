package misha.bondarenko.repositories;

import misha.bondarenko.entities.products.Catalog;
import misha.bondarenko.entities.products.Kit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// TODO: remove if unused
@Repository
public interface KitRepository extends JpaRepository<Kit, Long> {
    Optional<Kit> findByName(String name);
    List<Kit> findByParent(Catalog parentCatalog);
}
