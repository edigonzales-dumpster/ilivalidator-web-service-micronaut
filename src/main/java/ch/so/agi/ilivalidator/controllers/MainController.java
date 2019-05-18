package ch.so.agi.ilivalidator.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.views.View;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.so.agi.ilivalidator.services.IlivalidatorService;

@Controller("/ilivalidator")
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private static String FOLDER_PREFIX = "ilivalidator_";

    @Inject
    IlivalidatorService ilivalidatorService;

    @Get("/")
    @View("upload")
    public HttpStatus index() {
        System.out.println("bar");
        return HttpStatus.OK;
    }
    
    @Post(value = "/", consumes = MediaType.MULTIPART_FORM_DATA, produces = MediaType.TEXT_PLAIN) 
    @View("upload")
    public HttpResponse<String> validate(CompletedFileUpload file) {
        System.out.println("fubar");
        try {
            Path tmpDirectory = Files.createTempDirectory(Paths.get(System.getProperty("java.io.tmpdir")), FOLDER_PREFIX);
            Path uploadFilePath = Paths.get(tmpDirectory.toString(), file.getFilename());

            byte[] bytes = file.getBytes();
            Files.write(uploadFilePath, bytes);
            String uploadFileName = uploadFilePath.toFile().getAbsolutePath();
            log.info(uploadFileName);
            
            String logFileName = uploadFilePath.toFile().getAbsolutePath() + ".log";
            log.info(logFileName);

            boolean valid = ilivalidatorService.validate(uploadFileName, logFileName);
            Path logFilePath = Paths.get(logFileName);
            String logFileContent = new String(Files.readAllBytes(logFilePath));

            return HttpResponse.ok(logFileContent).header("Content-Type", "text/plain; charset=utf-8")
                    .contentLength(logFilePath.toFile().length());      
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return HttpResponse.badRequest("Something went wrong.\n\n" + e.getMessage());
        }
    }
}