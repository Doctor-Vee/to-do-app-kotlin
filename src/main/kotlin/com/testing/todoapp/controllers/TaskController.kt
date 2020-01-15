package com.testing.todoapp.controllers


import com.testing.todoapp.controllers.MyResponse
import com.testing.todoapp.models.Task
import com.testing.todoapp.models.StatusEnum
import com.testing.todoapp.repositories.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException
import java.sql.Time
import java.time.LocalTime
import java.util.*


@RestController
@RequestMapping(value = ["tasks"]) // or @RequestMapping(value="tasks")
class TaskController {
    @Autowired
    lateinit var  taskRepository: TaskRepository

    @get:GetMapping
    val allTasks: MyResponse<List<Any>>
        get() {
            val tasks: List<Task> = taskRepository?.findAll() as List<Task>
            return MyResponse(HttpStatus.OK, "Todos retrieved successfully", tasks)
        }

    @PostMapping
    fun createTask(@RequestBody task: Task): MyResponse<Task?> {
        val tvalue: Task? = taskRepository?.save(task)
        return MyResponse(HttpStatus.CREATED, "Todo created successfully", tvalue)
    }

    @RequestMapping(value = ["{id}"])
    fun getOneTask(@PathVariable("id") id: Int?): MyResponse<Optional<Task?>?> {
        val t: Optional<Task?>? = id?.let { taskRepository?.findById(it) }
        return MyResponse(HttpStatus.OK, "Todo retrieved successfully", t)
    }

    @RequestMapping(value = ["{id}"], method = [RequestMethod.DELETE])
    fun deleteTask(@PathVariable("id") id: Int?): MyResponse<Optional<Task?>?> {
        val t: Optional<Task?>? = id?.let { taskRepository?.findById(it) }
        if (id != null) {
            taskRepository?.deleteById(id)
        }
        return MyResponse(HttpStatus.OK, "Todo deleted successfully", t)
    }

    @PatchMapping(value = ["{id}"])
    fun updateTask(@RequestBody task: Task, @PathVariable("id") id: Int): MyResponse<Task> {
        val t: Task = taskRepository.getById(id)?:run {
            throw RuntimeException("Hello World")
        }
        t.updatedAt = Time.valueOf(LocalTime.now())

        if (task.title != null) {
            t.title = task.title
        }
        if (task.description != null) {
            t.description = task.description
        }
        if (task.status != null) {
            t.status = task.status
        }

        taskRepository.save(t)

        return MyResponse(HttpStatus.OK, "Todo updated successfully", t)
    }

    @PatchMapping(value = ["/in-progress/{id}"])
    fun setTaskInProgress(@PathVariable("id") id: Int): MyResponse<Task> {
                val t = taskRepository.getById(id)?.let {
                    it.status = StatusEnum.inProgress
                    it.updatedAt = Time.valueOf(LocalTime.now())
                    taskRepository.save(it)
                } ?: throw RuntimeException("dgfhjkl")

        return MyResponse(HttpStatus.OK, "Todo in progress", t!! )
    }

    @PatchMapping(value = ["/complete/{id}"])
    fun completeTask(@PathVariable("id") id: Int?): MyResponse<Task?> {
        val t: Optional<Task?>? = id?.let { taskRepository?.findById(it) } !!
        val toUpdate: Task? = t?.get()
//        if (toUpdate != null) {
//            toUpdate.setStatus(StatusEnum.done)
//        }
        toUpdate?.status = StatusEnum.done
        toUpdate?.setUpdate(Time.valueOf(LocalTime.now()))
        toUpdate?.setTheCompletedAt(Time.valueOf(LocalTime.now()))
        if (taskRepository != null) {
            if (toUpdate != null) {
                taskRepository.save(toUpdate)
            }
        }
        return MyResponse(HttpStatus.OK, "Todo complete", toUpdate)
    }
}
