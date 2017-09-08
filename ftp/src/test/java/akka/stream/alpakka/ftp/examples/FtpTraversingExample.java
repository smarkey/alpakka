/*
 * Copyright (C) 2016-2017 Lightbend Inc. <http://www.lightbend.com>
 */
package akka.stream.alpakka.ftp.examples;

//#traversing
import akka.NotUsed;
import akka.stream.alpakka.ftp.FtpFile;
import akka.stream.alpakka.ftp.FtpSettings;
import akka.stream.alpakka.ftp.javadsl.Ftp;
import akka.stream.javadsl.Source;

public class FtpTraversingExample {

    public Source<FtpFile, NotUsed> listFiles(String basePath, FtpSettings settings) throws Exception {
        return Ftp.ls(basePath, settings);
    }
}
//#traversing
