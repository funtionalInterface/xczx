/*
 Navicat Premium Data Transfer

 Source Server         : CloudMongoDB
 Source Server Type    : MongoDB
 Source Server Version : 40400
 Source Host           : haogege.icu:27017
 Source Schema         : xc_media

 Target Server Type    : MongoDB
 Target Server Version : 40400
 File Encoding         : 65001

 Date: 23/10/2020 00:25:13
*/


// ----------------------------
// Collection structure for media_file
// ----------------------------
db.getCollection("media_file").drop();
db.createCollection("media_file");

// ----------------------------
// Documents of media_file
// ----------------------------
db.getCollection("media_file").insert([ {
    _id: "f3fd7615ef44ce284af65040b5f0c12c",
    fileName: "f3fd7615ef44ce284af65040b5f0c12c.avi",
    fileOriginalName: "12-MongoDb入门-基础概念 【www.zxit8.com】.avi",
    filePath: "/f/3/f3fd7615ef44ce284af65040b5f0c12c/",
    absolutePath: "C:/Users/funtion/IdeaProjects/xczx-ui/media/",
    fileUrl: "/f/3/f3fd7615ef44ce284af65040b5f0c12c/hls/f3fd7615ef44ce284af65040b5f0c12c.m3u8",
    fileType: "avi",
    mimeType: "video/avi",
    fileSize: NumberLong("9714688"),
    fileStatus: "301002",
    uploadTime: ISODate("2020-10-14T15:47:29.541Z"),
    processStatus: "303002",
    "mediaFileProcess_m3u8": {
        tslist: [
            "f3fd7615ef44ce284af65040b5f0c12c_00000.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00001.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00002.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00003.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00004.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00005.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00006.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00007.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00008.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00009.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00010.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00011.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00012.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00013.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00014.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00015.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00016.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00017.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00018.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00019.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00020.ts",
            "f3fd7615ef44ce284af65040b5f0c12c_00021.ts"
        ]
    },
    _class: "com.xuecheng.framework.domain.media.MediaFile"
} ]);
db.getCollection("media_file").insert([ {
    _id: "0485c48656a57f766a96531ee5a93b8f",
    fileName: "0485c48656a57f766a96531ee5a93b8f.avi",
    fileOriginalName: "26-页面查询接口测试-Postman 【www.zxit8.com】.avi",
    filePath: "/0/4/0485c48656a57f766a96531ee5a93b8f/",
    absolutePath: "C:/Users/funtion/IdeaProjects/xczx-ui/media/",
    fileUrl: "/0/4/0485c48656a57f766a96531ee5a93b8f/hls/0485c48656a57f766a96531ee5a93b8f.m3u8",
    fileType: "avi",
    mimeType: "video/avi",
    fileSize: NumberLong("9774592"),
    fileStatus: "301002",
    uploadTime: ISODate("2020-10-14T18:40:44.123Z"),
    processStatus: "303002",
    "mediaFileProcess_m3u8": {
        tslist: [
            "0485c48656a57f766a96531ee5a93b8f_00000.ts",
            "0485c48656a57f766a96531ee5a93b8f_00001.ts",
            "0485c48656a57f766a96531ee5a93b8f_00002.ts",
            "0485c48656a57f766a96531ee5a93b8f_00003.ts",
            "0485c48656a57f766a96531ee5a93b8f_00004.ts",
            "0485c48656a57f766a96531ee5a93b8f_00005.ts",
            "0485c48656a57f766a96531ee5a93b8f_00006.ts",
            "0485c48656a57f766a96531ee5a93b8f_00007.ts",
            "0485c48656a57f766a96531ee5a93b8f_00008.ts",
            "0485c48656a57f766a96531ee5a93b8f_00009.ts",
            "0485c48656a57f766a96531ee5a93b8f_00010.ts",
            "0485c48656a57f766a96531ee5a93b8f_00011.ts",
            "0485c48656a57f766a96531ee5a93b8f_00012.ts",
            "0485c48656a57f766a96531ee5a93b8f_00013.ts",
            "0485c48656a57f766a96531ee5a93b8f_00014.ts",
            "0485c48656a57f766a96531ee5a93b8f_00015.ts",
            "0485c48656a57f766a96531ee5a93b8f_00016.ts",
            "0485c48656a57f766a96531ee5a93b8f_00017.ts",
            "0485c48656a57f766a96531ee5a93b8f_00018.ts",
            "0485c48656a57f766a96531ee5a93b8f_00019.ts"
        ]
    },
    _class: "com.xuecheng.framework.domain.media.MediaFile"
} ]);
db.getCollection("media_file").insert([ {
    _id: "2513fa31b8931dd5e01bbe3b4ed1197e",
    fileName: "2513fa31b8931dd5e01bbe3b4ed1197e.avi",
    fileOriginalName: "09-webpack研究-webpack介绍 【www.zxit8.com】.avi",
    filePath: "/2/5/2513fa31b8931dd5e01bbe3b4ed1197e/",
    absolutePath: "C:/Users/funtion/IdeaProjects/xczx-ui/media/",
    fileUrl: "/2/5/2513fa31b8931dd5e01bbe3b4ed1197e/hls/2513fa31b8931dd5e01bbe3b4ed1197e.m3u8",
    fileType: "avi",
    mimeType: "video/avi",
    fileSize: NumberLong("9899520"),
    fileStatus: "301002",
    uploadTime: ISODate("2020-10-14T18:42:27.359Z"),
    processStatus: "303002",
    "mediaFileProcess_m3u8": {
        tslist: [
            "2513fa31b8931dd5e01bbe3b4ed1197e_00000.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00001.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00002.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00003.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00004.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00005.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00006.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00007.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00008.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00009.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00010.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00011.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00012.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00013.ts",
            "2513fa31b8931dd5e01bbe3b4ed1197e_00014.ts"
        ]
    },
    _class: "com.xuecheng.framework.domain.media.MediaFile"
} ]);
db.getCollection("media_file").insert([ {
    _id: "36fa330ca8dc8668770df539cc369b06",
    fileName: "36fa330ca8dc8668770df539cc369b06.avi",
    fileOriginalName: "15-webpack研究-webpack-dev-server-程序调试 【www.zxit8.com】.avi",
    filePath: "/3/6/36fa330ca8dc8668770df539cc369b06/",
    absolutePath: "C:/Users/funtion/IdeaProjects/xczx-ui/media/",
    fileUrl: "/3/6/36fa330ca8dc8668770df539cc369b06/hls/36fa330ca8dc8668770df539cc369b06.m3u8",
    fileType: "avi",
    mimeType: "video/avi",
    fileSize: NumberLong("8766976"),
    fileStatus: "301002",
    uploadTime: ISODate("2020-10-16T07:27:54.408Z"),
    processStatus: "303002",
    "mediaFileProcess_m3u8": {
        tslist: [
            "36fa330ca8dc8668770df539cc369b06_00000.ts",
            "36fa330ca8dc8668770df539cc369b06_00001.ts",
            "36fa330ca8dc8668770df539cc369b06_00002.ts",
            "36fa330ca8dc8668770df539cc369b06_00003.ts",
            "36fa330ca8dc8668770df539cc369b06_00004.ts",
            "36fa330ca8dc8668770df539cc369b06_00005.ts",
            "36fa330ca8dc8668770df539cc369b06_00006.ts",
            "36fa330ca8dc8668770df539cc369b06_00007.ts",
            "36fa330ca8dc8668770df539cc369b06_00008.ts",
            "36fa330ca8dc8668770df539cc369b06_00009.ts",
            "36fa330ca8dc8668770df539cc369b06_00010.ts",
            "36fa330ca8dc8668770df539cc369b06_00011.ts",
            "36fa330ca8dc8668770df539cc369b06_00012.ts",
            "36fa330ca8dc8668770df539cc369b06_00013.ts",
            "36fa330ca8dc8668770df539cc369b06_00014.ts",
            "36fa330ca8dc8668770df539cc369b06_00015.ts",
            "36fa330ca8dc8668770df539cc369b06_00016.ts",
            "36fa330ca8dc8668770df539cc369b06_00017.ts",
            "36fa330ca8dc8668770df539cc369b06_00018.ts",
            "36fa330ca8dc8668770df539cc369b06_00019.ts",
            "36fa330ca8dc8668770df539cc369b06_00020.ts"
        ]
    },
    _class: "com.xuecheng.framework.domain.media.MediaFile"
} ]);
db.getCollection("media_file").insert([ {
    _id: "69a81d4b4b3404887ade5b252a5b8ca6",
    fileName: "69a81d4b4b3404887ade5b252a5b8ca6.avi",
    fileOriginalName: "01-项目概述-功能构架-项目背景 【www.zxit8.com】.avi",
    filePath: "/6/9/69a81d4b4b3404887ade5b252a5b8ca6/",
    absolutePath: "C:/Users/funtion/IdeaProjects/xczx-ui/media/",
    fileUrl: "/6/9/69a81d4b4b3404887ade5b252a5b8ca6/hls/69a81d4b4b3404887ade5b252a5b8ca6.m3u8",
    fileType: "avi",
    mimeType: "video/avi",
    fileSize: NumberLong("28296192"),
    fileStatus: "301002",
    uploadTime: ISODate("2020-10-16T07:37:56.784Z"),
    processStatus: "303002",
    "mediaFileProcess_m3u8": {
        tslist: [
            "69a81d4b4b3404887ade5b252a5b8ca6_00000.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00001.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00002.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00003.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00004.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00005.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00006.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00007.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00008.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00009.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00010.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00011.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00012.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00013.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00014.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00015.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00016.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00017.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00018.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00019.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00020.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00021.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00022.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00023.ts",
            "69a81d4b4b3404887ade5b252a5b8ca6_00024.ts"
        ]
    },
    _class: "com.xuecheng.framework.domain.media.MediaFile"
} ]);
