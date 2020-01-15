package com.testing.todoapp.controllers

import org.springframework.http.HttpStatus


class MyResponse<T>(val status: HttpStatus, val message: String, val data: T)
