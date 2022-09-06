package fi.plasmonics.inventory.entity;


import java.sql.Timestamp;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
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
