package lt.verbus.verbusltapi.web.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/")
    fun getIndex() = "Index"

    @GetMapping("/hello")
    fun greet() = "Hi"

    @GetMapping("/get-increased/{number}")
    fun increase(@PathVariable number: Int) = number + 1;
}