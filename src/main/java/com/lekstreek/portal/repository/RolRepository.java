package com.lekstreek.portal.repository;

import com.lekstreek.portal.domain.Rol;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Rol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RolRepository extends JpaRepository<Rol, UUID>, JpaSpecificationExecutor<Rol> {}
