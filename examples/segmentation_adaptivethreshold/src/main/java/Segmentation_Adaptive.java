
// 
// please refer to http://docs.opencv.org/2.4/modules/imgproc/doc/miscellaneous_transformations.html


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


public class Segmentation_Adaptive
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
		Mat blurImage = new Mat();
		

		Imgproc.cvtColor(testImage, grayImage, Imgproc.COLOR_BGR2GRAY);

		Imgproc.blur(grayImage, blurImage, new Size(3, 3));
		
		MatOfDouble mu = new MatOfDouble();
		MatOfDouble sigma = new MatOfDouble();
		Core.meanStdDev(blurImage, mu, sigma);

		double mean = mu.get(0,0)[0];
		int integer_mean = (int)mean;

		System.out.println("mean = " + integer_mean);

		Mat dst = new Mat(blurImage.rows(),blurImage.cols(),blurImage.type());
		//adaptiveThreshold(InputArray src, OutputArray dst, double maxValue, int adaptiveMethod, int thresholdType, int blockSize, double C)
		// blockSize, Size of a pixel neighborhood that is used to calculate a threshold value for the pixel 3, 5, 7, and so on

		// C, Constant subtracted from the mean or weighted mean (see the details below). Normally it is positive but may be zero or negative as well
		
		Imgproc.adaptiveThreshold(blurImage, dst, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 3);
		
		Imgcodecs.imwrite("src/main/resources/apple_1_adaptive.png", dst);
    	}
}
