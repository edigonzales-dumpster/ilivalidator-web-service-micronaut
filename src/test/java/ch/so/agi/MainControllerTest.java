package ch.so.agi;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@MicronautTest
public class MainControllerTest {

    @Inject
    EmbeddedServer embeddedServer;

    @Test
    public void testIndex() throws Exception {
        try(RxHttpClient client = embeddedServer.getApplicationContext().createBean(RxHttpClient.class, embeddedServer.getURL())) {
            assertEquals(HttpStatus.OK, client.toBlocking().exchange("/ilivalidator").status());
        }
    }
    
    @Test
    public void validation_Ok() throws Exception {
        try(RxHttpClient client = embeddedServer.getApplicationContext().createBean(RxHttpClient.class, embeddedServer.getURL())) {

            final MultipartBody body = MultipartBody.builder()
                    .addPart("file", new File("src/test/data/254900.itf"))
                    .build();
            
            String response = client.toBlocking().exchange(
                    HttpRequest.POST("/ilivalidator", body)
                            .contentType(MediaType.MULTIPART_FORM_DATA_TYPE), String.class)
                    .body();
            
            assertTrue(response.contains("Info: ...validation done"));
        }
    }
}
