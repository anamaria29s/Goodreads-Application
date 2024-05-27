package view;

import model.*;
import model.associative.ShelfBook;
import persistence.*;
import service.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;
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
        bookRepository = new BookRepository(db, authorRepository);
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
                case 5 -> login();
                case 6 -> {
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
                    int userId = user.getId();
                    System.out.print("Enter the ID of the new shelf: ");
                    int shelfId = scanner.nextInt();
                    scanner.nextLine();
                    Shelf shelf = new Shelf(shelfId, user);
                    shelfRepository.add(shelf);

                    System.out.println("User and shelf created successfully.");

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
                        System.out.println("Authors:");
                        List<Author> authors = bookRepository.getAuthorsForBook(bookId);
                        for (Author author : authors) {
                            System.out.println("- " + author.getNume() + " " + author.getPrenume());
                        }
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
    private void login(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username to login: ");
        String username = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        Utilizator user = utilizatorRepository.getByUsername(username);

        if (user == null) {
            System.out.println("User not found. Exiting login.");
            return;
        }

        if (!user.getPassword().equals(password)) {
            System.out.println("Invalid password. Exiting login.");
            return;
        }

        int option;
        while (true) {
            printLoginMenu();
            System.out.print("Choose option: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1 -> viewBooksByAuthor();
                case 2 -> viewBookById();
                case 3 -> viewRatingById();
                case 4 -> manageShelf(user);
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option, please choose something else.");
            }
        }
    }

    private void viewBooksByAuthor() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the ID of the author: ");
        int authorId = sc.nextInt();
        sc.nextLine();

        List<Book> books = bookRepository.getBooksByAuthor(authorId);
        if (books.isEmpty()) {
            System.out.println("No books found for author ID: " + authorId);
        } else {
            books.forEach(book -> System.out.println("ID: " + book.getIdBook() + ", Title: " + book.getTitlu()));
        }
    }

    private void viewBookById() {
        Scanner scanner = new Scanner(System.in);
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

    private void viewRatingById() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the ID of the rating: ");
        int ratingId = sc.nextInt();
        sc.nextLine();

        Rating rating = ratingRepository.get(ratingId);
        if (rating != null) {
            System.out.println("Rating found:");
            System.out.println("ID: " + rating.getIdRating());
            System.out.println("Note: " + rating.getNota());
            System.out.println("Review: " + rating.getReview());
        } else {
            System.out.println("Rating not found with ID: " + ratingId);
        }
    }

    private void manageShelf(Utilizator user) {
        Scanner sc = new Scanner(System.in);
        int choice;
        ShelfBookRepository shelfBookRepository = new ShelfBookRepository(db);
        ShelfRepository shelf = new ShelfRepository(db,  new UtilizatorRepository(db));
        while (true) {
            printShelfMenu();
            System.out.print("Choose option: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    Shelf userShelf = shelfRepository.getShelfByUserId(user.getId());
                    if (userShelf != null) {
                        ArrayList<ShelfBook> shelfBooks = shelfBookRepository.getBooksInShelf(userShelf.getIdShelf());
                        ArrayList<Integer> bookIds = new ArrayList<>();
                        for (ShelfBook shelfBook : shelfBooks) {
                            bookIds.add(shelfBook.getIdBook());
                        }
                        if (!bookIds.isEmpty()) {
                        ArrayList<String> bookTitles = bookRepository.getBookTitlesByIds(bookIds);

                            for (int i = 0; i < bookTitles.size(); i++) {
                                String title = bookTitles.get(i);
                                String status = shelfBooks.get(i).getStatus().value();
                                System.out.println("Book Title: " + title + " Status: " + status);
                            }
                        }
                        else {
                            System.out.println("User does not have books in shelf.");
                        }

                    } else {
                        System.out.println("User does not have a shelf.");
                    }


                }
                case 2 -> {
                    System.out.print("Enter the ID of the book to add to shelf: ");
                    int bookId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter the status of the book (read, to read, reading): ");
                    String statusInput = sc.nextLine();
                    Status status = Status.fromString(statusInput);

                    int shelfId = shelfRepository.getShelfByUserId(user.getId()).getIdShelf(); // Obține shelfId pentru utilizatorul curent
                    ShelfBook shelfBook = new ShelfBook(bookId, shelfId, status); // Folosește shelfId
                    shelfBookRepository.add(shelfBook); // Adaugă ShelfBook în repository

                    System.out.println("Book added to the user's shelf with status " + status.value());
                }
                case 3 -> {
                    System.out.print("Enter the ID of the book to remove from shelf: ");
                    int bookId = sc.nextInt();
                    sc.nextLine();
                    ShelfBook shelfBook =  shelfBookRepository.get(bookId);
                    shelfBookRepository.delete(shelfBook);
                    System.out.println("Book removed from the user's shelf.");
                }
                case 4 -> {
                    System.out.print("Enter the ID of the book to update status: ");
                    int bookIdToUpdate = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter the new status (read, to read, reading): ");
                    String newStatusInput = sc.nextLine();
                    Status newStatus = Status.fromString(newStatusInput);
                    int shelfId = shelfRepository.getShelfByUserId(user.getId()).getIdShelf(); // Obține shelfId pentru utilizatorul curent
                    shelfBookRepository.updateBookStatus(bookIdToUpdate, shelfId, newStatus);
                    System.out.println("Book status updated successfully.");

                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option, please choose something else.");
            }
        }
    }


    private void printCRUDMenu() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~ GOODREADS MENU (CRUD) ~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("1 - Create menu");
        System.out.println("2 - Read menu");
        System.out.println("3 - Update menu");
        System.out.println("4 - Delete menu");
        System.out.println("5 - Login");
        System.out.println("6 - Exit the application");
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
    private void printLoginMenu() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~ GOODREADS MENU (LOGIN) ~~~~~~~~~~~~~~~~~~~");
       // System.out.println("1 - User");
        System.out.println("1 - Author");
        System.out.println("2 - Book");
        System.out.println("3 - Rating");
        System.out.println("4 - Shelf");
        System.out.println("5 - Go back");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
    private void printShelfMenu() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~ GOODREADS MENU (Shelf) ~~~~~~~~~~~~~~~~~~~");
        System.out.println("1 - View shelf");
        System.out.println("2 - Add book to shelf");
        System.out.println("3 - Delete book from shelf");
        System.out.println("4 - Update shelf");
        System.out.println("5 - Go back");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
