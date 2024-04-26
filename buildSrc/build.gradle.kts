plugins{
    `kotlin-dsl`
}
repositories {
    jcenter()
    mavenCentral()
    google()
    maven("https://maven.google.com")

    maven("https://plugins.gradle.org/m2/")
    //maven("http://nexus.gluonhq.com/nexus/content/repositories/releases")
    maven("https://jitpack.io")
    maven("https://mvnrepository.com/")
}
dependencies {
    implementation ("com.google.protobuf:protobuf-gradle-plugin:0.8.8")
    implementation ("org.javafxports:jfxmobile-plugin:1.3.18")//2.0.30")//1.3.11" )
    // implementation ("net.sf.proguard:proguard-gradle:6.0.3")

}