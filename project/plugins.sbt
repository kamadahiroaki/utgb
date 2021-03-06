addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.1")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.3.0")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.7.1")

addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.3.1")

addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.4.1")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

scalacOptions ++= Seq("-deprecation", "-feature")


