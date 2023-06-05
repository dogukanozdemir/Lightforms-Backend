package com.forms.lightweight.lightweight.response;

import com.forms.lightweight.lightweight.response.repository.FormResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final FormResponseRepository formResponseRepository;

    public void submitResponse(String formToken){

    }
}
