package com.mithra.academy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mithra.jdo.Book;
import com.mithra.jdo.Chapter;
import com.mithra.utils.GsonExclusionStrategy;
import com.mithra.utils.PMF;


@Controller
public class BaseController
{
	@RequestMapping(value="/" ,method = RequestMethod.GET)
	public String welcome(HttpServletRequest request)
	{
		
		request.setAttribute("name", "Vasumithra Therl");
		request.setAttribute("town", "Chennai");
		return "home";
	}
	
	@RequestMapping(value="/login" ,method = RequestMethod.GET)
	public String login(HttpServletRequest request)
	{
		
		return "copy";
	}
	
	@ResponseBody
	@RequestMapping("/saveParent")
	public String saveParent()
	{
		Book b = new Book();
		b.setId(UUID.randomUUID().toString());
		b.setTitle("Radical of free");
		
		
		Chapter c1 = new Chapter();
		c1.setTitle("Intro");
		c1.setNumPages(10);
		c1.setId(KeyFactory.createKey(Chapter.class.getSimpleName(), UUID.randomUUID().toString()));
		c1.setBook(b);
		
		System.out.println(c1.getId().getId());
		
		Chapter c2 = new Chapter();
		c2.setTitle("How free came to existence");
		c2.setNumPages(9);
		c2.setId(KeyFactory.createKey(Chapter.class.getSimpleName(), UUID.randomUUID().toString()));
		c2.setBook(b);
		
		List<Chapter> list = new ArrayList<Chapter>();
		list.add(c1);
		list.add(c2);
		
		b.setChapters(list);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		
//		pm.currentTransaction().begin();
		try
		{
			pm.makePersistent(b);
//			pm.currentTransaction().commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "failure";
		}
		finally
		{
//			if (pm.currentTransaction().isActive())
//			{
//				pm.currentTransaction().rollback();
//			}
			pm.close();
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/getParent")
	public String getParent()
	{
		Gson gson = new GsonBuilder().setExclusionStrategies(new GsonExclusionStrategy()).serializeNulls().create();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try
		{
			Book temp =  pm.getObjectById(Book.class,"d93f7f99-1906-4b53-8dcb-937781c3a695") ;
			
			Book book = pm.detachCopy(temp);
			book.setChapters((List<Chapter>)pm.detachCopyAll(temp.getChapters()));
			
			
			
			String gsonval = gson.toJson(book);
			Gson newson = new Gson();
			Book Bookjson = newson.fromJson(gsonval, Book.class);
			List<Chapter> list = Bookjson.getChapters();
			List<Chapter> newlist = new ArrayList<Chapter>(list);
			
			Environment env = ApiProxy.getCurrentEnvironment();
			String appid = env.getAppId();
			System.out.println("Appid: "+appid);
			
			for(int i =0 ; i< newlist.size();i++)
			{
				Chapter chap = newlist.get(i);
				chap.setNumPages(11);
				System.out.println(chap.getId().getName());
				Key key = KeyFactory.createKey(Chapter.class.getSimpleName(), chap.getId().getName());
				System.out.println(key.getName());
				chap.setId(key);
			}
			Bookjson.setChapters(newlist);
			
			pm.makePersistent(Bookjson);
			
			
			
			return gson.toJson(book);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "failure";
		}
		finally
		{
			pm.close();
		}
	}
	
}
