package com.ck.lmmanagement.controller;

import com.ck.lmmanagement.domain.IndexQuery;
import com.ck.lmmanagement.domain.PageList;
import com.ck.lmmanagement.service.IndexService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 01378803
 * @date 2019/1/25 9:55
 * Description  :
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private IndexService indexService;

    /**
     * 分页查询
     * @return
     */
    @RequestMapping(value = "/imagePageList", method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView imagePageList(){
        IndexQuery indexQuery = new IndexQuery();
        PageList<IndexQuery> pageList = indexService.getPageList(indexQuery);
        ModelAndView mv = new ModelAndView("lmindex");
        mv.addObject("rows", pageList.getRows());
        mv.addObject("total", pageList.getTotal());
        return mv;
    }
}
