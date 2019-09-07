package com.lrm.blog.web.admin;

import com.lrm.blog.po.Type;
import com.lrm.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    //注入service
    @Autowired
    private TypeService typeService;

    //指定参数
    //Model - 前端的页面展示模型
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"}, direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){
        //所以要传递pageable这个参数
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    //return to a 新增页面
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/type-input";
    }

    //修改
    //返回到修改
    //@PathVariable - 才可以把id接收过来
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/type-input";
    }


    //Type type - 接收type name
    @PostMapping("/types")
    public String post(@Valid Type type,BindingResult result, RedirectAttributes attributes){
        //新增一个type
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            result.rejectValue("name","nameError","Cannot repeat adding the same type");
        }

        if (result.hasErrors()){
            return "admin/type-input";
        }

        //save type
        Type t = typeService.saveType(type);
        if (t == null){
            //没有保存成功
            attributes.addFlashAttribute("message","Adding type failed");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","Adding type succeeds");
        }
        //*using redirect
        return "redirect:/admin/types";
    }

    //Edit post - 根据ID来修改type
    //Binding result 一定要跟着Type
    //Update type with id 用@PathVariable传过来
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        //校验是否存在
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            result.rejectValue("name","nameError","Cannot repeat adding the same type");
        }
        if (result.hasErrors()){
            return "admin/type-input";
        }
        //若不存在就再更新 update type with id
        Type t = typeService.updateType(id,type);
        if (t == null){
            //没有保存成功
            attributes.addFlashAttribute("message","Update failed");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","Update succeeds");
        }
        //*using redirect
        return "redirect:/admin/types";
    }

    //delete method
    // add RedirectAttributes - 校验
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","Delete done!");
        return "redirect:/admin/types";
    }


}
