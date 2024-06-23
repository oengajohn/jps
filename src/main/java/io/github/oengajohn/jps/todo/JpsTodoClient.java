package io.github.oengajohn.jps.todo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.oengajohn.jps.JpsClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class JpsTodoClient implements JpsClient<Todo> {
    private final WebClient webClient;

    public JpsTodoClient(WebClient.Builder jpsWebClientBuilder) {
        this.webClient = jpsWebClientBuilder.build();
    }

    public Flux<Todo> findAll() {
        return webClient
                .get()
                .uri("/todos")
                .retrieve()
                .bodyToFlux(Todo.class);
    }

    public Mono<Todo> findById(Integer id) {
        return webClient
                .get()
                .uri("/todos/{id}", id)
                .retrieve()
                .bodyToMono(Todo.class);
    }

    public Mono<Todo> create(Todo todo) {
        return webClient
                .post()
                .uri("/todos")
                .body(BodyInserters.fromValue(todo))
                .retrieve()
                .bodyToMono(Todo.class);
    }

    @Override
    public Mono<Todo> update(Integer id, Todo todo) {
        return webClient
                .put()
                .uri("/todos/{id}", id)
                .body(BodyInserters.fromValue(todo))
                .retrieve()
                .bodyToMono(Todo.class);
    }

    @Override
    public Mono<ResponseEntity<Void>> delete(Integer id) {
        return webClient
                .delete()
                .uri("/todos/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }

}
