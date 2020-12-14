/*
 Navicat Premium Data Transfer

 Source Server         : CloudMongoDB
 Source Server Type    : MongoDB
 Source Server Version : 40400
 Source Host           : haogege.icu:27017
 Source Schema         : xc_fs

 Target Server Type    : MongoDB
 Target Server Version : 40400
 File Encoding         : 65001

 Date: 23/10/2020 00:24:53
*/


// ----------------------------
// Collection structure for filesystem
// ----------------------------
db.getCollection("filesystem").drop();
db.createCollection("filesystem");

// ----------------------------
// Documents of filesystem
// ----------------------------
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/wKifgV97G3CAE39IAABjnXSWAr0762.jpg",
    filePath: "group1/M00/00/00/wKifgV97G3CAE39IAABjnXSWAr0762.jpg",
    fileSize: NumberLong("25501"),
    fileName: "QQ图片20201005113159.jpg",
    fileType: "image/jpeg",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/wKifgV98ar2AQ9ChAADcMytyA3E427.jpg",
    filePath: "group1/M00/00/00/wKifgV98ar2AQ9ChAADcMytyA3E427.jpg",
    fileSize: NumberLong("56371"),
    fileName: "QQ图片20201006210028.jpg",
    fileType: "image/jpeg",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/wKifgV98atqADqAMAALq1Lhy1po379.jpg",
    filePath: "group1/M00/00/00/wKifgV98atqADqAMAALq1Lhy1po379.jpg",
    fileSize: NumberLong("191188"),
    fileName: "1.jpg",
    fileType: "image/jpeg",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
db.getCollection("filesystem").insert([ {
    _id: "group1/M00/00/00/wKifgV-Fwp2AccWUAABjnXSWAr0472.jpg",
    filePath: "group1/M00/00/00/wKifgV-Fwp2AccWUAABjnXSWAr0472.jpg",
    fileSize: NumberLong("25501"),
    fileName: "QQ图片20201005113159.jpg",
    fileType: "image/jpeg",
    fileWidth: NumberInt("0"),
    fileHeight: NumberInt("0"),
    _class: "com.xuecheng.framework.domain.filesystem.FileSystem"
} ]);
