package com.forms.lightweight.lightweight.form;

import com.forms.lightweight.lightweight.form.dto.CreateFormRequestDto;
import com.forms.lightweight.lightweight.form.dto.UpdateFormRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services/forms")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createForm(@Validated @RequestBody CreateFormRequestDto createFormRequestDto){
        formService.createForm(createFormRequestDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateForm(@PathVariable Long id, @Validated @RequestBody UpdateFormRequestDto updateFormRequestDto) {
        formService.updateForm(id,updateFormRequestDto);
    }

}
