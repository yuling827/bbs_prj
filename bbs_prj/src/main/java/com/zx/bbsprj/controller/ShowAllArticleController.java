package com.zx.bbsprj.controller;

import com.zx.bbsprj.entity.Article;
import com.zx.bbsprj.entity.Craft;
import com.zx.bbsprj.entity.User;
import com.zx.bbsprj.repository.*;
import com.zx.bbsprj.service.PagingAndSortingQueryService;
import com.zx.bbsprj.service.ArticelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ShowAllArticleController {

    @Autowired
    private ArticelService articelService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private BlogInfoRepository blogInfoRepository;

    @Autowired
    private CritiqueRepository critiqueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CraftRepository craftRepository;

    @Autowired
    private PagingAndSortingQueryService pagingAndSortingQueryService;

    /**
     * 站点首页
     * @param model
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("/qrySiteArticle")
    public String toSitePage(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        //设置浏览器不缓存该页面--为了保险起见，前后台都加上
        response.setDateHeader("Expires",0);   //for IE
        response.setHeader("Cache-Control","no-store"); //for 火狐或其他
        response.setHeader("Pragma","no-cache");    //for 火狐或其他

        //登录成功后，session存入username账号
        String username = (String) session.getAttribute("username");

        String page = request.getParameter("pno");
        Integer pageSize = 3;

        if (page==null) {
            page = "1";
        }

        Sort sort = new Sort(Sort.Direction.DESC,"createdDate");
        //Pageable pageable = new PageRequest(Integer.parseInt(page)-1,pageSize,sort);
        Pageable pageable = PageRequest.of(Integer.parseInt(page)-1,pageSize,sort);
        Page<com.zx.bbsprj.entity.Article> articles = articleRepository.findAll(pageable);
        List<com.zx.bbsprj.entity.Article> articleList = articles.getContent();
        //总页数
        int totalPage = articles.getTotalPages();
        //总条数
        long totalCount = articles.getTotalElements();

        //如果是登录状态-->查询登录账号的个性设置
        executeByUsername(model, username);

        //将查询并做过分页处理后的数据放到请求域中进行共享
        model.addAttribute("username",username);
        model.addAttribute("articleList",articleList);
        model.addAttribute("pageNo",page);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("totalPage",totalPage);

        return "showAllArticle";
    }

    /**
     * 查询所有已发表的文章
     * @return
     */
    @RequestMapping("/qryAllArticle")
    public String qryAllArticle(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        //设置浏览器不缓存该页面--为了保险起见，前后台都加上
        response.setDateHeader("Expires",0);   //for IE
        response.setHeader("Cache-Control","no-store"); //for 火狐或其他
        response.setHeader("Pragma","no-cache");    //for 火狐或其他

        //登录成功后，session存入username账号
        String username = (String) session.getAttribute("username");

        String page = request.getParameter("pno");
        Integer pageSize = 3;

        if (page==null) {
            page = "1";
        }

        com.zx.bbsprj.entity.Article article = new com.zx.bbsprj.entity.Article();
        article.setUsername(username);

        //第一个参数表示正序、倒序，第二个参数表示排序字段--是个数组
        Sort sort = new Sort(Sort.Direction.DESC,"createdDate");
        //Pageable pageable = new PageRequest(Integer.parseInt(page)-1,pageSize,sort);
        //Spring Boot 2.0版本后，PageRequest这个类不再推荐使用new的方式创建构造器，推荐直接调用它的静态方法
        Pageable pageable = PageRequest.of(Integer.parseInt(page)-1,pageSize,sort);
        Page<com.zx.bbsprj.entity.Article> articles = pagingAndSortingQueryService.findAll(article, pageable);
        System.out.println(articles);
        List<com.zx.bbsprj.entity.Article> articleList = articles.getContent();
        //总页数
        int totalPage = articles.getTotalPages();
        //总条数
        long totalCount = articles.getTotalElements();

        //如果是登录状态-->查询登录账号的个性设置
        executeByUsername(model, username);

        //将查询并做过分页处理后的数据放到请求域中进行共享
        model.addAttribute("username",username);
        model.addAttribute("articleList",articleList);
        model.addAttribute("pageNo",page);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("totalPage",totalPage);

        return "showAllArticle";

    }

    /**
     * 查看某篇文章
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/showArticle")
    public String showArticle(HttpServletRequest request, Model model, HttpSession session, HttpServletResponse response) {

        //设置浏览器不缓存该页面--为了保险起见，前后台都加上
        response.setDateHeader("Expires",0);   //for IE
        response.setHeader("Cache-Control","no-store"); //for 火狐或其他
        response.setHeader("Pragma","no-cache");    //for 火狐或其他

        //不是分页插件做的页码跳转--page值为null
        String page = request.getParameter("pno");
        Integer pageSize = 2;
        Integer id = Integer.valueOf(request.getParameter("id"));
        //根据ID定位到某一篇博客
        //使用Spring Data JPA定位到文章
        Optional<com.zx.bbsprj.entity.Article> optional = articleRepository.findById(id);
        com.zx.bbsprj.entity.Article article = optional.get();
        if (page==null) {
            page = "1";

            //获得当前用户的IP
            String IP = request.getRemoteAddr();
            //获得当前系统时间
            Date time = new Date();
            //判断当前IP用户，在当天有没有点击过，如果点击过，则点击量不变，否则，点击量加1，并保存记录
            if (!articelService.isVistor(id,IP,time)) {
                //没有点击记录
                article.setHasread(article.getHasread()+1);
                //更新点击量
                articleRepository.saveAndFlush(article);
            }

        }

        //登录成功后，session存入username账号
        String username = (String) session.getAttribute("username");
        //如果是登录状态-->查询登录账号的个性设置
        executeByUsername(model, username);

        com.zx.bbsprj.entity.Critique critique = new com.zx.bbsprj.entity.Critique();
        critique.setAid(id);

        //使用Spring Data JPA提供的排序分页功能
        Sort sort = new Sort(Sort.Direction.DESC,"createdDate");
        Pageable pageable = PageRequest.of(Integer.parseInt(page)-1,pageSize,sort);
        Page<com.zx.bbsprj.entity.Critique> critiques = pagingAndSortingQueryService.findAll(critique, pageable);
        System.out.println(critiques);
        List<com.zx.bbsprj.entity.Critique> comments = critiques.getContent();
        long totalCount = critiques.getTotalElements();
        int totalPages = critiques.getTotalPages();

        model.addAttribute("username",username);
        model.addAttribute("comments",comments);
        model.addAttribute("pageNo",page);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("totalPage",totalPages);
        model.addAttribute("article",article);

        return "showArticle";

    }

    /**
     * 个性化设置页面跳转
     * @param model
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("/editBlogInfo")
    public String editBlogInfo(Model model,HttpSession session,HttpServletResponse response) {
        //设置浏览器不缓存该页面--为了保险起见，前后台都加上
        response.setDateHeader("Expires",0);   //for IE
        response.setHeader("Cache-Control","no-store"); //for 火狐或其他
        response.setHeader("Pragma","no-cache");    //for 火狐或其他

        //登录成功后，session存入username账号
        String username = (String) session.getAttribute("username");
        //查询登录账号的个性设置
        executeByUsername(model, username);
        model.addAttribute("username",username);

        return "editBlogInfo";
    }

    /**
     * 个性化设置
     * @param blogtitle
     * @param idiograph
     * @param session
     * @return
     */
    @PostMapping("/editBlogInfo")
    public String editBlogInfo(@RequestParam("blogtitle")String blogtitle,@RequestParam("idiograph")String idiograph,HttpSession session) {
        //账号
        String username = (String) session.getAttribute("username");

        com.zx.bbsprj.entity.BlogInfo blogInfo = blogInfoRepository.findByUsername(username);
        if (blogInfo==null) {
            //新增操作
            blogInfo = new com.zx.bbsprj.entity.BlogInfo();
            blogInfo.setUsername(username);
            blogInfo.setBlogtitle(blogtitle);
            blogInfo.setIdiograph(idiograph);
            com.zx.bbsprj.entity.BlogInfo info = blogInfoRepository.save(blogInfo);
        } else {
            //更新操作
            blogInfo.setBlogtitle(blogtitle);
            blogInfo.setIdiograph(idiograph);
            //更新也是根据主键来作更新--update xxx xxx where id = xxx
            com.zx.bbsprj.entity.BlogInfo saveAndFlush = blogInfoRepository.saveAndFlush(blogInfo);
        }

        //修改成功后重新刷新个性化设置页面
        return "redirect:/editBlogInfo";

    }

    /**
     * 写博客页面跳转
     * @param model
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("/addArticle")
    public String addArticle(Model model,HttpSession session,HttpServletResponse response) {

        //设置浏览器不缓存该页面--为了保险起见，前后台都加上
        response.setDateHeader("Expires",0);   //for IE
        response.setHeader("Cache-Control","no-store"); //for 火狐或其他
        response.setHeader("Pragma","no-cache");    //for 火狐或其他

        //登录成功后，session存入username账号
        String username = (String) session.getAttribute("username");

        User user = userRepository.findByUsername(username);
        Integer userId = user.getId();

        //查询登录账号的个性设置
        executeByUsername(model, username);
        model.addAttribute("username",username);
        model.addAttribute("userId",userId);

        return "addArticle";
    }

    /**
     * 根据账号查询个性设置
     * @param model
     * @param username
     */
    private void executeByUsername(Model model, String username) {
        if (username!=null) {
            //使用Spring Data JPA对单表进行CRUD操作
            com.zx.bbsprj.entity.BlogInfo blogInfo = blogInfoRepository.findByUsername(username);
            if (blogInfo!=null) {
                String blogtitle = blogInfo.getBlogtitle();
                String idiograph = blogInfo.getIdiograph();
                model.addAttribute("blogtitle",blogtitle);
                model.addAttribute("idiograph",idiograph);
            }
        }
    }

    /**
     * 添加文章
     * @param request
     * @param session
     * @return
     */
    @PostMapping("/addArticleConfirm")
    @ResponseBody
    public Map<String,Integer> addArticleConfirm(HttpServletRequest request, HttpSession session) {
        //登录成功后，session存入username账号
        String username = (String) session.getAttribute("username");
        String title = request.getParameter("title");
        String text = request.getParameter("text");

        com.zx.bbsprj.entity.Article article = new com.zx.bbsprj.entity.Article();
        article.setTitle(title);
        article.setContent(text);
        article.setHasread(0);
        article.setUsername(username);
        article.setCreatedDate(new Date());

        //使用Spring Data JPA提供的方法保存实体类
        Article result = articleRepository.save(article);

        Integer id = result.getId();
        Map<String,Integer> map = new HashMap<>();
        map.put("result",id);

        return map;

    }

    /**
     * 发表评论
     * @param request
     * @param session
     * @return
     */
    @PostMapping("/criCommit")
    @ResponseBody
    public Map<String,Integer> criCommit(HttpServletRequest request, HttpSession session) {
        //获取username，不是登录状态-->username=null
        String username = (String) session.getAttribute("username");
        if (username==null) {
            username = "匿名";
        }

        //文章id
        String aid = request.getParameter("aid");
        //评论内容
        String text = request.getParameter("text");

        com.zx.bbsprj.entity.Critique critique = new com.zx.bbsprj.entity.Critique();
        critique.setAid(Integer.parseInt(aid));
        critique.setContent(text);
        critique.setUsername(username);
        critique.setCreatedDate(new Date());

        critiqueRepository.save(critique);

        Map<String,Integer> map = new HashMap<>();
        map.put("aid",Integer.parseInt(aid));

        return map;

    }

    /**
     * 保存草稿
     * @param request
     * @return
     */
    @PostMapping("/saveCraft")
    @ResponseBody
    public Map<String,String> saveCraft(HttpServletRequest request) {
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        String userId = request.getParameter("userId");

        Craft craft = new Craft();
        craft.setTitle(title);
        craft.setContent(text);
        craft.setCreatedDate(new Date());

        Optional<User> optional = userRepository.findById(Integer.parseInt(userId));
        User user = optional.get();

        craft.setUser(user);

        Craft save = craftRepository.save(craft);
        System.out.println("多端主键："+save.getId()+",一端主键："+save.getUser().getId());

        Map<String,String> map = new HashMap<>();
        map.put("result","1");
        return map;
    }

}
