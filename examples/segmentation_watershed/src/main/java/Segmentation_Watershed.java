
// this source code is based on http://opencv-java-tutorials.readthedocs.io/en/latest/07-image-segmentation.html
// and http://stackoverflow.com/questions/11435974/watershed-segmentation-opencv-xcode/11441676#11441676 (OpenCV 2 Computer Vision Application Programming Cookbook)


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Size;
import org.opencv.core.Point;
import org.opencv.core.Scalar;


import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Segmentation_Watershed
{

    	static {

        	System.load("/opt/share/OpenCV/java/libopencv_java320.so");
        	//System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    	}

	public static void main(String[] args) throws Exception {
        	new Segmentation_Watershed().run(args);
    	}
	
    	public void run( String[] args )
    	{
        	
        	Mat testImage = Imgcodecs.imread("src/main/resources/apple_1.png", Imgcodecs.CV_LOAD_IMAGE_COLOR);
		int n_channels = testImage.channels();
	
		System.out.println("channels = " + n_channels);	
			
		if(testImage.empty()){
			System.out.println("image is empty");
		} else {
			System.out.println("image is NOT empty");
		}

		Mat grayImage = new Mat();
		Mat binaryImage = new Mat();
		Imgproc.cvtColor(testImage, grayImage, Imgproc.COLOR_BGR2GRAY);
		
		//int thresh_type = Imgproc.THRESH_BINARY;
		int thresh_type = Imgproc.THRESH_BINARY_INV;

		Imgproc.threshold(grayImage, binaryImage, 110, 255, thresh_type);

		Mat foreground = new Mat();
		Imgproc.erode(binaryImage, foreground, new Mat(), new Point(-1, -1), 3);

		Mat background = new Mat();
		Imgproc.dilate(binaryImage, background, new Mat(), new Point(-1, -1), 1);
		Imgproc.threshold(background, background, 1, 128, thresh_type);

		Mat Marker = new Mat(binaryImage.size(), CvType.CV_8U);

		Core.add(background, foreground, Marker);

		Marker.convertTo(Marker, CvType.CV_32S);
		Imgproc.watershed(testImage, Marker);

		Marker.convertTo(Marker, CvType.CV_8U);
		
		Imgcodecs.imwrite("src/main/resources/apple_1_watershed.png", Marker);
		
    	}
}
