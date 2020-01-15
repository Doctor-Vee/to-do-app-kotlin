package com.testing.todoapp.models

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.validator.constraints.Length
import java.sql.Time
import javax.persistence.*


@Entity(name = "tasks") // if you don't specify a name, it will use the plural of the class name by default
class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
        private set
    var title: @Length(max = 150) String? = null
    var description: String? = null
    @Column(name = "task_status")
    var status: StatusEnum = StatusEnum.pending
    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt: Time? = null
    @Column(name = "updated_at")
    var updatedAt: Time? = null
    @Column(name = "completed_at")
    var completedAt: Time? = null

    constructor() {}
    constructor(title: @Length(max = 150) String?, description: String?, status: StatusEnum) {
        this.title = title
        this.description = description
        this.status = status
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun setUpdate(updatedAt: Time?) {
        this.updatedAt = updatedAt
    }

    fun setTheTitle(title: String) {
        this.title = title
    }

    fun getTheDescription(): String? {
        return description
    }

    fun setTheDescription(description: String?) {
        this.description = description
    }

    fun setTheCompletedAt(completedAt: Time?) {
        this.completedAt = completedAt
    }
}
