package com.savingaccount.app.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String id;
    private DocumentType documentType; //ruc/dni
    private String documentNumber; //ruc o dni
    private String names; //nombres o razon social
    private String gender; //masculino / feminino
    private String numberphone; //telefono
    private String address;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateBirth;
    private TypeCustomer typeCustomer;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date createAc;

    public enum DocumentType {
        DNI,
        RUC
    }
}
