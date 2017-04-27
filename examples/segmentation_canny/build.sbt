name := "segmentation_canny"
version := "1.0"
scalaVersion := "2.11.8"
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

javaOptions in run += "-Djava.library.path=/opt/share/OpenCV/java"

libraryDependencies += "commons-io" % "commons-io" % "2.5"

resolvers += "Local Maven Repository" at "file:///root/.m2/repository"

autoScalaLibrary := false
