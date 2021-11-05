package com.esoft.placemaps.configuration.basicclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public abstract class BasicClass {
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id", length = 36)
    protected String id;

    public BasicClass(){
        this.id = UUID.randomUUID().toString();
    }
}
