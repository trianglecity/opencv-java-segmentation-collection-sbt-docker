
// this source code is based (and from) http://opencv-java-tutorials.readthedocs.io/en/latest/07-image-segmentation.html
// 


import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
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

public class Segmentation_BgRemoval
{
    	static {
        	System.load("/opt/share/OpenCV/java/libopencv_java320.so");
        	//System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    	}

	public static void main(String[] args) throws Exception {
        	new Segmentation_BgRemoval().run(args);
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

		Mat hsvImg = new Mat();
		List<Mat> hsvPlanes = new ArrayList<>();
		Mat thresholdImg = new Mat();

		int thresh_type = Imgproc.THRESH_BINARY_INV;
		//int thresh_type = Imgproc.THRESH_BINARY;

		hsvImg.create(testImage.size(), CvType.CV_8U);
		Imgproc.cvtColor(testImage, hsvImg, Imgproc.COLOR_BGR2HSV);
		Core.split(hsvImg, hsvPlanes);

		double threshValue = getHistAverage(hsvImg, hsvPlanes.get(0));
		
		Imgproc.threshold(hsvPlanes.get(0), thresholdImg, threshValue/2, 125.0, thresh_type);

		Imgproc.blur(thresholdImg, thresholdImg, new Size(5, 5));

		Imgproc.dilate(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 1);
		Imgproc.erode(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 3);

		System.out.println("threshValue = " + threshValue);

		Imgproc.threshold(thresholdImg, thresholdImg, threshValue/2, 125.0, Imgproc.THRESH_BINARY);

		Mat foreground = new Mat(testImage.size(), CvType.CV_8UC3, new Scalar(0, 0, 0));
		testImage.copyTo(foreground, thresholdImg);
		
		Imgcodecs.imwrite("src/main/resources/apple_1_bgremoval.png", foreground);
    	}

	private double getHistAverage(Mat hsvImg, Mat hueValues)
	{
		double average = 0.0;
		Mat hist_hue = new Mat();
		
		MatOfInt histSize = new MatOfInt(180);
		List<Mat> hue = new ArrayList<>();
		hue.add(hueValues);
				
		Imgproc.calcHist(hue, new MatOfInt(0), new Mat(), hist_hue, histSize, new MatOfFloat(0, 179));
				
		for (int h = 0; h < 180; h++)
		{
			average += (hist_hue.get(h, 0)[0] * h);
		}
		
		return average = average / hsvImg.size().height / hsvImg.size().width;
	}
}
