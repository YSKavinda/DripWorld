package com.codevibe.web.dripworld.entities;

import com.codevibe.web.dripworld.constants.SettingType;
import com.codevibe.web.dripworld.util.AppUtil;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "settings", schema = "dripwrld_db", catalog = "")
public class SettingsEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "name",unique = true)
    private SettingType name;

    @Column(name = "value")
    private String value;


    public SettingsEntity() {
    }

    public SettingsEntity(SettingType name, String value) {
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SettingType getName() {
        return name;
    }

    public void setName(SettingType name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @PostPersist
    @PostUpdate
    public void updateAppUtil(){
        AppUtil.reload();
    }


}
