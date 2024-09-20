package com.nmt.groceryfinder.common.enums;

import lombok.Getter;

@Getter
public enum ApiVersionEnum {
    API_VERSION_V1("2024-08-24"),
    API_VERSION_V2("2024-09-24"),
    ;
    private final String timestamp;
    ApiVersionEnum(String timestamp){
        this.timestamp = timestamp;
    }
}
