package io.github.oengajohn.jps.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.http.HttpStatus;

import io.github.oengajohn.jps.JsonPlaceHolderServiceAutoConfiguration;
import io.github.oengajohn.jps.JsonPlaceHolderServiceProperties;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

public class JpsTodoClientTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().withConfiguration(
            AutoConfigurations.of(JsonPlaceHolderServiceAutoConfiguration.class,
                    WebClientAutoConfiguration.class));

    @Test
    void shouldContainJpsTodoClient() {
        contextRunner.run(context -> {
            assertTrue(context.containsBean("jpsWebClientBuilder"));
            assertTrue(context.containsBean("jpsTodoClient"));

        });
    }

    @Test
    void shouldContainDefaultBaseUrl() {
        contextRunner
                .run((context) -> {
                    assertThat(context).hasSingleBean(JsonPlaceHolderServiceProperties.class);
                    assertThat(context.getBean(JsonPlaceHolderServiceProperties.class).baseUrl())
                            .isEqualTo("https://jsonplaceholder.typicode.com");
                });
    }

    @Test
    void shouldSetCustomBaseUrl() {
        contextRunner
                .withPropertyValues("json-placeholder-service.base-url=https://localhost:3000")
                .run((context) -> {
                    assertThat(context).hasSingleBean(JsonPlaceHolderServiceProperties.class);
                    assertThat(context.getBean(JsonPlaceHolderServiceProperties.class).baseUrl())
                            .isEqualTo("https://localhost:3000");
                });
    }

    @Test
    void shouldFindAllTodos() {
        contextRunner
                .run((context) -> {
                    JpsTodoClient todoClient = context.getBean(JpsTodoClient.class);
                    StepVerifier
                            .create(todoClient.findAll())
                            .expectNextCount(200)
                            .verifyComplete();

                });
    }

    @Test
    void shouldFindTodoById() {
        contextRunner
                .run((context) -> {
                    JpsTodoClient todoClient = context.getBean(JpsTodoClient.class);
                    StepVerifier
                            .create(todoClient.findById(1))
                            .expectNextMatches(todo -> {
                                assertEquals(1, todo.id());
                                assertEquals(1, todo.userId());
                                assertEquals("delectus aut autem", todo.title());
                                assertEquals(false, todo.completed());
                                return true;
                            })
                            .verifyComplete();

                });
    }

    @Test
    void shouldDeleteTodoById() {
        contextRunner
                .run((context) -> {
                    JpsTodoClient todoClient = context.getBean(JpsTodoClient.class);
                    StepVerifier
                            .create(todoClient.delete(1))
                            .expectNextMatches(responseEntity -> {
                                assertTrue(responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT));
                                return true;
                            })
                            .verifyComplete();

                });
    }
}
