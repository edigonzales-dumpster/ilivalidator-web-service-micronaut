package ch.so.agi.ilivalidator.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.HttpStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/ilivalidator")
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Get("/")
    public HttpStatus index() {
        System.out.println("bar");
        return HttpStatus.OK;
    }
}