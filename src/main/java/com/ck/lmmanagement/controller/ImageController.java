package com.ck.lmmanagement.controller;

import com.ck.lmmanagement.constant.LmEnum;
import com.ck.lmmanagement.domain.Image;
import com.ck.lmmanagement.domain.ResultData;
import com.ck.lmmanagement.exception.MyException;
import com.ck.lmmanagement.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author 01378803
 * @date 2019/1/7 14:02
 * Description  :
 */
@RestController
@RequestMapping("/image")
public class ImageController {
    private final static Logger logger = LoggerFactory.getLogger(ImageController.class);
    @Autowired
    ImageService imageService;
    @RequestMapping(value = "/addImage", method = RequestMethod.POST)
    public ResultData addImage(@RequestBody Image image, HttpServletRequest request){
        try {
            image = imageService.saveForm(image);
            if(image.isEnableFlag()){
                return new ResultData();
            }else{
                return new ResultData("fail", image.getReturnMsg());
            }
        } catch (MyException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            logger.error(e.getMsg() + sw.toString());
            return new ResultData(LmEnum.RETURN_NUM_202.getNum(), "fail", e.getMsg());
        }
    }

}
