To compile on server:
javac -cp .:jopt-simple-5.0.2.jar PathFinderTester.java map/*.java pathFinder/*.java

Windows:
javac -cp .;jopt-simple-5.0.2.jar PathFinderTester.java map/*.java pathFinder/*.java

To run on server, using example1 and all the optional files, apart from output file, specified:
java -cp .:jopt-simple-5.0.2.jar PathFinderTester -v -t terrain1.para -w waypoints1.para example1.para

Windows:
java -cp .;jopt-simple-5.0.2.jar PathFinderTester -v -t terrain1.para -w waypoints1.para example1.para

test:
java -cp .;jopt-simple-5.0.2.jar PathFinderTester -v -w D:\wp.para D:\graph.para

python2 assign2TestScript.py -v -s ourimp my tests/test1.para tests/test2.para tests/test3.para tests/test4.para tests/test5.para tests/test6.para tests/test7.para tests/test8.para tests/testm9.para tests/testm10.para tests/testm11.para tests/testm12.para tests/test13.para tests/test14.para tests/test15.para tests/test16.para