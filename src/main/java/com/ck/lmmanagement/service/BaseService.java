package com.ck.lmmanagement.service;



import com.ck.lmmanagement.domain.BaseForm;
import com.ck.lmmanagement.domain.PageList;
import com.ck.lmmanagement.exception.MyException;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/10/28 0028
 * Description :
 */
public interface BaseService<T extends BaseForm> {
    /**
     * 分页查询
     * @param query
     * @return
     */
    PageList<T> getPageList(T query);

    /**
     * 根据条件查询数据
     * @param query
     * @return
     */
    List<T> findData(T query);

    /**
     * 根据id查询数据集合
     * @param id
     * @return
     */
    List<T> findDataById(Long id);

    /**
     * 通过id得到详细信息
     * @param id
     * @return
     */
    T findDetailById(Long id);

    /**
     * 新增
     * @param form
     * @return
     */
    T saveForm(T form) throws MyException;

    /**
     * 更新
     * @param form
     * @return
     */
    T updateForm(T form) throws MyException;

    /**
     * 删除
     * @param form
     * @return
     */
    Integer deleteForm(T form);

    /**
     * 失效
     * @param form
     * @return
     */
    Integer updateToDisable(T form);

    /**
     * 重新置为有效
     * @param form
     * @return
     */
    Integer updateToEnable(T form);
}
