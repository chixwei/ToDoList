name := "TodoList"
version := "0.1"

// https://mvnrepository.com/artifact/org.scalafx/scalafx
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.192-R14"

// https://mvnrepository.com/artifact/org.scalafx/scalafxml-core-sfx8
libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.5"

// https://mvnrepository.com/artifact/org.apache.derby/derby
libraryDependencies += "org.apache.derby" % "derby" % "10.12.1.1"

// Scala 2.10, 2.11, 2.12
libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc"               % "2.5.2",
  "com.h2database"  %  "h2"                        % "1.4.200",
  "ch.qos.logback"  %  "logback-classic"           % "1.2.3"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

fork := true

