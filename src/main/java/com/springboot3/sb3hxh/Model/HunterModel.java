package com.springboot3.sb3hxh.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.*;

import java.text.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name="hunters")
public class HunterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotBlank(message = "O nome do Hunter é requerido")
    @Column(name="nome_hunter")
    private String nome_hunter;

    @NotNull(message = "A idade do Hunter é requerida")
    @Min(value = 13, message = "A idade do Hunter deve ser no mínimo de 13 anos")
    @Column(name="idade_hunter")
    private Integer idade_hunter;

    @NotNull(message = "A altura do Hunter é requerida")
    @DecimalMin(value = "1.50", message = "A altura deve ser no mínimo 1.50m")
    @DecimalMax(value = "2.50", message = "A altura deve ser no máximo 2.50m")
    @Column(name="altura_hunter")
    private Float altura_hunter;

    @NotNull(message = "O peso do Hunter é requerido")
    @DecimalMin(value = "50.00", message = "O peso do Hunter deve ser no mínimo 50.00kg")
    @DecimalMax(value = "150.00", message = "O peso do Hunter deve ser no máximo 150.00kg")
    @Column(name="peso_hunter")
    private Float peso_hunter;

    @OneToOne
    @NotNull(message = "O tipo de Hunter é requerido")
    @JoinColumn(name="tipo_hunter_id", referencedColumnName="id")
    private TipoHunterModel tipo_hunter_id;

    @OneToOne
    @NotNull(message = "O tipo de Nen é requerido")
    @JoinColumn(name="tipo_nen_id", referencedColumnName="id")
    private TipoNenModel tipo_nen_id;

    @OneToOne
    @NotNull(message = "O tipo sanguíneo é requerido")
    @JoinColumn(name="tipo_sanguineo_id", referencedColumnName="id")
    private TipoSanguineoModel tipo_sanguineo_id;

    @NotNull(message = "A data de início é requerida")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inicio;

    @NotNull(message = "A data de término é requerida")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date termino;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public HunterModel() {

    }

    public HunterModel(int id, String nome_hunter, Integer idade_hunter, Float altura_hunter, Float peso_hunter, Date inicio, Date termino, LocalDateTime deleted_at) {
        this.id = id;
        this.nome_hunter = nome_hunter;
        this.idade_hunter = idade_hunter;
        this.altura_hunter = altura_hunter;
        this.peso_hunter = peso_hunter;
        this.inicio = inicio;
        this.termino = termino;
        this.deleted_at = deleted_at;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome_hunter() { return nome_hunter; }

    public void setNome_hunter(String nome_hunter) { this.nome_hunter = nome_hunter; }

    public Integer getIdade_hunter() { return idade_hunter; }

    public void setIdade_hunter(Integer idade_hunter) { this.idade_hunter = idade_hunter; }

    public Float getAltura_hunter() { return altura_hunter; }

    public void setAltura_hunter(Float altura_hunter) { this.altura_hunter = altura_hunter; }

    public Float getPeso_hunter() { return peso_hunter; }

    public void setPeso_hunter(Float peso_hunter) { this.peso_hunter = peso_hunter; }

    public TipoHunterModel getTipo_hunter_id() { return tipo_hunter_id; }

    public void setTipo_hunter_id(TipoHunterModel tipo_hunter_id) { this.tipo_hunter_id = tipo_hunter_id; }

    public TipoNenModel getTipo_nen_id() { return tipo_nen_id; }

    public void setTipo_nen_id(TipoNenModel tipo_nen_id) { this.tipo_nen_id = tipo_nen_id; }

    public TipoSanguineoModel getTipo_sanguineo_id() { return tipo_sanguineo_id; }

    public void setTipo_sanguineo_id(TipoSanguineoModel tipo_sanguineo_id) { this.tipo_sanguineo_id = tipo_sanguineo_id; }

    public Date getInicio() { return inicio; }

    public void setInicio(Date inicio) { this.inicio = inicio; }

    public Date getTermino() { return termino; }

    public void setTermino(Date termino) { this.termino = termino; }

    public LocalDateTime getDeleted_at() { return deleted_at; }

    public void setDeleted_at(LocalDateTime deleted_at) { this.deleted_at = deleted_at; }

    public String alturaFormatada() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(this.getAltura_hunter()) + " m";
    }

    public String pesoFormatado() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(this.getPeso_hunter()) + " kg";
    }

    public String inicioFormatado() {
        if (this.getInicio() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(this.getInicio());
        } else {
            return "";
        }
    }

    public String terminoFormatado() {
        if (this.getTermino() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(this.getTermino());
        } else {
            return "";
        }
    }

}