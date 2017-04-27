
// this source code is based on http://opencv-java-tutorials.readthedocs.io/en/latest/07-image-segmentation.html
// please refer to http://docs.opencv.org/2.4/doc/tutorials/imgproc/imgtrans/canny_detector/canny_detector.html


import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Size;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Segmentation_Canny
{

    	static {

        	System.load("/opt/share/OpenCV/java/libopencv_java320.so");
        	//System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

    	}

    	public static void main( String[] args )
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
		Mat detectedEdges = new Mat();
		

		Imgproc.cvtColor(testImage, grayImage, Imgproc.COLOR_BGR2GRAY);

		Imgproc.blur(grayImage, detectedEdges, new Size(5, 5));
		
		MatOfDouble mu = new MatOfDouble();
		MatOfDouble sigma = new MatOfDouble();
		Core.meanStdDev(detectedEdges, mu, sigma);

		double mean = mu.get(0,0)[0];
		int integer_mean = (int)mean;

		System.out.println("mean = " + integer_mean);

		
		int lowThreshold= 20;
		int ratio = 3;
		
		Imgproc.Canny(detectedEdges, detectedEdges, lowThreshold, lowThreshold*ratio);
		
		Imgcodecs.imwrite("src/main/resources/apple_1_canny.png", detectedEdges);
    	}

}
