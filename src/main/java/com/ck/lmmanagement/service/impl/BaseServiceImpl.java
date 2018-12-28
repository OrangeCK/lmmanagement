package com.ck.lmmanagement.service.impl;

import com.ck.lmmanagement.domain.BaseForm;
import com.ck.lmmanagement.domain.PageList;
import com.ck.lmmanagement.exception.MyException;
import com.ck.lmmanagement.mapper.BaseMapper;
import com.ck.lmmanagement.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/10/28 0028
 * Description :
 */
@Transactional(rollbackFor=Exception.class)
public class BaseServiceImpl<T extends BaseForm> implements BaseService<T> {
    @Autowired
    private BaseMapper<T> baseMapper;
    @Override
    public PageList<T> getPageList(T query) {
        int total = baseMapper.findPageTotal(query);
        List<T> list = baseMapper.findPageData(query);
        return new PageList(total, list);
    }

    @Override
    public List<T> findData(T query) {
        return baseMapper.findData(query);
    }

    @Override
    public List<T> findDataById(Long id) {
        return baseMapper.findDataById(id);
    }

    @Override
    public T findDetailById(Long id) {
        return null;
    }

    @Override
    public T saveForm(T form) throws MyException {
        this.basicForm(form);
        int count = baseMapper.saveForm(form);
        if(count < 1){
            form.setEnableFlag(false);
        }
        return form;
    }

    @Override
    public T updateForm(T form) throws MyException{
        this.basicForm(form);
        int count = baseMapper.updateForm(form);
        if(count < 1){
            form.setEnableFlag(false);
        }
        return form;
    }

    @Override
    public Integer deleteForm(Long id) {
        int count = baseMapper.deleteForm(id);
        return count;
    }

    @Override
    public Integer updateToDisable(Long id) {
        int count = baseMapper.updateToDisable(id);
        return count;
    }

    @Override
    public Integer updateToEnable(T form) {
        return null;
    }

    public void basicForm(T form){
        Date date=new Date();
        form.setCreationBy((long)1);
        form.setCreationDate(date);
        form.setUpdatedBy((long)1);
        form.setUpdatedDate(date);
    }
}
