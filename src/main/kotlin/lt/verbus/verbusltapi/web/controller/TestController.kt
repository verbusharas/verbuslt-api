package lt.verbus.verbusltapi.web.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/hello")
    fun greet() = "Hi"
}