/*
 * Copyright (C) 2016-2017 Lightbend Inc. <http://www.lightbend.com>
 */
package akka.stream.alpakka.s3.scaladsl

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import org.scalatest.{FlatSpecLike, Matchers}
import akka.stream.alpakka.s3.Proxy
import scala.collection.JavaConverters._

/*
 * Copyright (C) 2016-2017 Lightbend Inc. <http://www.lightbend.com>
 */
class S3ClientSpec extends FlatSpecLike with Matchers {
  it should "reuse application config from actor system" in {
    val config = ConfigFactory.parseMap(
      Map(
        "akka.stream.alpakka.s3.proxy.host" -> "localhost",
        "akka.stream.alpakka.s3.proxy.port" -> 8001,
        "akka.stream.alpakka.s3.proxy.secure" -> false,
        "akka.stream.alpakka.s3.path-style-access" -> true,
        "akka.stream.alpakka.s3.list-bucket-version" -> 2
      ).asJava
    )
    implicit val system = ActorSystem.create("s3", config)
    implicit val materializer = ActorMaterializer()
    val client = S3Client()
    client.s3Settings.proxy shouldBe Some(Proxy("localhost", 8001, "http"))
    client.s3Settings.pathStyleAccess shouldBe true
    client.s3Settings.listBucketVersion shouldBe 2
  }

  it should "default to list-bucket v2" in {
    val config = ConfigFactory.parseMap(
      Map(
        "akka.stream.alpakka.s3.proxy.host" -> "localhost",
        "akka.stream.alpakka.s3.proxy.port" -> 8001,
        "akka.stream.alpakka.s3.proxy.secure" -> false,
        "akka.stream.alpakka.s3.path-style-access" -> true
      ).asJava
    )
    implicit val system = ActorSystem.create("s3", config)
    implicit val materializer = ActorMaterializer()
    val client = S3Client()
    client.s3Settings.proxy shouldBe Some(Proxy("localhost", 8001, "http"))
    client.s3Settings.pathStyleAccess shouldBe true
    client.s3Settings.listBucketVersion shouldBe 2
  }

  it should "be possible to configure list-bucket to v1" in {
    val config = ConfigFactory.parseMap(
      Map(
        "akka.stream.alpakka.s3.proxy.host" -> "localhost",
        "akka.stream.alpakka.s3.proxy.port" -> 8001,
        "akka.stream.alpakka.s3.proxy.secure" -> false,
        "akka.stream.alpakka.s3.path-style-access" -> true,
        "akka.stream.alpakka.s3.list-bucket-version" -> 1
      ).asJava
    )
    implicit val system = ActorSystem.create("s3", config)
    implicit val materializer = ActorMaterializer()
    val client = S3Client()
    client.s3Settings.proxy shouldBe Some(Proxy("localhost", 8001, "http"))
    client.s3Settings.pathStyleAccess shouldBe true
    client.s3Settings.listBucketVersion shouldBe 1
  }
}
