package com.opencv.api.register;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class MotionController {

    @Autowired
    private MotionService motionService;

    @GetMapping("/start")
    public ResponseEntity<String> startMotionDetection() {
        boolean result = motionService.detectMotion();
        return ResponseEntity.ok(result ? "움직임 감지됨! 이미지 저장 완료" : "움직임 없음");
    }

    @GetMapping("/last")
    public ResponseEntity<InputStreamResource> getLastDetectedImage() throws IOException {
        File file = new File("C:/imgTest/motion.jpg");
        if (!file.exists()) return ResponseEntity.notFound().build();

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

}
