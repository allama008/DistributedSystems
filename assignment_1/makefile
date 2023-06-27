JFLAGS = -g
JC = javac
JVM= java 
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
	Computer3.java
MAIN = Computer3
classes: $(CLASSES:.java=.class)
default: classes
run: classes
	$(JVM) $(MAIN) 
clean:
	$(RM) *.class