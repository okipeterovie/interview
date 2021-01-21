
package com.interviewtest.line.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

/**
 * @author Oki-PEter
 */
@Data
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/utility")
public class UtilityController {
//
//    @Autowired
//    private ClassificationRepository classificationRepository;

    
//    @GetMapping(path = "/find/all/classifications")
//    public ResponseEntity<Object> Classifications() {
//        List<Classification> classifications = classificationRepository.findAll();
//        List<ClassificationDto> classificationDtos = new ArrayList<>();
//        classifications.forEach((classification) ->{
//            ClassificationDto classificationDto = new ClassificationDto();
//            classificationDto.setId(classification.getId());
//            classificationDto.setName(classification.getName());
//            classificationDto.setDescription(classification.getDescription());
//
//            classificationDtos.add(classificationDto);
//        });
//        return ResponseEntity.ok(new JsonResponse("See Data Object for Details", classificationDtos));
//    }
    
    
}
