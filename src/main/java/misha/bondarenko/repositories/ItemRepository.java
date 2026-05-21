package misha.bondarenko.repositories;

import misha.bondarenko.entities.products.Catalog;
import misha.bondarenko.entities.products.Item;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    Optional<Item> findByName(String name);
    List<Item> findByParent(Catalog parentCatalog);
}