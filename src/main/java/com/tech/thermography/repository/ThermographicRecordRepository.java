package com.tech.thermography.repository;

import com.tech.thermography.domain.ThermographicRecord;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ThermographicRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThermographicRecordRepository extends JpaRepository<ThermographicRecord, UUID> {
    @Query(
        """
        SELECT DISTINCT tir FROM ThermographicRecord tir
        LEFT JOIN FETCH tir.plant
        LEFT JOIN FETCH tir.route
        LEFT JOIN FETCH tir.equipment
        LEFT JOIN FETCH tir.component
        LEFT JOIN FETCH tir.createdBy
        LEFT JOIN FETCH tir.finishedBy
        LEFT JOIN FETCH tir.parentRecord
        LEFT JOIN FETCH tir.thermogram t
        LEFT JOIN FETCH t.rois
        """
    )
    List<ThermographicRecord> findAllWithRelationships();

    @Query(
        """
        SELECT tir FROM ThermographicRecord tir
        LEFT JOIN FETCH tir.thermogramRef tr
        LEFT JOIN FETCH tr.rois
        WHERE tir IN :records
        """
    )
    List<ThermographicRecord> findWithThermogramRef(@Param("records") List<ThermographicRecord> records);
}
