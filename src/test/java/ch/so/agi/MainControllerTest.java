package ch.so.agi;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

@MicronautTest
public class MainControllerTest {

//    @Inject
//    EmbeddedServer embeddedServer;
    
    static EmbeddedServer embeddedServer;

    @BeforeAll
    public static void setup() {
        ApplicationContext context = ApplicationContext.run(
                "micronaut.http.client.read-timeout:1s"
        );
        embeddedServer = context.getBean(EmbeddedServer.class).start();
    }

    @AfterAll
    public static void cleanup() {
        if (embeddedServer != null) {
            embeddedServer.stop();
        }
    }


    @Test
    public void testIndex() throws Exception {
        try(RxHttpClient client = embeddedServer.getApplicationContext().createBean(RxHttpClient.class, embeddedServer.getURL())) {            
            assertEquals(HttpStatus.OK, client.toBlocking().exchange("/ilivalidator").status());
        }
    }
    
    @Test
    public void validation_Ok() throws Exception {
        try(HttpClient client = embeddedServer.getApplicationContext().createBean(HttpClient.class, embeddedServer.getURL())) {

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
