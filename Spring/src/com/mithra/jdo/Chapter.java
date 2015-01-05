package com.mithra.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Chapter {
    @PrimaryKey
    @Persistent
    private Key id;

    private String title;
    private int numPages;

    @Persistent
    private Book book;

	public Key getId()
	{
		return id;
	}

	public void setId(Key id)
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

	public int getNumPages()
	{
		return numPages;
	}

	public void setNumPages(int numPages)
	{
		this.numPages = numPages;
	}

	public Book getBook()
	{
		return book;
	}

	public void setBook(Book book)
	{
		this.book = book;
	}
}