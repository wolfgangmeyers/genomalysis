name := "genomalysis"

version := "1.0"

scalaVersion := "2.10.0"

lazy val Genomalysis = project

lazy val StandardFilters = project.dependsOn(Genomalysis)

lazy val HydropathyPlot = project.dependsOn(Genomalysis)

lazy val ClustalWTool = project.dependsOn(Genomalysis)