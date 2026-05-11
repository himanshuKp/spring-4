package com.himanshu.springpractice.service;

import com.himanshu.springpractice.model.Book;
import com.himanshu.springpractice.model.BorrowingRecord;
import com.himanshu.springpractice.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class LibraryService {

    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private Map<Long, BorrowingRecord> borrowingRecords = new HashMap<>();

    public List<Book> getBooks() {
        return books;
    }

//    get book by id
    public Optional<Book> getBookById(Long id){
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public Book addBook(Book book){
        book.setTitle(HtmlUtils.htmlEscape(book.getTitle()));
        books.add(book);
        return book;
    }

    public void updateBook(Book updatedBook){
        IntStream.range(0, books.size())
                .filter(index -> books.get(index).getId().equals(updatedBook.getId()))
                .findFirst()
                .ifPresent(index -> books.set(index, updatedBook));
    }

    public void deleteBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }

    public List<Member> getMembers() {
        return members;
    }

    public Optional<Member> getMemberById(Long id){
        return members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst();
    }

    public void addMember(Member member){
        members.add(member);
    }

    public void updateMember(Member updatedMember){
        IntStream.range(0, members.size())
                .filter(index -> members.get(index).getId().equals(updatedMember.getId()))
                .findFirst()
                .ifPresent(index -> members.set(index, updatedMember));
    }

    public void deleteMember(Long id){
        members.removeIf(member -> member.getId().equals(id));
    }

    public  Map<Long, BorrowingRecord> getBorrowingRecords() {
        return  borrowingRecords;
    }

    public void borrowBook(BorrowingRecord borrowingRecord){
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setDueDate(LocalDate.now().plusDays(14));
        borrowingRecords.put(borrowingRecord.getId(), borrowingRecord);

        Book book = borrowingRecord.getBook();
        book.setAvailableCopies(book.getAvailableCopies()-1);
    }

    public void returnBook(Long recordId, LocalDate returnDate) {
        Optional.ofNullable(borrowingRecords.get(recordId))
                .ifPresent(record -> {
                    record.setReturnDate(LocalDate.now());
                    record.getBook().setAvailableCopies(record.getBook().getAvailableCopies()+1);
                });
    }
}
