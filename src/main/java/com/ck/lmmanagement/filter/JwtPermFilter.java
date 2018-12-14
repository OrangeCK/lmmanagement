//package com.ck.lmmanagement.filter;
//
//import org.apache.shiro.subject.Subject;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
///**
// * @author 01378803
// * @date 2018/12/13 17:03
// * Description  :
// */
//public class JwtPermFilter extends JwtFilter {
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        Subject subject = getSubject(request, response);
//        String[] perms = (String[]) mappedValue;
//        boolean isPermitted = true;
//        if (perms != null && perms.length > 0) {
//            if (perms.length == 1) {
//                if (!subject.isPermitted(perms[0])) {
//                    isPermitted = false;
//                }
//            } else {
//                if (!subject.isPermittedAll(perms)) {
//                    isPermitted = false;
//                }
//            }
//        }
//        return isPermitted;
//    }
//}
