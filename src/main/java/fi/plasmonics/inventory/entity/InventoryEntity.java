package fi.plasmonics.inventory.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "inventory_entity")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class InventoryEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "created_time")
    private Timestamp createTime;


    @Column(name = "created_by")
    private String createdBy;

}
