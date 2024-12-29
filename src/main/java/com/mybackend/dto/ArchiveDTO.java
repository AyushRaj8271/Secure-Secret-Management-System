package com.mybackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveDTO {

    private Long archiveId;
    private String name;
    private String description;
    private Long createdBy;
    private Timestamp createdOn;
    private Timestamp lastModified;
    private String encryptionVersion;
    private int accessCount;
    private String path;
    private String encryptedData;
}
