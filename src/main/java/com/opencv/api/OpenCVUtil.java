package com.opencv.api;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Slf4j
@Component
public class OpenCVUtil implements FaceDetectionProcessor{

    private CascadeClassifier faceCascade;

    @PostConstruct
    public void init() {
        try {
            log.info("=================== OpenCV 라이브러리 로드 시작 ===================");
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            log.info("=================== OpenCV 라이브러리 로드 성공 = {} ============", Core.VERSION);
        } catch (UnsatisfiedLinkError e) {
            throw new RuntimeException("OpenCV 라이브러리를 로드할 수 없습니다. 환경 변수를 확인하세요.", e);
        }
    }

    @PostConstruct
    public void loadCascade() {
        try {
            String cascadePath = "C:/Class/MyOpencv/src/main/java/com/opencv/global/LbpCascade.xml";
            faceCascade = new CascadeClassifier(cascadePath);

            if (faceCascade.empty()) {
                throw new IOException("LBP Cascade 파일을 로드할 수 없습니다.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading LBP Cascade file", e);
        }
    }


    @Override
    public Mat detectFaces(Mat image) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(grayImage, faces, 1.1, 3, 0, new Size(30, 30), new Size());

        log.info("찾은 얼굴 수 = {}",faces.toArray().length);

        for (Rect face : faces.toArray()) {
            Imgproc.rectangle(image, face.tl(), face.br(), new Scalar(0, 0, 255), 2);
        }

        return image;
    }

}
