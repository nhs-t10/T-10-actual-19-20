//import org.opencv.core.Mat;
//import org.opencv.core.Point;
//import org.opencv.core.Size;
//import org.dogecv.core.Rect;
//import org.opencv.core.MatOfPoint;
//
//import java.util.ArrayList;
//
//import org.
//import org.opencv.core.Core;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.core.Scalar;
//
//import android.hardware.camera2.CameraManager;
//import android.hardware.camera2.CameraDevice;
//import android.hardware.camera2.CameraCharacteristics;
//
//public class cvBase {
//	private enum TrackingStates {
//		SKY_STONE
//	}
//
//	private TrackingStates currentTrackingState = SKY_STONE;
//	private int kernelSize = 10; // An idea, I do not have
//
//	private Scalar skyStoneColorLower = new Scalar(35, 95, 90);
//	private Scalar skyStoneColorUpper = new Scalar(45, 105, 100);
//
//	private int skyStoneWidth;
//	private int skyStoneHeight;
//
//	private CameraDevice webCam;
//
//	cvBase() {
//		super();
//	}
//
//	// this method is trying to parse out the individual elements in the object
//	private ArrayList<MatOfPoint> FindBounds(Mat image) {
//		Mat converted = new Mat();
//		Imgproc.cvtColor(image, converted, Imgproc.COLOR_RGB2HSV);
//		Mat element = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,
//				new Size(2 * kernelSize + 1, 2 * kernelSize + 1), new Point(kernelSize, kernelSize));
//		Mat dilated = new Mat();
//		Imgproc.erode(converted, dilated, element);
//		Mat eroded = new Mat();
//		Imgproc.dilate(dilated, eroded, element);
//		Mat thresh = new Mat();
//		if (currentTrackingState == trackingStates.SKY_STONE) {
//			Core.inRange(skyStoneColorLower, skyStoneColorUpper, thresh);
//		}
//		ArrayList<MatOfPoint> contours = new ArrayList<>();
//		Imgproc.findContours(thresh, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//		return contours;
//	}
//
//	/*
//	 * private void detectObject(){ Mat hsvImage = new Mat();
//	 * Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV); List<Mat> images =
//	 * new ArrayList<>(); Core.split(hsvImage, images);
//	 *
//	 * Mat blur = new Mat(); Imgproc.medianBlur(images.get(1), blur, 1);
//	 *
//	 * Mat thresh = new Mat(); Imgproc.threshold(blur, thresh, 40, 255,
//	 * Imgproc.THRESH_BINARY);
//	 *
//	 * List<MatOfPoint> contours = new ArrayList<>(); Imgproc.findContours(thresh,
//	 * contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE); }
//	 */
//
//	private getExternalWebcam() {
//		String[] allCameraIds = CameraManager.getCameraIdList();
//		String chosenCameraId;
//		for (int i = 0; i < allCameraIds.length; i++){
//			CameraCharacteristics currentCharactoristics = CameraManager.getCameraCharacteristics(allCameraIds[i]);
//			if (currentCharactoristics.LENS_FACING == 2) {
//				// LENS_FACING_EXTERNAL, it is an external webcam
//				chosenCameraId = allCameraIds[i];
//				break;
//			}
//		}
//		if (chosenCameraId) {
//			// We have it
//		} else {
//			// The webcam isnt connected
//			throw new Error("Webcam isn't connected");
//		}
//	}
//
//	private void getImageFromWebCam() {
//		VideoImputGrabber gerald = new VideoImputGraber(0);
//	}
//
//	private boolean determineRange(ArrayList<MatOfPoint> contours) {
//		Rect mainRect = Imgproc.boundingRect(contours.get(0));
//		if ((mainRect.width > skyStoneWidth - 5) && (mainRect.width < skyStoneWidth + 5)) {
//			if ((mainRect.height > skyStoneHeight - 5) && (mainRect.height < skyStoneHeight + 5)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private Mat takeWebcamScreencap() {
//		FrameGrabber grabber = new VideoInputFrameGrabber(0); // 1 for next camera
//		OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
//	}
//
//}