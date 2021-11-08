package com.savingaccount.app.models.dto;

import lombok.Data;

@Data
public class SubType {
    private String id;
    private EnumSubType value;
    public enum EnumSubType{
        NORMAL, VIP, PYME
    }
}
