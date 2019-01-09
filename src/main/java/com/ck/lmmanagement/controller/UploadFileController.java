package com.ck.lmmanagement.controller;

import com.ck.lmmanagement.domain.ResultData;
import com.ck.lmmanagement.domain.UploadFile;
import com.ck.lmmanagement.exception.MyException;
import com.ck.lmmanagement.service.UploadFileService;
import com.ck.lmmanagement.util.UploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author 01378803
 * @date 2019/1/4 11:07
 * Description  :
 */
@RestController
@RequestMapping("/upload")
public class UploadFileController {
    private final static Logger logger = LoggerFactory.getLogger(UploadFileController.class);
    @Autowired
    UploadFileService uploadFileService;
    @Value("${com.filePath.img}")
    private String imagePath;
    /**
     * 图片上传接口
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public ResultData uploadImg(@RequestParam("multipartFile") MultipartFile multipartFile){
        if(multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())){
            return new ResultData("fail", "上传文件NULL");
        }
        String contentType = multipartFile.getContentType();
        if(!contentType.contains("")){
            return new ResultData("fail", "上传文件格式错误");
        }
        String rootFileName = multipartFile.getOriginalFilename();
        logger.info("上传图片：name={},type={}",rootFileName, contentType);
        try {
            String fileName = UploadUtil.uploadFile(multipartFile, imagePath, rootFileName);
            UploadFile uploadFile = new UploadFile();
            uploadFile.setFileName(rootFileName);
            uploadFile.setFilePath(File.separator + fileName);
            uploadFile.setFileType(multipartFile.getContentType());
            uploadFile.setFileSize(String.valueOf(multipartFile.getSize()));
            uploadFile = uploadFileService.saveForm(uploadFile);
            if(uploadFile.isEnableFlag()){
                return new ResultData(uploadFile);
            }else{
                return new ResultData("fail", "上传失败");
            }
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            logger.error("文件上传失败" + sw.toString());
            return new ResultData("fail", "上传失败");
        } catch (MyException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            logger.error("文件上传失败" + sw.toString());
            return new ResultData("fail", "上传失败");
        }
    }

    /**
     * 删除图片接口
     * @param id 附件id
     * @return
     */
    @RequestMapping(value = "/deleteUploadImg", method = RequestMethod.POST)
    public ResultData deleteUploadImg(@RequestParam("id") Long id){
        UploadFile uploadFile = uploadFileService.findDetailById(id);
        if(uploadFile != null){
            File file = new File(imagePath + uploadFile.getFilePath());
            if(!file.exists()){
                return new ResultData("fail", "删除的图片不存在");
            }
            try {
                // 删除数据库中的数据
                uploadFileService.deleteForm(id);
                // 删除文件夹中的图片
                UploadUtil.deleteFile(file);
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));
                logger.error("文件删除失败" + sw.toString());
                return new ResultData("fail", "删除失败");
            }
            return new ResultData("删除成功");
        }else{
            return new ResultData("fail", "找不到对应信息");
        }
    }

}
