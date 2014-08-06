name := "transmembrane-prediction-filter"

version := "1.0"

javacOptions ++= Seq("-g:lines,vars,source")

javacOptions in doc := Seq()

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java