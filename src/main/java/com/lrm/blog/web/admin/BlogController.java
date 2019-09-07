package com.lrm.blog.web.admin;

import com.lrm.blog.po.Blog;
import com.lrm.blog.po.Type;
import com.lrm.blog.po.User;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    //注入BlogService
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;


    //add Model
    //指定分页大小
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        //初始化一个blog and categories
        model.addAttribute("blog",new Blog());
        model.addAttribute("types", typeService.listType());
        return INPUT;
    }


    //根据ID来修改
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        //初始化一个blog and categories
        model.addAttribute("types",typeService.getType(id));
        Blog blog = blogService.getBlog(id);
        model.addAttribute("blog",blog);
        return INPUT;
    }

    //新增保存的方法
    //Session - 里面有user 要设置user对象
    //RedirectAttribute - attributes 来校验非空
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        //当前登录的用户对象 就要从session里面取
        blog.setUser((User) session.getAttribute("user"));
        //setType
        blog.setType(typeService.getType(blog.getType().getId()));
        //blog.tags
        Blog b;
        if (blog.getId() == null){
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null){
            //没有保存成功
            attributes.addFlashAttribute("message","Operation failed");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","Operation succeeds");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        //先@PathVariable来获取ID
        //传递一个ID
        blogService.deleteBlog(id);
        //传递message
        attributes.addFlashAttribute("message","Delete done!");
        //最终返回这个页面
        return REDIRECT_LIST;
    }

}
