name := "FnDm"

version := "0.1"

scalaVersion := "2.12.14"

// spark version
val sparkVersion = "3.1.2"

idePackagePrefix := Some("com.testing.biadko")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion //% "provided"
)