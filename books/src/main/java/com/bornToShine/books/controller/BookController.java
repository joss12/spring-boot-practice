package com.bornToShine.books.controller;

import com.bornToShine.books.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks(){
        books.addAll(List.of(
                new Book("Title one", "Author one", "science"),
                new Book("Title two", "Author two", "science"),
                new Book("Title Three", "Author three", "history"),
                new Book("Title Four", "Author four", "math"),
                new Book("Title Five", "Author five", "math"),
                new Book("Title Six", "Author six", "math")
        ));
    }

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category){
        if(category == null){
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @GetMapping("/{title}")
    public Book getBookByTitle(@PathVariable String title){
//        for(Book book : books){
//            if(book.getTitle().equalsIgnoreCase(title)){
//                return book;
//            }
//        }
//        return null;

        //Lambda and streams for filtering
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public void createBook(@RequestBody Book newBook) {

        boolean isNewBook = books.stream()
                .noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
        if(isNewBook){
            books.add(newBook);
        }
//        for(Book book : books){
//            if(book.getTitle().equalsIgnoreCase(newBook.getTitle())){
//                return;
//            }
//        }
    }

    @PutMapping("/{title}")
    public void updateBook(@PathVariable String title, @RequestBody Book updateBook) {
        for (int i = 0; i < books.size(); i++){
            if (books.get(i).getTitle().equalsIgnoreCase(title)){
                books.set(i, updateBook);
                return;
            }
        }
    }

    @DeleteMapping("/{title}")
    public void deleteBook(@PathVariable String title){
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

}
