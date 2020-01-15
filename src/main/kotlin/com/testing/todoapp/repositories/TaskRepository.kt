package com.testing.todoapp.repositories

import com.testing.todoapp.models.Task
import org.springframework.data.jpa.repository.JpaRepository


interface TaskRepository : JpaRepository<Task, Int>{
 fun getById(int: Int): Task?
}
