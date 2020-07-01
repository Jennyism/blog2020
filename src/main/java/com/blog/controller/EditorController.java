package com.blog.controller;

import com.baidu.ueditor.ActionEnter;
import com.blog.entity.UeditorEntity;
import com.blog.util.PublicMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Jennyism
 * @date 23/5/2020 下午10:13
 */
@Controller
public class EditorController {

    @Value("${ueditor.uri:127.0.0.1}")
    private String uri;

    @RequestMapping(value = "/ueditor")
    @ResponseBody
    public String ueditor(@RequestParam("action") String param, MultipartFile upfile, HttpServletRequest request, @RequestParam(value = "filepath", required = false) String filepath) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        UeditorEntity ueditor = new UeditorEntity();
        if (param != null & param.equals("config")) {
            PublicMsg.uri = uri;
            System.out.println("=-=======================url:"+uri);
            return PublicMsg.getConfig();
        } else if (param != null & param.equals("uploadimage") || param.equals("uploadscrawl")) {
            if (upfile != null) {
                //{state：”数据状态信息”，url：”图片回显路径”，title：”文件title”，original：”文件名称”，···}
                try {
                    return uploadImg(upfile, request, filepath);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    ueditor.setState("出现异常");
                    return mapper.writeValueAsString(ueditor);
                }
            } else {
                ueditor.setState("文件为空");
                return mapper.writeValueAsString(ueditor);
            }
        } else {
            ueditor.setState("不支持该操作");
            return mapper.writeValueAsString(ueditor);
        }
    }


    @RequestMapping(value = "/imgUpload")
    @ResponseBody
    public UeditorEntity imgUpload(@RequestParam("action") String param, MultipartFile upfile, HttpServletRequest request) {
        UeditorEntity ueditor = new UeditorEntity();
        return ueditor;
    }


    /**
     * 上传图片
     * @param file
     * @param request
     * @param filepath
     * @return
     * @throws IOException
     */
    public String uploadImg(MultipartFile file,
                            HttpServletRequest request, String filepath) throws IOException {
        UeditorEntity ueditor = new UeditorEntity();
        // 保存
        Map<String, String> map = null;
        String uuid = UUID.randomUUID().toString().trim();
        String fileN=file.getOriginalFilename();
        int index=fileN.indexOf(".");
        String fileName=uuid+fileN.substring(index);
        try {
            File fileMkdir=new File(EditorController.class.getClassLoader().getResource("static").getPath()+"\\images");
            if(!fileMkdir.exists()) {
                fileMkdir.mkdir();
            }
            //定义输出流 将文件保存在D盘    file.getOriginalFilename()为获得文件的名字
            FileOutputStream os = new FileOutputStream(fileMkdir.getPath()+"\\"+fileName);
            InputStream in = file.getInputStream();
            int b = 0;
            while((b=in.read())!=-1){ //读取文件
                os.write(b);
            }
            os.flush(); //关闭流
            in.close();
            os.close();
            map = new HashMap<>();
            map.put("fileName",fileMkdir.getPath()+"\\"+fileName);
            map.put("imgUrl",":8001/images/"+fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (map != null) {
            ueditor.setState("SUCCESS");
            ueditor.setTitle(map.get("fileName"));
            ueditor.setOriginal(map.get("fileName"));
            ueditor.setUrl(map.get("imgUrl"));
        } else {
            ueditor.setState("ERROR:上传失败");
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ueditor);
    }
}
