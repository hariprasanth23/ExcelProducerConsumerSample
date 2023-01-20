package com.springboot.basics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    private String fileName;
    private int fileSize;
    private String fileType;
    private String fileOwner;

    public FileEntity(String fileName , int fileSize,String fileType,String fileOwner){
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.fileOwner = fileOwner;
    }


}
