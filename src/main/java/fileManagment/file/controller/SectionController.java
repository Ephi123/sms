package fileManagment.file.controller;

import fileManagment.file.domain.Response;
import fileManagment.file.requestDto.SectionRequest;
import fileManagment.file.requestDto.UserRequest;
import fileManagment.file.service.SectionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

import static fileManagment.file.util.RequestUtil.getResponse;
import static fileManagment.file.util.RequestUtil.getUrRI;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(path = {"/section"})
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @PostMapping("/register")
    public ResponseEntity<Response> sectionRegister(@RequestBody  SectionRequest section, HttpServletRequest request) {

        var sec = sectionService.createSection(section.getRoom(), section.getBlock(), section.getGrade(), section.getFieldCode());
        return ResponseEntity.created(getUrRI("section/id")).body(getResponse(request, Map.of("section", sec), "section is created ", CREATED));

    }
}