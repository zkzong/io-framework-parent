package io.code.example.simple.entity.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDto {

    private MultipartFile file;

}
