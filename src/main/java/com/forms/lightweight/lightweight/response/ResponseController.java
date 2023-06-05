package com.forms.lightweight.lightweight.response;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services/response")
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService responseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void submitResponse(@RequestParam String formToken){

    }
}
