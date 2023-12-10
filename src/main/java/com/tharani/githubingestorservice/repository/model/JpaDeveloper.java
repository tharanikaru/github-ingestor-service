package com.tharani.githubingestorservice.repository.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "developer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class JpaDeveloper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String githubUserName;
    private String firstName;
    private String lastName;


}
