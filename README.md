
##
## OpenCV java segmentation on Docker
##

NOTICE 1: The segmentation source code is based on (from) http://opencv-java-tutorials.readthedocs.io/en/latest/07-image-segmentation.html (https://github.com/opencv-java/).

NOTICE 2: The watershed segmentation is based on (from) OpenCV 2 Computer Vision Application Programming Cookbook (http://stackoverflow.com/questions/11435974/watershed-segmentation-opencv-xcode/11441676#11441676).

NOTICE 3: build.sbt is used instead of pom.xml.

NOTICE 4: Canny, AdativeThreshold, Background-Removal and watershed are re-produced using sbt.


Please follow the instructions below to run the examples.


[1] download (or git clone) this source code folder.

[2] cd downloaded-source-code-folder.

[3] sudo make BIND_DIR=.  shell

	wait ... wait ... wait ... wait ... then a bash shell will be ready (root@9735ef2f0d04:/#)


[4] root@9735ef2f0d04:/# cd /home

[5] root@9735ef2f0d04:/home# cd opencv/

[6] root@9735ef2f0d04:/home/opencv# cd examples/

[7 canny] root@9735ef2f0d04:/home/opencv/examples# cd segmentation_canny/

[8] root@9735ef2f0d04:/home/opencv/examples/segmentation_canny# cp /opt/share/OpenCV/java/opencv-320.jar ./lib/

[9] root@9735ef2f0d04:/home/opencv/examples/segmentation_canny# sbt sbt-version

[10] the source code looks something like this

	
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


[11] root@9735ef2f0d04:/home/opencv/examples/segmentation_canny# sbt clean compile

[12] root@9735ef2f0d04:/home/opencv/examples/segmentation_canny# sbt clean package

[13] root@9735ef2f0d04:/home/opencv/examples/segmentation_canny# scala -cp ./lib/opencv-320.jar ./target/scala-2.11/segmentation_canny_2.11-1.0.jar

[14] root@9735ef2f0d04:/home/opencv/examples/segmentation_canny# cd ..

[15 adaptive] root@9735ef2f0d04:/home/opencv/examples# cd segmentation_adaptivethreshold/

[16] root@9735ef2f0d04:/home/opencv/examples/segmentation_adaptivethreshold# cp /opt/share/OpenCV/java/opencv-320.jar ./lib/

[17] root@9735ef2f0d04:/home/opencv/examples/segmentation_adaptivethreshold# sbe sbt-version

[18] the source code looks like this


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


[19] root@9735ef2f0d04:/home/opencv/examples/segmentation_adaptivethreshold# sbt clean compile

[20] root@9735ef2f0d04:/home/opencv/examples/segmentation_adaptivethreshold# sbt clean package

[21] root@9735ef2f0d04:/home/opencv/examples/segmentation_adaptivethreshold# scala -cp ./lib/opencv-320.jar ./target/scala-2.11/segmentation_adaptive_2.11-1.0.jar


[22] root@9735ef2f0d04:/home/opencv/examples/segmentation_adaptivethreshold# cd ..

[23 bg removal] root@9735ef2f0d04:/home/opencv/examples# cd segmentation_backgroudremoval/

[24] root@9735ef2f0d04:/home/opencv/examples/segmentation_backgroudremoval# cp /opt/share/OpenCV/java/opencv-320.jar ./lib/

[25] root@9735ef2f0d04:/home/opencv/examples/segmentation_backgroudremoval# sbt sbt-version

[26] the source code looks like this
	
		
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
	
	
	
[27] root@9735ef2f0d04:/home/opencv/examples/segmentation_backgroudremoval#  sbt clean compile

[28] root@9735ef2f0d04:/home/opencv/examples/segmentation_backgroudremoval# sbt clean package

[29] root@9735ef2f0d04:/home/opencv/examples/segmentation_backgroudremoval# scala -cp ./lib/opencv-320.jar ./target/scala-2.11/segmentation_bgremoval_2.11-1.0.jar

[30] root@9735ef2f0d04:/home/opencv/examples/segmentation_backgroudremoval# cd ..

[31 watershed] root@9735ef2f0d04:/home/opencv/examples# cd segmentation_watershed/

[32] root@9735ef2f0d04:/home/opencv/examples/segmentation_watershed# sbt sbt-version

[33] the source code looks like this

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


[34] root@9735ef2f0d04:/home/opencv/examples/segmentation_watershed# sbt clean compile

[35] root@9735ef2f0d04:/home/opencv/examples/segmentation_watershed# sbt clean package

[36] root@9735ef2f0d04:/home/opencv/examples/segmentation_watershed# scala -cp ./lib/opencv-320.jar ./target/scala-2.11/segmentation_watershed_2.11-1.0.jar
