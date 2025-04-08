package com.opencv;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.image.BufferedImage;

@SpringBootApplication
public class OpencvApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpencvApplication.class, args);

//		기본 카메라 장치 0번 카메라를 사용한다.
		VideoCapture camera = new VideoCapture(0);
		if (!camera.isOpened()) {
			System.out.println("카메라 접속 안됨!!");
			return;
		}

		// 얼굴 검출 분류기 로딩
		CascadeClassifier faceDetector = new CascadeClassifier("C:/Class/MyOpencv/src/main/java/com/opencv/global/haarcascade_frontalface_default.xml");

//		Mat 객체를 생성해 프레임 저장할꺼임
		Mat frame = new Mat();

//		Swing GUI 창 생성 및 닫기
		JFrame window = new JFrame("Face Detection");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		이미지를 보여줄 JLabel을 GUI에 추가하고 창을 표시
		JLabel imageLabel = new JLabel();
		window.getContentPane().add(imageLabel);
		window.setVisible(true);


		while (true) {

//			현재 프레임을 frame 객체에 저장, 읽기 성공시
			if (camera.read(frame)) {

//				얼굴 위치 기억하기 위해 Rect 리스트 생성
				MatOfRect faceDetections = new MatOfRect();
//				탐지된 얼굴들을 face Detections에 저장
				faceDetector.detectMultiScale(frame, faceDetections);

//				감지된 이미지에 사격형 네모 틀 그리기
				for (Rect rect : faceDetections.toArray()) {
					Imgproc.rectangle(frame, new Point(rect.x, rect.y),
							new Point(rect.x + rect.width, rect.y + rect.height),
							new Scalar(0, 255, 0), 2);
				}

//				Mat 객체인 frame을 BufferedImage로 변환하고, ImageIcon으로 감싼다.
				ImageIcon image = new ImageIcon(matToBufferedImage(frame));

//				JLabel에 이미지 출력 → 화면 업데이트, pack()은 컴포넌트 크기에 맞게 창 크기 조정
				imageLabel.setIcon(image);
				window.pack();
			} else {
				System.out.println("frame을 읽지 못하였습니다.!!");
				break;
			}
		}

//		사용한 카메라 자원 해제
		camera.release();
	}

//	Mat객체를 자바 BufferedImage로 변환하는 메서드
	public static BufferedImage matToBufferedImage(Mat matrix) {
//		흑백인지 컬러인지에 따라 이미지 타입 결정
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (matrix.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}

//		이미지 크기와 타입으로 BufferedImage 객체 생성
		BufferedImage image = new BufferedImage(matrix.cols(), matrix.rows(), type);

//		영상의 픽셀 데이터를 바이트 배열로 가져옴
		byte[] data = new byte[matrix.rows() * matrix.cols() * (int) matrix.elemSize()];
		matrix.get(0, 0, data);

//		가져온 픽셀 데이터를 Java 이미지 객체에 복사.
		image.getRaster().setDataElements(0, 0, matrix.cols(), matrix.rows(), data);

		return image;
	}

}
