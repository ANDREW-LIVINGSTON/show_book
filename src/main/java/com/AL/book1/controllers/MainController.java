package com.AL.book1.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.AL.book1.models.Book;
import com.AL.book1.services.BookService;

@Controller
public class MainController {
	private final BookService bookService;
	public MainController(BookService bookService) {
        this.bookService = bookService;
    }
	
	@RequestMapping("/")
	public String index() {
		return "index.jsp";
	}
	
	@RequestMapping("/books")
    public String index(Model model) {
        List<Book> books = bookService.allBooks();
        model.addAttribute("books", books);
        return "/books/books.jsp";
    }
	
	@RequestMapping("/books/new")
    public String newBook(Model model) {
		model.addAttribute("book", new Book());
        return "/books/new.jsp";
    }
    @RequestMapping(value="/books", method=RequestMethod.POST)
    public String create(@Valid @ModelAttribute("book") Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "/books/new.jsp";
        } else {
            bookService.createBook(book);
            return "redirect:/books";
        }
    }
    @RequestMapping("/books/{id}")
    public String showBook(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("book", bookService.findBook(id));
    	return "/books/show.jsp";
    }
}
