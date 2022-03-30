package fi.plasmonics.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import fi.plasmonics.inventory.entity.AuditLog;
import fi.plasmonics.inventory.repo.AuditLogRepository;

@Service("auditLogService")
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;


    public List<AuditLog> getAuditLogs(){
        return auditLogRepository.findAll();
    }

    public AuditLog save(AuditLog auditLog){
        return auditLogRepository.save(auditLog);
    }
}
