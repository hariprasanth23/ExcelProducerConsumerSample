package com.springboot.basics.repository;

import com.springboot.basics.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Integer> {

    List<FileEntity> findByFileSizeGreaterThanOrFileType(int fileSize,String fileType);
    FileEntity findByFileName(String fileName);
    Boolean deleteByFileName(String fileName);
}
