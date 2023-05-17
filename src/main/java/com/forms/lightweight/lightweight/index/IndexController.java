package com.forms.lightweight.lightweight.index;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class IndexController {

    @GetMapping()
    public String index(){
        return "Index";
    }
}
