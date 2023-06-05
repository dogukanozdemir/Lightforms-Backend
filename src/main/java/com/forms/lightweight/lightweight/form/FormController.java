package com.forms.lightweight.lightweight.form;

import com.forms.lightweight.lightweight.form.dto.*;
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
    public ResponseEntity<CreateFormResponseDto> createForm(@Validated @RequestBody CreateFormRequestDto createFormRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(formService.createForm(createFormRequestDto));
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

    @GetMapping("/byId/{id}")
    public ResponseEntity<FormContentResponseDto> getFormContentById(@PathVariable Long id){
        return ResponseEntity.ok(formService.getFormContentByFormId(id));
    }

    @GetMapping("/byUuid/{token}")
    public ResponseEntity<FormContentResponseDto> getFormContentByIdentifier(@PathVariable String token){
        return ResponseEntity.ok(formService.getFormContentByFormIdentifier(token));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<PublishFormResponseDto> publishForm(@PathVariable Long id){
        return ResponseEntity.ok(formService.publishForm(id));
    }


}
