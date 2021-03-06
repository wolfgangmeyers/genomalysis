import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

name := "genomalysis"

version := "1.0"

// Setup the packager
packageArchetype.java_application

// Enable JAR export for staging
exportJars := true

mainClass in (Compile) := Some("org.genomalysis.ui.FrmMain")

javacOptions ++= Seq("-g:lines,vars,source")

javacOptions in doc := Seq()

libraryDependencies += "jfree" % "jfreechart" % "1.0.13"

libraryDependencies += "com.google.code.gson" % "gson" % "2.2.4"

libraryDependencies += "commons-io" % "commons-io" % "2.4"

libraryDependencies += "jython" % "jython" % "2.2-beta1"

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java
