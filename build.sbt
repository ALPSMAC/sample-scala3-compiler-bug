
val akkaVersion = "2.6.19"
val akkaHttpVersion = "10.2.9"
val akkaHttpCorsVersion      = "1.1.3"
val playJsonVersion          = "2.10.0-RC6"

lazy val root = (project in file(".")).settings(
  scalaVersion := "3.1.2",
  libraryDependencies ++= Seq(
    ("com.typesafe.akka"   %% "akka-stream"           % akkaVersion         withSources()).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka"   %% "akka-actor"            % akkaVersion         withSources()).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka"   %% "akka-actor-typed"      % akkaVersion         withSources()).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka"   %% "akka-protobuf-v3"      % akkaVersion         withSources()).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka"   %% "akka-http"            % akkaHttpVersion     withSources()).cross(CrossVersion.for3Use2_13),
    ("ch.megard"           %% "akka-http-cors"        % akkaHttpCorsVersion withSources()).cross(CrossVersion.for3Use2_13),
    "com.typesafe.play"           %% "play-json"        % playJsonVersion             withSources(),
  )
)
