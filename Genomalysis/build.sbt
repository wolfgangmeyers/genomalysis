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

libraryDependencies += "jgoodies" % "looks" % "1.2.2"

libraryDependencies += "com.google.code.gson" % "gson" % "2.2.4"

libraryDependencies += "commons-io" % "commons-io" % "2.4"

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java
