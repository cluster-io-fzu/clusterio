package org.west2.clusterio.jsr.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.west2.clusterio.jsr.utils.CodingUtil;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Haechi
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/encode")
    public String encode(MultipartFile file) {

        // 可以传入文件的字节数组，也可以传入文件以及inputStream
        byte[][] shards = CodingUtil.encode(file);

        //获得编码后的字节数组，可以自定义地去储存

        String outputFilePathPrefix = "D:\\javaProject\\jsr\\jsr-test\\repository\\shard";

        // 这边是存储到本地的/repository目录下，以bin二进制文件的形式存储
        for (int i = 0; i < CodingUtil.TOTAL_SHARDS; i++) {
            String outputFilePath = outputFilePathPrefix + i+".bin";

            try (FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {
                outputStream.write(shards[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return "ok";
    }

    @PostMapping("/fileCov")
    public String covFile(MultipartFile [] files){

        // 可以传入文件的字节数组，也可以传入文件数组以及inputStream数组
        // 可以获得编码后的字节二维数组，也可以获得复原文件的字节数组
        // 再根据自己的需求去重新存储所有备份或者只是获得复原的文件
        byte[] fileByteArr = CodingUtil.getFileByteArr(files);

        // 这边是将存储的文件存储到本地的/repository目录下
        try (FileOutputStream outputStream = new FileOutputStream("D:\\javaProject\\jsr\\jsr-test\\repository\\file.png")) {
            outputStream.write(fileByteArr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }
}
