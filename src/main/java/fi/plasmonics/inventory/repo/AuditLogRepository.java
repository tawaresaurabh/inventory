package fi.plasmonics.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.plasmonics.inventory.entity.AuditLog;


@Repository("auditLogRepository")
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
