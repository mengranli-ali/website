package com.lrm.blog.web;

import com.lrm.blog.po.Type;
import com.lrm.blog.service.BlogService;
import com.lrm.blog.service.TypeService;
import com.lrm.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {

    //2.
    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    //1.传递一个ID
    //传递分页
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        //3. show the list - 确保数据足够大
        List<Type> types = typeService.listTypeTop(10000);
        //4. id=-1 - 不知道第一个分类是什么
        if (id == -1){
            id = types.get(0).getId();
        }

        //6.
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);

        //5. 分类页面
        //博客页面列表 - 分页查询
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id); //把ID放进去 - 其他分类为灰色
        return "types";
    }

}
