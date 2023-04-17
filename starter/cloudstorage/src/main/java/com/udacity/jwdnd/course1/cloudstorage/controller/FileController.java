package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileRepository;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private FileRepository fileRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("fileUpload") MultipartFile multipartFile, Principal principal) throws IOException {

        if (Objects.nonNull(fileRepository.findByName(multipartFile.getOriginalFilename()))) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("filename already exist!");
        }

        fileService.insertFile(multipartFile, principal);

        return ResponseEntity.ok("Successfully uploaded file");
    }

    @GetMapping("/delete/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable int fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.ok("Successfully deleted file");
    }

    @GetMapping("/download/{fileId}")
    public void downloadFile(@PathVariable int fileId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = fileService.findFile(fileId);

        String mimeType = URLConnection.guessContentTypeFromName(file.getFilename());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getFilename() + "\""));
        response.setContentLength(Integer.parseInt(file.getFilesize()));

        InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(file.getFiledata()));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
