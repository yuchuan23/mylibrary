projects:
	./gradlew projects

build:
	./gradlew clean build

# make project=app1
run:
	./gradlew :$(project):run

clean:
	./gradlew clean

test:
	./gradlew clean test
