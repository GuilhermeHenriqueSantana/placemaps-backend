package com.esoft.placemaps.configuration.basicclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public abstract class BasicClass {
    @Id
    @Column(name = "id", length = 36)
    protected String id;

    public BasicClass(){
        this.id = UUID.randomUUID().toString();
    }
}
