import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

name := "genomalysis"

version := "1.0"

// Setup the packager
packageArchetype.java_application

// Enable JAR export for staging
exportJars := true

mainClass in (Compile) := Some("org.genomalysis.ui.FrmMain")

libraryDependencies += "jfree" % "jfreechart" % "1.0.13"
