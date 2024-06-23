package io.github.oengajohn.jps.todo;

/**
 * Todo
 */
public record Todo(Integer userId, Integer id, String title, Boolean completed) {
}