package com.acmebank.accountmanager.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class Customer {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String password;

    private BigDecimal balance;

}
