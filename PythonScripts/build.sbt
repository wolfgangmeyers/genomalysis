name := "python-scripts"

version := "1.0"

javacOptions ++= Seq("-g:lines,vars,source")

javacOptions in doc := Seq()

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java

libraryDependencies += "jython" % "jython" % "2.2-beta1"