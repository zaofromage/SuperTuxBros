javac -d out/production/SuperTuxBros $(find src -name "*.java")
jar cfm SuperTuxBros.jar manifest.txt -C out/production/SuperTuxBros .
java -jar SuperTuxBros.jar

