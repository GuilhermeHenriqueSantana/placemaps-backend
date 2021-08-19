package com.esoft.placemaps.placemaps.dadosemanal;

import java.sql.Time;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.esoft.placemaps.configuration.basicclass.BasicClass;
import com.esoft.placemaps.placemaps.diadasemana.DiaDaSemana;
import com.esoft.placemaps.placemaps.item.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DadoSemanal extends BasicClass {
    
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "hora_inicio", nullable = false)
    private Time horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private Time horaFim;

    @Column(name = "descricao", nullable = false, length = 1000)
    private String descricao;

    @Column(name = "possui_valor", nullable = false)
    private Boolean possuiValor;

    @ManyToMany
    @JoinTable(
        name = "dado_semanal_dia_da_semana",
        joinColumns = @JoinColumn(name = "dado_semanal_id"),
        inverseJoinColumns = @JoinColumn(name = "dia_da_semana_id")
    )
    private List<DiaDaSemana> diasDaSemana;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dado_semanal_id")
    private List<Item> itens;
}
