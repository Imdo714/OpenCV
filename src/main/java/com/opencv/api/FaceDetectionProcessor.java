package com.opencv.api;

import org.opencv.core.Mat;

public interface FaceDetectionProcessor {

    Mat detectFaces(Mat image);
}
