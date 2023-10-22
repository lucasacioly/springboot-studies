package com.example.todoRocketLab2.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TaskModel {

    /*
    *
    * ID
    * usuario
    * descrição
    * titulo
    * data de inicio
    * data de conclusao
    * prioridade
    *
    * */

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String titulo;
    private String descricao;
    private LocalDateTime dataIni;
    private LocalDateTime dataFim;
    private String prioridade;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private UUID userId;


    public void setTitulo(String titulo) throws Exception {
        if (titulo.length() > 50){
            throw new Exception("Campo title deve ter no máximo 50 caracteres");
        }
        this.titulo = titulo;
    }
}
