package cn.itheima.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageTypeSpecifier;
import javax.management.QueryEval;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.itheima.springmvc.exception.MessageException;
import cn.itheima.springmvc.pojo.Items;
import cn.itheima.springmvc.pojo.QueryVo;
import cn.itheima.springmvc.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	/*
	 * controller 返回值 1. ModelAndView 无敌的 带着数据，返回视图路径 （不推荐使用） 2. String
	 * 返回路径，需要带数据则需要从形参的Model或ModelMap进行设置（官方推荐，解耦思想，数据视图分离） 3. void ajax请求 适合
	 * json格式数据（response） 异步请求使用
	 */

	// 入门查询 第一
	// @RequestMapping：里面放的是请求的url，和用户请求的url进行匹配
	// action可以写也可以不写
	@RequestMapping(value = { "/item/itemlist", "/item/list" })
	public String itemList(Model model) {
		// 创建页面需要显示的商品数据
		List<Items> list = itemService.selectItemsList();

		// 创建ModelAndView，用来存放数据和视图
		// ModelAndView modelAndView = new ModelAndView();
		// 设置数据到模型中
		// modelAndView.addObject("itemList", list);
		// 设置视图jsp，需要设置视图的物理地址
		// modelAndView.setViewName("itemList");

		model.addAttribute("itemList", list);
		return "itemList";

		// return modelAndView;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value = "/item/toEdit")
	public ModelAndView go2ModifyPage(@RequestParam(value = "id", required = false) Integer id, HttpServletRequest req,
			HttpServletResponse resp, HttpSession session, Model model) {
		ModelAndView mv = new ModelAndView("editItem");

		// Integer id = Integer.valueOf(req.getParameter("id"));
		Items items = itemService.selectItemById(id);
		mv.addObject("item", items);

		return mv;
	}

	@RequestMapping(value = "/item/updateitem")
	// public String updateItem(Items items){
	public String updateItem(QueryVo vo, MultipartFile pictureFile) throws IllegalStateException, IOException {
		// 保存图片到E:/upload
		String name = UUID.randomUUID().toString().replace("-", "");

		String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
		pictureFile.transferTo(new File("E:/upload/" + name + "." + ext));

		vo.getItems().setPic(name + "." + ext);
		itemService.updateById(vo.getItems());

		ModelAndView mav = new ModelAndView();
		mav.setViewName("itemList");

		return "redirect:/item/toEdit?id=" + vo.getItems().getId();
//		return "redirect:/item/itemlist";
	}

	@RequestMapping(value = "/item/delete")
	public String deleteItem(Integer[] ids) {
		System.out.println("**************ids:" + Arrays.toString(ids));

		return "redirect:/item/itemlist";
	}

	@RequestMapping(value = "/item/updates", method = { RequestMethod.POST, RequestMethod.GET })
	public String updates(QueryVo vo) {


		return "forward:/item/itemlist";
	}

	@RequestMapping(value = "/item/testexception", method = { RequestMethod.POST, RequestMethod.GET })
	public String testexception() throws MessageException {
		// Integer integer = 1 / 0;
		// return "forward:/item/itemlist";
		if (true) {
			throw new MessageException("预期异常");
		}
		return "redirect:/item/itemlist";
	}

	@RequestMapping(value = "/item/json",method=RequestMethod.POST)
	public @ResponseBody Items testexceptionJson(@RequestBody Items items) {
		items.setName(items.getName()+"-handled");
		items.setDetail(items.getDetail()+"-handled");
		items.setPrice(items.getPrice() + 10000.0f);
		return items;
	}

	@RequestMapping(value = "/loginPage")
	public String loginPage() {
		
		return "login";
	}

	@RequestMapping(value = "/login",method=RequestMethod.POST)
	public String login(String name, HttpSession session) {
		if(null != name && !"".equals(name)){
			session.setAttribute("user", name);
			return "redirect:/item/list";
		}
		return "login";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/loginPage";
	}

}
