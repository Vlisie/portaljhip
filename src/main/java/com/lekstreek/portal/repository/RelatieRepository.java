package com.lekstreek.portal.repository;

import com.lekstreek.portal.domain.Relatie;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Relatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelatieRepository extends JpaRepository<Relatie, UUID>, JpaSpecificationExecutor<Relatie> {}
