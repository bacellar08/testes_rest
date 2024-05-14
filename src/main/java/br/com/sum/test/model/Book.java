package br.com.sum.test.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "books")
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String author;
    @Column(name = "launch_date")
    private Date launchDate;
    private double price;
}
