package com.ajackus.digitallibrarymanagement.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "books")
public class Book {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String title;


    private String author;

    private String genre;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
    
    public enum AvailabilityStatus {
        AVAILABLE, CHECKED_OUT
    }

}
