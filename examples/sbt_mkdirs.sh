#!/bin/sh
mkdir -p src/main/java
mkdir -p src/main/resources
mkdir -p src/main/scala

mkdir -p src/test/java
mkdir -p src/test/resources
mkdir -p src/test/scala

mkdir lib project target


echo 'name := "segmentation_canny"
version := "1.0"
scalaVersion := "2.11.8"
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

javaOptions in run += "-Djava.library.path=/opt/share/OpenCV/java"

libraryDependencies += "commons-io" % "commons-io" % "2.5"

resolvers += "Local Maven Repository" at "file:///root/.m2/repository"

autoScalaLibrary := false' > build.sbt


