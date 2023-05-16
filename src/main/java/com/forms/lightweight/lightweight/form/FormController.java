package com.forms.lightweight.lightweight.form;

import com.forms.lightweight.lightweight.form.dto.CreateFormRequestDto;
import com.forms.lightweight.lightweight.form.dto.UpdateFormRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services/forms")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

    @PostMapping
    public ResponseEntity createForm(@Validated @RequestBody CreateFormRequestDto createFormRequestDto){
        formService.createForm(createFormRequestDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateForm(@PathVariable Long id, @Validated @RequestBody UpdateFormRequestDto updateFormRequestDto) {
        formService.updateForm(id,updateFormRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

}
