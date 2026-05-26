package br.com.sergio.customer_management_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column (unique = true, nullable = false)
    private String email;

    @Column (name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column (unique = true, nullable = false)
    private String cpf;

}
