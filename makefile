# levytal7
# 312497910 
# Assignment 6

compile: bin
	find src | grep .java > out.txt
	javac -d bin -cp biuoop-1.4.jar @out.txt
	
jar:
	jar cfm ass6game.jar manifest.mf -C bin . -C resources .
	
run:
	java -cp biuoop-1.4.jar:bin:resources Ass6Game
	
bin:
	mkdir bin