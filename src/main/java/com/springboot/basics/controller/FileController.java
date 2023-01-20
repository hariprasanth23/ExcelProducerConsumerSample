package com.springboot.basics.controller;

import com.springboot.basics.Exception.FileNotFoundException;
import com.springboot.basics.entity.FileEntity;
import com.springboot.basics.entity.FileEntityErrorResponse;
import com.springboot.basics.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Slf4j
public class FileController {

    @Autowired
    FileRepository fileRepository;

    @GetMapping("/")
    public String home(){
        return "hello world";
    }

    @PostMapping("/addingFilesDetailsToDB")
    public String addingFilesToFolder(){
        log.info(" First Time adding file details to DB");
        String[] fileType = {"csv","txt","xlsx","docx"};
        for(int i=0;i<10;i++){
            FileEntity fileEntity = new FileEntity("File"+1,new Random().nextInt(1000),fileType[new Random().nextInt(3)],"Owner"+i );
            fileRepository.save(fileEntity);
        }
        return "Random File Data is added to DB";
    }

    @GetMapping("/getAllFileDetails")
    public List<FileEntity> getAllFileDetails(){
        log.info("Getting all file details");
        List<FileEntity> fileEntityList = fileRepository.findAll();
        return fileEntityList;
    }

    @GetMapping("/getBySizeOrType/{Size}/{Type}")
    public List<FileEntity> getAllFilesBySizeOrByType(@PathVariable int Size, @PathVariable String Type){
        log.info(" Getting file by size or type");
        List<FileEntity> fileEntityList = fileRepository.findByFileSizeGreaterThanOrFileType(Size, Type);
        return fileEntityList;
    }

    @GetMapping("/getFileName/{FileName}")
    public FileEntity getFileEntityByFileName(@PathVariable String FileName){
        log.info("Getting file by filename");
        return fileRepository.findByFileName(FileName);
    }

    @DeleteMapping("/deleteFileName/{FileName}")
    public void deleteByFileName(@PathVariable String FileName){
        log.info("Deleting file by filename");
        fileRepository.deleteByFileName(FileName);
    }

    @PutMapping("/updateFiledetails")
    public String updateFileDetails(@RequestBody FileEntity FileEntity){
        Optional<FileEntity> fileEntityOptional = fileRepository.findById(FileEntity.getId());
        if(fileEntityOptional.isPresent()){
            FileEntity fileEntityToUpdate = fileEntityOptional.get();
            fileEntityToUpdate.setFileName(FileEntity.getFileName());
            fileEntityToUpdate.setFileSize(FileEntity.getFileSize());
            fileEntityToUpdate.setFileOwner(FileEntity.getFileOwner());
            fileEntityToUpdate.setId(FileEntity.getId());
            fileEntityToUpdate.setFileType(FileEntity.getFileType());
            fileRepository.save(fileEntityToUpdate);
            return "file Details Updated Successfuly";
        }
        return "file details not found";
    }

    @GetMapping("/filesInsideFolder")
    public List<String> listOfFiles() throws IOException{
        String dir = "C:\\Users\\HP Akash\\OneDrive\\wissen projects";
        try(Stream<Path> stream = Files.list(Paths.get(dir))){
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }

    }

    @ExceptionHandler
    public ResponseEntity<FileEntityErrorResponse> handleException(FileNotFoundException exc){

        FileEntityErrorResponse error = new FileEntityErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

}
