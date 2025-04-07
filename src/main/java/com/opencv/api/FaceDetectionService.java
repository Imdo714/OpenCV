package com.opencv.api;

import org.opencv.core.Mat;

public interface FaceDetectionService {

    Mat detectFaces(Mat image);

}
