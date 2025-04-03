package com.opencv.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class FaceDetectionService {

    private final FaceDetectionProcessor  faceDetectionProcessor;

    public byte[] detectFaces(MultipartFile file) throws IOException {

        // imdecode() 함수는 파일 경로가 아닌 메모리 내 바이트 데이터를 직접 읽어서 이미지(Mat)로 변환 즉, 파일을 직접 디스크에 저장하지 않고 메모리에서 즉시 처리 가능
        // 이미지를 저정하지 않고 메모리에서 처리 할 수 있도록 하기 위해 byte[] 형식으로 변환 즉, 파일을 저장하지 않고 바로 OpenCV에서 처리하려면 byte[]를 사용
        Mat image = Imgcodecs.imdecode(new MatOfByte(file.getBytes()), Imgcodecs.IMREAD_COLOR);

        Mat processedImage = faceDetectionProcessor.detectFaces(image);

        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", processedImage, buffer);
        return buffer.toArray();

    }

}
