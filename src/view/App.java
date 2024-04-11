package view;

import model.*;
import persistence.*;
import service.DatabaseConnection;
import java.util.Scanner;

public class App {
    private static App instance;
    private final DatabaseConnection db;
    private final UtilizatorRepository utilizatorRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final RatingRepository ratingRepository;
    private final ShelfRepository shelfRepository;
    private final ShelfBookRepository shelfBookRepository;
    private final BookRatingRepository bookRatingRepository;
    private final AuthorRatingRepository authorRatingRepository;

    private App() {
        db = DatabaseConnection.getInstance();
        utilizatorRepository = new UtilizatorRepository(db);
        authorRepository = new AuthorRepository(db);
        bookRepository = new BookRepository(db);
        ratingRepository = new RatingRepository(db);
        shelfRepository = new ShelfRepository(db, utilizatorRepository);
        shelfBookRepository = new ShelfBookRepository(db);
        bookRatingRepository = new BookRatingRepository(db);
        authorRatingRepository = new AuthorRatingRepository(db);
    }

    public static App getInstance() {
        if (instance == null)
            instance = new App();
        return instance;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option;

        while (true) {
            printCRUDMenu();
            System.out.print("Choose option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> create();
                case 2 -> read();
                case 3 -> update();
                case 4 -> delete();
                case 5 -> {
                    System.out.println("Exiting the application...");
                    return;
                }
                default -> System.out.println("Invalid option, please choose something else.");
            }
        }
    }

    private void create() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            printMenu();
            System.out.print("Choose option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    Utilizator user = new Utilizator();
                    user.read();
                    utilizatorRepository.add(user);
                }
                case 2 -> {
                    Author author = new Author();
                    author.read();
                    authorRepository.add(author);
                }
                case 3 -> {
                    Book book = new Book();
                    book.read();
                    bookRepository.add(book);
                }
                case 4 -> {
                    Rating rating = new Rating();
                    rating.read();
                    ratingRepository.add(rating);
                }
                case 5 -> {return;}
                default -> System.out.println("Invalid option, please choose something else.");
            }
        }
    }

    private void read() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            printMenu();
            System.out.print("Choose option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    // Read utilizator
                    System.out.print("Enter the ID of the user: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    Utilizator user = utilizatorRepository.get(userId);
                    if (user != null) {
                        System.out.println("User found:");
                        System.out.println("ID: " + user.getId());
                        System.out.println("Username: " + user.getUsername());
                        System.out.println("Mail: " + user.getMail());
                        // Print other user details as needed
                    } else {
                        System.out.println("User not found with ID: " + userId);
                    }
                }
                case 2 -> {
                    // Read author
                    System.out.print("Enter the ID of the author: ");
                    int authorId = scanner.nextInt();
                    scanner.nextLine();
                    Author author = authorRepository.get(authorId);
                    if (author != null) {
                        System.out.println("Author found:");
                        System.out.println("ID: " + author.getIdAuthor());
                        System.out.println("Last Name: " + author.getNume());
                        System.out.println("First Name: " + author.getPrenume());
                        // Print other author details as needed
                    } else {
                        System.out.println("Author not found with ID: " + authorId);
                    }
                }
                case 3 -> {
                    // Read book
                    System.out.print("Enter the ID of the book: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();
                    Book book = bookRepository.get(bookId);
                    if (book != null) {
                        System.out.println("Book found:");
                        System.out.println("ID: " + book.getIdBook());
                        System.out.println("Title: " + book.getTitlu());
                        // Print other book details as needed
                    } else {
                        System.out.println("Book not found with ID: " + bookId);
                    }
                }
                case 4 -> {
                    // Read rating
                    System.out.print("Enter the ID of the rating: ");
                    int ratingId = scanner.nextInt();
                    scanner.nextLine();
                    Rating rating = ratingRepository.get(ratingId);
                    if (rating != null) {
                        System.out.println("Rating found:");
                        System.out.println("ID: " + rating.getIdRating());
                        System.out.println("Note: " + rating.getNota());
                        System.out.println("Review: " + rating.getReview());
                        // Print other rating details as needed
                    } else {
                        System.out.println("Rating not found with ID: " + ratingId);
                    }
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option, please choose something else.");
            }
        }
    }


    private void update() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            printMenu();
            System.out.print("Choose option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    // Update utilizator
                    System.out.print("Enter the ID of the user to update: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    Utilizator user = utilizatorRepository.get(userId);
                    if (user != null) {
                        System.out.print("Enter the new username: ");
                        String newUsername = scanner.nextLine();
                        user.setUsername(newUsername);
                        utilizatorRepository.update(user);
                        System.out.println("User updated successfully.");
                    } else {
                        System.out.println("User not found with ID: " + userId);
                    }
                }
                case 2 -> {
                    // Update author
                    System.out.print("Enter the ID of the author to update: ");
                    int authorId = scanner.nextInt();
                    scanner.nextLine();
                    Author author = authorRepository.get(authorId);
                    if (author != null) {
                        System.out.print("Enter the new last name: ");
                        String newLastName = scanner.nextLine();
                        author.setNume(newLastName);
                        System.out.print("Enter the new first name: ");
                        String newFirstName = scanner.nextLine();
                        author.setPrenume(newFirstName);
                        authorRepository.update(author);
                        System.out.println("Author updated successfully.");
                    } else {
                        System.out.println("Author not found with ID: " + authorId);
                    }
                }
                case 3 -> {
                    // Update book
                    System.out.print("Enter the ID of the book to update: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();
                    Book book = bookRepository.get(bookId);
                    if (book != null) {
                        System.out.print("Enter the new title: ");
                        String newTitle = scanner.nextLine();
                        book.setTitlu(newTitle);
                        bookRepository.update(book);
                        System.out.println("Book updated successfully.");
                    } else {
                        System.out.println("Book not found with ID: " + bookId);
                    }
                }
                case 4 -> {
                    // Update rating
                    System.out.print("Enter the ID of the rating to update: ");
                    int ratingId = scanner.nextInt();
                    scanner.nextLine();
                    Rating rating = ratingRepository.get(ratingId);
                    if (rating != null) {
                        System.out.print("Enter the new note: ");
                        int newNote = scanner.nextInt();
                        scanner.nextLine();
                        rating.setNota(newNote);
                        System.out.print("Enter the new review: ");
                        String newReview = scanner.nextLine();
                        rating.setReview(newReview);
                        ratingRepository.update(rating);
                        System.out.println("Rating updated successfully.");
                    } else {
                        System.out.println("Rating not found with ID: " + ratingId);
                    }
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option, please choose something else.");
            }
        }
    }

    private void delete() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            printMenu();
            System.out.print("Choose option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    // Delete utilizator
                    System.out.print("Enter the ID of the user to delete: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    Utilizator user = utilizatorRepository.get(userId);
                    if (user != null) {
                        utilizatorRepository.delete(user);
                        System.out.println("User deleted successfully.");
                    } else {
                        System.out.println("User not found with ID: " + userId);
                    }
                }
                case 2 -> {
                    // Delete author
                    System.out.print("Enter the ID of the author to delete: ");
                    int authorId = scanner.nextInt();
                    scanner.nextLine();
                    Author author = authorRepository.get(authorId);
                    if (author != null) {
                        authorRepository.delete(author);
                        System.out.println("Author deleted successfully.");
                    } else {
                        System.out.println("Author not found with ID: " + authorId);
                    }
                }
                case 3 -> {
                    // Delete book
                    System.out.print("Enter the ID of the book to delete: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();
                    Book book = bookRepository.get(bookId);
                    if (book != null) {
                        bookRepository.delete(book);
                        System.out.println("Book deleted successfully.");
                    } else {
                        System.out.println("Book not found with ID: " + bookId);
                    }
                }
                case 4 -> {
                    // Delete rating
                    System.out.print("Enter the ID of the rating to delete: ");
                    int ratingId = scanner.nextInt();
                    scanner.nextLine();
                    Rating rating = ratingRepository.get(ratingId);
                    if (rating != null) {
                        ratingRepository.delete(rating);
                        System.out.println("Rating deleted successfully.");
                    } else {
                        System.out.println("Rating not found with ID: " + ratingId);
                    }
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option, please choose something else.");
            }
        }
    }


    private void printCRUDMenu() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~ GOODREADS MENU ~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("1 - Create menu");
        System.out.println("2 - Read menu");
        System.out.println("3 - Update menu");
        System.out.println("4 - Delete menu");
        System.out.println("5 - Exit the application");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private void printMenu() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~ GOODREADS MENU ~~~~~~~~~~~~~~~~~~~");
        System.out.println("1 - User");
        System.out.println("2 - Author");
        System.out.println("3 - Book");
        System.out.println("4 - Rating");
        System.out.println("5 - Go back");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
