package com.zx.bbsprj.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/files")
@PropertySource(value = {"classpath:ckeditor.properties"})
public class FilesController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${ckeditor.storage.image.path}")
    private String ckeditorStorageImagePath;

    @RequestMapping("/upload/image")
    @ResponseBody
    public String uploadImage(@RequestParam("upload")MultipartFile file,String CKEditorFuncNum) throws IOException {

        if (!file.isEmpty()) {
            //chrome浏览器通过getOriginalFilename()即可获取上传时的文件名
            String fileName = file.getOriginalFilename();
            //IE浏览器通过getOriginalFilename()获取到的时上传时文件的全路径
            fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);

            String prefixName = fileName.substring(0,fileName.lastIndexOf("."));
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            //判断上传文件是否为图片
            if (!StringUtils.equalsIgnoreCase(suffixName,".jpg")
                    && !StringUtils.equalsIgnoreCase(suffixName,".jpeg")
                    && !StringUtils.equalsIgnoreCase(suffixName,".bmp")
                    && !StringUtils.equalsIgnoreCase(suffixName,".gif")
                    && !StringUtils.equalsIgnoreCase(suffixName,".png")) {
                logger.error("Incorrect format of the uploading file -- "+suffixName);
                StringBuffer sb = new StringBuffer();
                sb.append("<script type=\"text/javascript\">");
                sb.append("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" + "/image/" + "','');");
                sb.append("alert(\"上传格式错误，请重新上传...\")");
                sb.append("</script>");
                return sb.toString();
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String nowDate = sdf.format(new Date());

                //图片名加上时间戳
                String newFileName = prefixName+nowDate+suffixName;
                FileUtils.copyInputStreamToFile(file.getInputStream(),new File(ckeditorStorageImagePath+newFileName));
                StringBuffer sb = new StringBuffer();
                sb.append("<script type=\"text/javascript\">");
                sb.append("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" + "/image/" + newFileName + "','')");
                sb.append("</script>");
                return sb.toString();
            }
        } else {
            //理论上，未选择文件的情况下，上传到服务器的按钮不会触发该段逻辑
            logger.info("You failed to upload file,because the file was empty");
            StringBuffer sb = new StringBuffer();
            sb.append("<script type=\"text/javascript\">");
            sb.append("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" + "/image/" + "','');");
            sb.append("alert(\"请先选择文件...\")");
            sb.append("</script>");
            return sb.toString();
        }

    }

}
