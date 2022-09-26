FROM openjdk:11
COPY build/libs/*.jar uniquone_user_img.jar
ENTRYPOINT ["java", "-jar", "uniquone_user_img.jar"]