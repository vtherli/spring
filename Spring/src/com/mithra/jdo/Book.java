package com.mithra.jdo;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Book {
  
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String id;

    private String title;

    @Persistent(mappedBy = "book")
    @Element(dependent = "true")
    private List<Chapter> chapters = new ArrayList<Chapter>();

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public List<Chapter> getChapters()
	{
		return chapters;
	}

	public void setChapters(List<Chapter> chapters)
	{
		this.chapters = chapters;
	}

    // getters and setters
}