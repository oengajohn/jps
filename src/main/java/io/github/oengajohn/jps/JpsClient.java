package io.github.oengajohn.jps;

import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JpsClient<T> {
    Flux<T> findAll();

    Mono<T> findById(Integer id);

    Mono<T> create(T t);

    Mono<T> update(Integer id, T t);

    Mono<ResponseEntity<Void>> delete(Integer id);
}
