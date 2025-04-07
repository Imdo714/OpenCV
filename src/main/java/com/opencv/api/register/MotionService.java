package com.opencv.api.register;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Service
public class MotionService {

    public boolean detectMotion() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        VideoCapture capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            log.info("카메라 연결 실패!");
            return false;
        }

        Mat prev = new Mat();
        Mat curr = new Mat();
        Mat diff = new Mat();
        Mat threshold = new Mat();

        capture.read(prev);
        Imgproc.cvtColor(prev, prev, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(prev, prev, new Size(21, 21), 0);

        long start = System.currentTimeMillis();
        while ((System.currentTimeMillis() - start) < 5_000) { // 5초간 감지
            if (!capture.read(curr)) break;

            Imgproc.cvtColor(curr, curr, Imgproc.COLOR_BGR2GRAY);
            Imgproc.GaussianBlur(curr, curr, new Size(21, 21), 0);
            Core.absdiff(prev, curr, diff);
            Imgproc.threshold(diff, threshold, 25, 255, Imgproc.THRESH_BINARY);

            int motion = Core.countNonZero(threshold);
            if (motion > 5000) {
                Imgcodecs.imwrite("C:/imgTest/motion.jpg", curr);
                log.info("움직임 감지됨!");
                capture.release();
                return true;
            }

            prev = curr.clone();
        }

        capture.release();
        return false;
    }




}
