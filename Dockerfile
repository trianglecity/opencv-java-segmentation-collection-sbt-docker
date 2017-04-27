FROM ubuntu:16.04


RUN	apt-get update -y
RUN	apt-get install -y software-properties-common
RUN	apt-get update

RUN	apt-get install -y default-jdk 

RUN	apt-get install -y maven
	
RUN 	apt-get install -y python3-pip

RUN	apt-get install -y libssl-dev && \
	apt-get install -y libffi-dev && \
	apt-get install -y python3-dev && \
	apt-get install -y python3-venv 

RUN	apt-get install -y curl && \
	apt-get install -y git && \
	apt-get install -y iptables && \
	apt-get install -y less && \
	apt-get install -y vim && \
	apt-get install -y vim-common && \
	apt-get install -y tar && \
	apt-get install -y zip && \
	apt-get install -y unzip

RUN	apt-get install -y build-essential && \
 	apt-get install -y apt-utils && \
	apt-get install -y automake && \
	apt-get install -y cmake && \
	apt-get install -y libprotobuf-dev && \
	apt-get install -y gcc && \
	apt-get install -y gcc-4.9 && \
	apt-get install -y gcc-4.8 && \
	apt-get install -y g++ && \
	apt-get install -y g++-4.9 && \
	apt-get install -y g++-4.8 && \
	apt-get install -y gcc-multilib && \
	apt-get install -y libgomp1 && \
	apt-get install -y pkg-config && \
	apt-get install -y sphinx-common && \
	apt-get install -y gfortran && \
	apt-get install -y maven 

RUN	apt-get install -y yasm  && \
	apt-get install -y libxext-dev  && \
	apt-get install -y libfreetype6-dev  && \
	apt-get install -y libsdl2-dev  && \
	apt-get install -y libtheora-dev  && \
	apt-get install -y libtool  && \
	apt-get install -y libva-dev  && \
	apt-get install -y libvdpau-dev  && \
	apt-get install -y libvorbis-dev  && \
	apt-get install -y libxcb1-dev  && \
	apt-get install -y libxcb-shm0-dev  && \
	apt-get install -y libxcb-xfixes0-dev  && \
	apt-get install -y zlib1g-dev 

RUN 	apt-get install -y libgtk-3-dev && \
	apt-get install -y libavcodec-dev && \
	apt-get install -y libavformat-dev && \
	apt-get install -y libavutil-dev

RUN echo 'alias python="/usr/bin/python3"' >> ~/.bashrc 

RUN 	apt-get install -y python-scipy && \
	apt-get install -y python-numpy && \
	apt-get install -y python3-scipy && \
	apt-get install -y python3-numpy

RUN 	apt-get install -y freeglut3 && \ 
	apt-get install -y freeglut3-dev && \ 
	apt-get install -y binutils && \
	apt-get install -y libgtkglext1 && \
	apt-get install -y libgtkglext1-dev && \
	apt-get install -y libgl1-mesa-dev && \
	apt-get install -y libgles2-mesa-dev  && \
	apt-get install -y mesa-common-dev && \
	apt-get install -y libgl1-mesa-dri && \
	apt-get install -y libgles1-mesa-dev && \
	apt-get install -y libglu1-mesa-dev && \
	apt-get install -y libglw1-mesa-dev && \
	apt-get install -y libglew-dev && \
	apt-get install -y mesa-utils && \
	apt-get install -y libglapi-mesa
	
RUN	apt-get install -y libvtk6-dev && \
	apt-get install -y libvtk6-qt-dev

RUN	apt-get install -y libswscale-dev && \
	apt-get install -y libtbb2 && \
	apt-get install -y libtbb-dev && \
	apt-get install -y libjpeg-dev && \
	apt-get install -y libpng-dev && \
	apt-get install -y libtiff-dev && \
	apt-get install -y libjasper-dev 
	

RUN	apt-get install -y wget && \
	mkdir scala-2.11.8 && \ 	
	wget http://downloads.lightbend.com/scala/2.11.8/scala-2.11.8.tgz && \
	tar -zxvf ./scala-2.11.8.tgz && \
	mkdir /usr/lib/scala && \
	cp -rp ./scala-2.11.8 /usr/lib/scala/ && \
	ln -s /usr/lib/scala/scala-2.11.8/bin/scala /usr/bin/scala && \
	ln -s /usr/lib/scala/scala-2.11.8/bin/scalac /usr/bin/scalac && \
	scala -version && \
	cd ..

RUN 	apt-get install -y apt-transport-https && \
	echo "deb https://dl.bintray.com/sbt/debian /" |  tee -a /etc/apt/sources.list.d/sbt.list && \
	apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823

RUN	apt-get update && \
	apt-get install -y sbt

RUN 	echo "... wait for a while for sbt ..." && \
	sbt -version

RUN	apt-get install -y tree


ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

RUN	git clone https://github.com/opencv/opencv_contrib.git

RUN	git clone https://github.com/opencv/opencv.git --branch master && \
	cd opencv && \
	mkdir build && \
	cd build && \
	cmake -DCMAKE_C_COMPILER=/usr/bin/gcc-4.8 -DCMAKE_CXX_COMPILER=/usr/bin/g++-4.8  -DBUILD_EXAMPLES=on -DCMAKE_INSTALL_PREFIX=/opt  DOPENCV_EXTRA_MODULES_PATH=../../opencv_contrib/modules  .. && \
	make && \
	make install && \
	cd .. && \
	cd ..
	

