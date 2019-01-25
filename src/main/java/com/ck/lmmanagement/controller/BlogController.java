package com.ck.lmmanagement.controller;

import com.ck.lmmanagement.domain.BlogQuery;
import com.ck.lmmanagement.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 01378803
 * @date 2019/1/25 15:59
 * Description  :
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    /**
     * 分页查询
     * @return
     */
    @GetMapping(value = "/blogDetail/{blogId}")
    public ModelAndView imagePageList(@PathVariable("blogId") Long id){
        BlogQuery blogQuery = blogService.findDetailById(id);
        ModelAndView mv = new ModelAndView("lmblog");
        mv.addObject("blog", blogQuery);
        return mv;
    }
}
