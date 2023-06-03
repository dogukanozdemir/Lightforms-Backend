package com.forms.lightweight.lightweight.form;

import com.forms.lightweight.lightweight.form.dto.CreateFormRequestDto;
import com.forms.lightweight.lightweight.form.dto.FormContentResponseDto;
import com.forms.lightweight.lightweight.form.dto.FormPreviewResponseDto;
import com.forms.lightweight.lightweight.form.dto.UpdateFormRequestDto;
import com.forms.lightweight.lightweight.form.enums.FormState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteForm(@PathVariable Long id){
        formService.deleteForm(id);
    }

    @GetMapping
    public ResponseEntity<List<FormPreviewResponseDto>> getFormByState(@RequestParam FormState state){
        return ResponseEntity.ok(formService.getUserFormPreviewsByState(state));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormContentResponseDto> getFormContent(@PathVariable Long id){
        return ResponseEntity.ok(formService.getFormContents(id));
    }


}
