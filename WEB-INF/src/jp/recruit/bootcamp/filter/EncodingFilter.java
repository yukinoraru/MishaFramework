package jp.recruit.bootcamp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 全リクエストのエンコーディングに関する設定するフィルター<br>
 *
 * @see RootFilter
 * @see CustomFilterAbstract
 */
public class EncodingFilter extends CustomFilterAbstract {

    @Override
    public void execute(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
    }

}
