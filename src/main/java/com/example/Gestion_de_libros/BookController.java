package com.example.Gestion_de_libros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String getAllBooks(Model model){
        model.addAttribute("books", bookRepository.findAll());
        return "book-list";
    }

    @GetMapping("/add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }


    @PostMapping("/add-book")
    public String addBook(@ModelAttribute Book book){
        bookRepository.save(book);
        return "redirect:/";
    }

    /*
    @PostMapping("/edit-book/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        model.addAttribute("book", book);
        return "edit-book";

    }
    */


    @GetMapping("/edit-book/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/edit-book/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute Book updatedBook) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setIsbn(updatedBook.getIsbn());
        bookRepository.save(book);
        return "redirect:/";
    }


    @GetMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable Long id){
        bookRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/about")
    public String showAboutPage(){
        return "about";
    }




}
