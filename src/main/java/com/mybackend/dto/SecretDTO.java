package com.mybackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretDTO {

    private Long secretId;
    private String name;
    private String description;
    private Long createdBy; // Assuming this will hold the userId of the creator
    private Timestamp createdOn;
    private Timestamp lastModified;
    private String encryptionVersion;
    private int accessCount;
    private String path;
    private String encryptedData;
}
