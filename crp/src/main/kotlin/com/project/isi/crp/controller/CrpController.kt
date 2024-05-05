package com.project.isi.crp.controller

import com.project.isi.crp.model.car
import com.project.isi.crp.service.CrpService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CrpController(private val service: CrpService) {

    @GetMapping("/test")
    fun test(): String{
        return  service.testService()
    }

    @GetMapping("/cars")
    fun getAllCars(): List<String?>{
        return service.cars()
    }
}