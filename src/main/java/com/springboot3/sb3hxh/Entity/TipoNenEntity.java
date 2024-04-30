package com.springboot3.sb3hxh.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.*;

@Entity @Table(name="tipos_nens") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TipoNenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name="id")
    private int id;

    @NotBlank(message = "A descrição do tipo de Nen é requerida")
    @Column(name="descricao")
    private String descricao;

    @Column(name = "deleted_at")
    @Setter(AccessLevel.NONE)
    private LocalDateTime deleted_at;

    public void setId(int id) { this.id = id; }

    public void setDeletedAt(LocalDateTime deletedAt) { this.deleted_at = deletedAt; }

}