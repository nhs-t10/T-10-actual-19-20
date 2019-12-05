import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.dogecv.core.Rect;
import org.opencv.core.MatOfPoint;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Scalar;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.OpenCVPipeline;
import com.disnodeteam.dogecv.detectors.DogeCVDetector;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraCharacteristics;

// Okay bois, turns out DogeCV is doing something I'm not so I'm just gonna use that
// Imma give up
// - Addison
//sc-sasha's comments ( i try and understand what this does and write my hypothesis)

public static class cvBase {  //sc: probably used to change action once an event happens
	// private enum TrackingStates 
	// {
	// 	SKY_STONE
	// }

	//private TrackingStates currentTrackingState = SKY_STONE; //sc: start by setting to the first state
	private int kernelSize = 10; // An idea, I do not have  //sc: ???????

	//These scalars represent the lightest and darkest a stone can reasonably be to be considered a skystone
	private Scalar skyStoneColorLower = new Scalar(35, 95, 90);
	private Scalar skyStoneColorUpper = new Scalar(45, 105, 100);

	// Assuming the default resolution is 640x480
	// 7 (width) by 4.75 (height) inches
	private float skyStoneAspectRatio = 7 / 4.5; //sc: ?????? this will become 1? not sure what adison is planning with this

	// this method is trying to parse out the individual elements in the object
	private ArrayList<MatOfPoint> FindBounds(Mat image/*sc: this is how the image is stored */) { //sc: new method to find bounds?
		Mat converted = new Mat(); //sc: creates another mat (how the image is stored)
		Imgproc.cvtColor(image, converted, Imgproc.COLOR_RGB2HSV);//sc: ??????????
		Mat element = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,
				new Size(2 * kernelSize + 1, 2 * kernelSize + 1), new Point(kernelSize, kernelSize));//sc: creates a rectangular object based on cv???????
		Mat dilated = new Mat();//sc: dilated Mat
		Imgproc.erode(converted, dilated, element);//sc: makes the mat smaller
		Mat eroded = new Mat();// sc: anotha one
		Imgproc.dilate(dilated, eroded, element);//sc: dilates the Mat's, changes size?
		Mat thresh = new Mat(); //sc: we got more
		if (currentTrackingState == trackingStates.SKY_STONE){ //sc: if the state is the tracking state what do you think lmao
			Core.inRange(skyStoneColorLower, skyStoneColorUpper, thresh); //sc: checks if blocks are close enough to read??
		}
		ArrayList<MatOfPoint> contours = new ArrayList<>();//sc: no idea what mat of point is but it makes a list of them
        Imgproc.findContours(thresh, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);//sc: looks for similarities?
		return contours;
	}


	
	  private void detectObject(){ Mat hsvImage = new Mat(); //sc: anotha method
	  Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV); List<Mat> images = 
	  new ArrayList<>(); Core.split(hsvImage, images);//sc: calls a imgproc method that I do not know what it does
	 
	  Mat blur = new Mat(); Imgproc.medianBlur(images.get(1), blur, 1);//sc: a mat that checks for blur?
	 
	  Mat thresh = new Mat(); Imgproc.threshold(blur, thresh, 40, 255,
	  Imgproc.THRESH_BINARY); // sc: a mat that I have no idea what it does
	 
	  List<MatOfPoint> contours = new ArrayList<>(); Imgproc.findContours(thresh,
	  contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE); } 
	 

	private getExternalWebcam() {
		String[] allCameraIds = CameraManager.getCameraIdList();
		String chosenCameraId;
		for (int i = 0; i < allCameraIds.length; i++){
			CameraCharacteristics currentCharactoristics = CameraManager.getCameraCharacteristics(allCameraIds[i]);
			if (currentCharactoristics.LENS_FACING == 2) {
				// LENS_FACING_EXTERNAL, it is an external webcam
				chosenCameraId = allCameraIds[i];
				break;
			}
		}
		if (chosenCameraId) {
			// We have it
		} else {
			// The webcam isnt connected
			throw new Error("Webcam isn't connected");
		}
	}

	private void getImageFromWebCam() {
		VideoImputGrabber gerald = new VideoImputGraber(0);
	}

	private boolean isSkystone(Mat image) {
		ArrayList<MatOfPoint> contours = FindBounds(image);
		Rect mainRect = Imgproc.boundingRect(contours.get(0));
		int aspectRatio = mainRect.width / mainRect.height;
		if (aspectRatio - skyStoneAspectRatio > -0.1 && aspectRatio - skyStoneAspectRatio < 0.1) {
			return true;
		} else {
			return false;
		}
	}

	private Mat takeWebcamScreencap() {
		FrameGrabber grabber = new VideoInputFrameGrabber(0); // 1 for next camera
		OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();



		return new Mat();// please delete me later we have an error right now destroy him
	}

}


