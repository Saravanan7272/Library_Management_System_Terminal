package Books;

public class Book  {
    // Private fields (as shown in the diagram)
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String bookGroup;
    private int totalQuantity;
    private int availableQuantity;
    private String rowName;
    private String rackNo;
    private int borrowCount;

    // Default constructor
    public Book() {
    }

    // Parameterized constructor
    public Book(String isbn, String title, String author, String publisher, String bookGroup,
                int totalQuantity, int availableQuantity, String rowName, String rackNo,
                int borrowCount) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.bookGroup = bookGroup;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.rowName = rowName;
        this.rackNo = rackNo;
        this.borrowCount = borrowCount;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getBookGroup() {
        return bookGroup;
    }

    public void setBookGroup(String bookGroup) {
        this.bookGroup = bookGroup;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public String getRackNo() {
        return rackNo;
    }

    public void setRackNo(String rackNo) {
        this.rackNo = rackNo;
    }

    public int getNoOfborrowCount() {
        return borrowCount;
    }

    public void setNoOfborrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }

    @Override
    public String toString() {
        return String.format("\u001B[36m%-15s %-25s %-25s %-20s %-20s %-15d %-15d %-15s %-15s %-15d\u001B[0m",
                isbn, title, author, publisher, bookGroup, totalQuantity, availableQuantity, rowName, rackNo, borrowCount);
    }

    public static String getHeader() {
        return String.format("\u001B[1;34m%-15s %-25s %-25s %-20s %-20s %-15s %-15s %-15s %-15s %-15s\u001B[0m",
                "ISBN", "Title", "Author", "Publisher", "Book Group", "Total Quantity",
                "Available Quantity", "Row Name", "Rack No", "No of BorrowCount");
    }


}
