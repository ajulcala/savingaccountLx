package com.savingaccount.app.models.documents;

import com.savingaccount.app.models.dto.Customer;
import com.savingaccount.app.models.dto.Managers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document("saving_account")
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccount {
    @Id
    private String id;
    @NotNull
    private Customer customer;
    @NotNull
    private String cardNumber;
    @NotNull
    private Integer limitTransactions;
    @NotNull
    private Integer freeTransactions;
    @NotNull
    private Double commissionTransactions;
    @NotNull
    private Double balance;
    private Double minAverageVip;
    private LocalDateTime createAt;
    private List<Managers> owners;
    private List<Managers> signatories;
}
