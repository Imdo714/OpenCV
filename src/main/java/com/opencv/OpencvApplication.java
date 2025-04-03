package com.opencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpencvApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpencvApplication.class, args);

		System.out.println("Welcome to OpenCV " + Core.VERSION);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat m  = Mat.eye(3, 3, CvType.CV_8UC1);
		System.out.println("m = " + m.dump());
	}

}
