package com.example.day14.task;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Day14：任务内存仓库。对照笔记：§2。
 */
@Repository
public class InMemoryTaskRepository {

    private final Map<Long, Task> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Task saveNew(Task task) {
        long id = seq.getAndIncrement();
        task.setId(id);
        store.put(id, task);
        return task;
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    public Task update(Task task) {
        store.put(task.getId(), task);
        return task;
    }
}
