addCommandAlias("namaste", "~test-only org.functionalkoans.forscala.Koans")

name := "Scala Draughts"

version := "1.0"

scalaVersion := "2.11.6"

traceLevel := -1

logLevel := Level.Info

// disable printing timing information, but still print [success]
showTiming := false

// disable printing a message indicating the success or failure of running a task
showSuccess := false

// append -deprecation to the options passed to the Scala compiler
scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

// disable updating dynamic revisions (including -SNAPSHOT versions)
offline := true

parallelExecution in Test := false

unmanagedJars in Compile += {
  val ps = new sys.SystemProperties
  val jh = ps("java.home")
  Attributed.blank(file(jh) / "lib/ext/jfxrt.jar")
}

libraryDependencies ++= Seq(
	"org.scalafx" %% "scalafx" % "8.0.92-R10",
	"org.scalatest" %% "scalatest" % "2.2.4" % "test" withSources() withJavadoc()
)
