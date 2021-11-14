package com.lekstreek.portal.repository;

import com.lekstreek.portal.domain.Adres;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Adres entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdresRepository extends JpaRepository<Adres, UUID> {}
