package com.lekstreek.portal.repository;

import com.lekstreek.portal.domain.Relatie;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Relatie entity.
 */
@Repository
public interface RelatieRepository extends JpaRepository<Relatie, UUID>, JpaSpecificationExecutor<Relatie> {
    @Query(
        value = "select distinct relatie from Relatie relatie left join fetch relatie.rols",
        countQuery = "select count(distinct relatie) from Relatie relatie"
    )
    Page<Relatie> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct relatie from Relatie relatie left join fetch relatie.rols")
    List<Relatie> findAllWithEagerRelationships();

    @Query("select relatie from Relatie relatie left join fetch relatie.rols where relatie.id =:id")
    Optional<Relatie> findOneWithEagerRelationships(@Param("id") UUID id);
}
