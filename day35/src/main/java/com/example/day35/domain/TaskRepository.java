package com.example.day35.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data JPA：方法名即查询，无需实现类。 */
public interface TaskRepository extends JpaRepository<Task, Long> {
}
