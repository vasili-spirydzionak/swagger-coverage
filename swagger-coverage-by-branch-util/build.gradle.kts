plugins {
    java
    `java-library`
    application
}

description = "Swagger-coverage Commandline Util"

application {
    mainClassName = "com.github.viclovsky.swagger.coverage.branch.util.CommandLineUtil"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":swagger-coverage-by-branch"))
    api(project(":swagger-coverage-commons"))
    implementation("io.swagger:swagger-compat-spec-parser")
    implementation("org.slf4j:slf4j-simple")
    implementation("log4j:log4j")
    implementation("com.beust:jcommander")
    testImplementation("junit:junit")
    testImplementation("org.hamcrest:hamcrest-all")
}